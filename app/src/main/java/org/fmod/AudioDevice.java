package org.fmod;

import android.media.AudioTrack;
import android.util.Log;

/* JADX INFO: loaded from: dex_minecraftpe.jar:org/fmod/AudioDevice.class */
public class AudioDevice {
    private AudioTrack mTrack = null;

    private int fetchChannelConfigFromCount(int i) {
        int i2;
        if (i == 1) {
            i2 = 2;
        } else if (i == 2) {
            i2 = 3;
        } else {
            i2 = i == 6 ? 252 : 0;
        }
        return i2;
    }

    public void close() {
        try {
            this.mTrack.stop();
        } catch (IllegalStateException e) {
            Log.e("fmod", "AudioDevice::init : AudioTrack stop caused IllegalStateException");
        }
        this.mTrack.release();
        this.mTrack = null;
    }

    public boolean init(int i, int i2, int i3, int i4) {
        boolean z;
        int iFetchChannelConfigFromCount = fetchChannelConfigFromCount(i);
        int minBufferSize = AudioTrack.getMinBufferSize(i2, iFetchChannelConfigFromCount, 2);
        if (minBufferSize < 0) {
            Log.w("fmod", "AudioDevice::init : Couldn't query minimum buffer size, possibly unsupported sample rate or channel count");
        } else {
            Log.i("fmod", "AudioDevice::init : Min buffer size: " + minBufferSize + " bytes");
        }
        int i5 = i3 * i4 * i * 2;
        if (i5 <= minBufferSize) {
            i5 = minBufferSize;
        }
        Log.i("fmod", "AudioDevice::init : Actual buffer size: " + i5 + " bytes");
        try {
            this.mTrack = new AudioTrack(3, i2, iFetchChannelConfigFromCount, 2, i5, 1);
            try {
                this.mTrack.play();
                z = true;
            } catch (IllegalStateException e) {
                Log.e("fmod", "AudioDevice::init : AudioTrack play caused IllegalStateException");
                this.mTrack.release();
                this.mTrack = null;
                z = false;
            }
        } catch (IllegalArgumentException e2) {
            Log.e("fmod", "AudioDevice::init : AudioTrack creation caused IllegalArgumentException");
            z = false;
        }
        return z;
    }

    public void write(byte[] bArr, int i) {
        this.mTrack.write(bArr, 0, i);
    }
}
