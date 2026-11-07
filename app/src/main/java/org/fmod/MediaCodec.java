package org.fmod;

import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: dex_minecraftpe.jar:org/fmod/MediaCodec.class */
public class MediaCodec implements InvocationHandler {
    private long mCodecPtr = 0;
    private long mLength = 0;
    private int mSampleRate = 0;
    private int mChannelCount = 0;
    private boolean mInputFinished = false;
    private boolean mOutputFinished = false;
    private android.media.MediaCodec mDecoder = null;
    private Object mDataSourceProxy = null;
    private MediaExtractor mExtractor = null;
    private ByteBuffer[] mInputBuffers = null;
    private ByteBuffer[] mOutputBuffers = null;
    private int mCurrentOutputBufferIndex = -1;

    private static native long fmodGetSize(long j);

    private static native int fmodReadAt(long j, long j2, byte[] bArr, int i);

    public void close() {
        if (this.mDecoder != null) {
            this.mDecoder.stop();
            this.mDecoder.release();
            this.mDecoder = null;
        }
        if (this.mExtractor != null) {
            this.mExtractor.release();
            this.mExtractor = null;
        }
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public long getLength() {
        return this.mLength;
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public boolean init(long j) {
        boolean z;
        int integer = 0;
        if (Build.VERSION.SDK_INT < 17) {
            Log.w("fmod", "MediaCodec::init : MediaCodec unavailable, ensure device is running at least 4.2 (JellyBean).\n");
            z = false;
        } else {
            this.mCodecPtr = j;
            this.mExtractor = new MediaExtractor();
            try {
                Class<?> cls = Class.forName("android.media.DataSource");
                Method method = Class.forName("android.media.MediaExtractor").getMethod("setDataSource", cls);
                this.mDataSourceProxy = Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, this);
                method.invoke(this.mExtractor, this.mDataSourceProxy);
                int trackCount = this.mExtractor.getTrackCount();
                int i = 0;
                while (true) {
                    z = false;
                    if (i >= trackCount) {
                        break;
                    }
                    MediaFormat trackFormat = this.mExtractor.getTrackFormat(i);
                    String string = trackFormat.getString("mime");
                    Log.d("fmod", "MediaCodec::init : Format " + i + " / " + trackCount + " -- " + trackFormat);
                    if (string.equals("audio/mp4a-latm")) {
                        try {
                            this.mDecoder = android.media.MediaCodec.createDecoderByType(string);
                            this.mExtractor.selectTrack(i);
                            this.mDecoder.configure(trackFormat, (Surface) null, (MediaCrypto) null, 0);
                            this.mDecoder.start();
                            this.mInputBuffers = this.mDecoder.getInputBuffers();
                            this.mOutputBuffers = this.mDecoder.getOutputBuffers();
                            int integer2 = trackFormat.containsKey("encoder-delay") ? trackFormat.getInteger("encoder-delay") : 0;
                            if (trackFormat.containsKey("encoder-padding")) {
                                integer = trackFormat.getInteger("encoder-padding");
                            }
                            long j2 = trackFormat.getLong("durationUs");
                            this.mChannelCount = trackFormat.getInteger("channel-count");
                            this.mSampleRate = trackFormat.getInteger("sample-rate");
                            this.mLength = (((int) (((j2 * ((long) this.mSampleRate)) + (1000000 - 1)) / 1000000)) - integer2) - integer;
                            z = true;
                            break;
                        } catch (IOException e) {
                            Log.e("fmod", "MediaCodec::init : " + e.toString());
                            z = false;
                        }
                    } else {
                        i++;
                    }
                }
            } catch (ClassNotFoundException e2) {
                Log.w("fmod", "MediaCodec::init : " + e2.toString());
                z = false;
            } catch (IllegalAccessException e3) {
                Log.e("fmod", "MediaCodec::init : " + e3.toString());
                z = false;
            } catch (NoSuchMethodException e4) {
                Log.w("fmod", "MediaCodec::init : " + e4.toString());
                z = false;
            } catch (InvocationTargetException e5) {
                Log.e("fmod", "MediaCodec::init : " + e5.toString());
                z = false;
            }
        }
        return z;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) {
        Object objValueOf;
        if (method.getName().equals("readAt")) {
            objValueOf = Integer.valueOf(fmodReadAt(this.mCodecPtr, ((Long) objArr[0]).longValue(), (byte[]) objArr[1], ((Integer) objArr[2]).intValue()));
        } else if (method.getName().equals("getSize")) {
            objValueOf = Long.valueOf(fmodGetSize(this.mCodecPtr));
        } else {
            objValueOf = null;
            if (!method.getName().equals("close")) {
                Log.w("fmod", "MediaCodec::invoke : Unrecognised method found: " + method.getName());
                objValueOf = null;
            }
        }
        return objValueOf;
    }

    public int read(byte[] bArr, int i) {
        int iDequeueInputBuffer;
        int i2 = (this.mInputFinished && this.mOutputFinished && this.mCurrentOutputBufferIndex == -1) ? -1 : 0;
        while (!this.mInputFinished && (iDequeueInputBuffer = this.mDecoder.dequeueInputBuffer(0L)) >= 0) {
            int sampleData = this.mExtractor.readSampleData(this.mInputBuffers[iDequeueInputBuffer], 0);
            if (sampleData >= 0) {
                this.mDecoder.queueInputBuffer(iDequeueInputBuffer, 0, sampleData, this.mExtractor.getSampleTime(), 0);
                this.mExtractor.advance();
            } else {
                this.mDecoder.queueInputBuffer(iDequeueInputBuffer, 0, 0, 0L, 4);
                this.mInputFinished = true;
            }
        }
        if (!this.mOutputFinished && this.mCurrentOutputBufferIndex == -1) {
            android.media.MediaCodec.BufferInfo bufferInfo = new android.media.MediaCodec.BufferInfo();
            int iDequeueOutputBuffer = this.mDecoder.dequeueOutputBuffer(bufferInfo, 10000L);
            if (iDequeueOutputBuffer >= 0) {
                this.mCurrentOutputBufferIndex = iDequeueOutputBuffer;
                this.mOutputBuffers[iDequeueOutputBuffer].limit(bufferInfo.size);
                this.mOutputBuffers[iDequeueOutputBuffer].position(bufferInfo.offset);
            } else if (iDequeueOutputBuffer == -3) {
                this.mOutputBuffers = this.mDecoder.getOutputBuffers();
            } else if (iDequeueOutputBuffer == -2) {
                Log.d("fmod", "MediaCodec::read : MediaCodec::dequeueOutputBuffer returned MediaCodec.INFO_OUTPUT_FORMAT_CHANGED " + this.mDecoder.getOutputFormat());
            } else if (iDequeueOutputBuffer == -1) {
                Log.d("fmod", "MediaCodec::read : MediaCodec::dequeueOutputBuffer returned MediaCodec.INFO_TRY_AGAIN_LATER.");
            } else {
                Log.w("fmod", "MediaCodec::read : MediaCodec::dequeueOutputBuffer returned " + iDequeueOutputBuffer);
            }
            if ((bufferInfo.flags & 4) != 0) {
                this.mOutputFinished = true;
            }
        }
        if (this.mCurrentOutputBufferIndex != -1) {
            ByteBuffer byteBuffer = this.mOutputBuffers[this.mCurrentOutputBufferIndex];
            int iMin = Math.min(byteBuffer.remaining(), i);
            byteBuffer.get(bArr, 0, iMin);
            i2 = iMin;
            if (!byteBuffer.hasRemaining()) {
                byteBuffer.clear();
                this.mDecoder.releaseOutputBuffer(this.mCurrentOutputBufferIndex, false);
                this.mCurrentOutputBufferIndex = -1;
                i2 = iMin;
            }
        }
        return i2;
    }

    public void seek(int i) {
        if (this.mCurrentOutputBufferIndex != -1) {
            this.mOutputBuffers[this.mCurrentOutputBufferIndex].clear();
            this.mCurrentOutputBufferIndex = -1;
        }
        this.mInputFinished = false;
        this.mOutputFinished = false;
        this.mDecoder.flush();
        this.mExtractor.seekTo((((long) i) * 1000000) / ((long) this.mSampleRate), 0);
        long sampleTime = ((this.mExtractor.getSampleTime() * ((long) this.mSampleRate)) + (1000000 - 1)) / 1000000;
        int i2 = (int) ((((long) i) - sampleTime) * ((long) this.mChannelCount) * 2);
        if (i2 < 0) {
            Log.w("fmod", "MediaCodec::seek : Seek to " + i + " resulted in position " + sampleTime);
            return;
        }
        byte[] bArr = new byte[1024];
        int i3 = i2;
        while (true) {
            int i4 = i3;
            if (i4 <= 0) {
                return;
            } else {
                i3 = i4 - read(bArr, Math.min(bArr.length, i4));
            }
        }
    }
}
