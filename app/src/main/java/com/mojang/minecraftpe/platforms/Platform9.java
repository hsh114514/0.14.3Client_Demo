package com.mojang.minecraftpe.platforms;

import android.os.Build;
import android.view.View;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/platforms/Platform9.class */
public class Platform9 extends Platform {
    @Override // com.mojang.minecraftpe.platforms.Platform
    public String getABIS() {
        return Build.CPU_ABI;
    }

    @Override // com.mojang.minecraftpe.platforms.Platform
    public void onAppStart(View view) {
    }

    @Override // com.mojang.minecraftpe.platforms.Platform
    public void onViewFocusChanged(boolean z) {
    }

    @Override // com.mojang.minecraftpe.platforms.Platform
    public void onVolumePressed() {
    }
}
