package com.mojang.minecraftpe.platforms;

import android.annotation.TargetApi;
import android.os.Build;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/platforms/Platform21.class */
@TargetApi(21)
public class Platform21 extends Platform19 {
    public Platform21(boolean z) {
        super(z);
    }

    @Override // com.mojang.minecraftpe.platforms.Platform9, com.mojang.minecraftpe.platforms.Platform
    public String getABIS() {
        return Build.SUPPORTED_ABIS.toString();
    }
}
