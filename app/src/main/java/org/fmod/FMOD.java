package org.fmod;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Build;

/* JADX INFO: loaded from: dex_minecraftpe.jar:org/fmod/FMOD.class */
public class FMOD {
    private static Context gContext = null;

    public static boolean checkInit() {
        return gContext != null;
    }

    public static void close() {
        gContext = null;
    }

    public static AssetManager getAssetManager() {
        return gContext != null ? gContext.getAssets() : null;
    }

    public static int getOutputBlockSize() {
        String property;
        return (gContext == null || Build.VERSION.SDK_INT < 17 || (property = ((AudioManager) gContext.getSystemService("audio")).getProperty("android.media.property.OUTPUT_FRAMES_PER_BUFFER")) == null) ? 0 : Integer.parseInt(property);
    }

    public static int getOutputSampleRate() {
        String property;
        return (gContext == null || Build.VERSION.SDK_INT < 17 || (property = ((AudioManager) gContext.getSystemService("audio")).getProperty("android.media.property.OUTPUT_SAMPLE_RATE")) == null) ? 0 : Integer.parseInt(property);
    }

    public static void init(Context context) {
        gContext = context;
    }

    public static boolean supportsLowLatency() {
        return (gContext == null || Build.VERSION.SDK_INT < 5) ? false : gContext.getPackageManager().hasSystemFeature("android.hardware.audio.low_latency");
    }
}
