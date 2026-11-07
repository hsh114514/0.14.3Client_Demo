package com.mojang.minecraftpe.platforms;

import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/platforms/Platform.class */
public abstract class Platform {
    public static Platform createPlatform(boolean z) {
        Platform9 platform21;
        if (Build.VERSION.SDK_INT >= 19) {
            platform21 = new Platform19(z);
        } else {
            platform21 = Build.VERSION.SDK_INT >= 21 ? new Platform21(z) : new Platform9();
        }
        return platform21;
    }

    public abstract String getABIS();

    public abstract void onAppStart(View view);

    public abstract void onViewFocusChanged(boolean z);

    public abstract void onVolumePressed();
}
