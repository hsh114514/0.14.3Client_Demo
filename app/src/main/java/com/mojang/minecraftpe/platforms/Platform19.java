package com.mojang.minecraftpe.platforms;

import android.annotation.TargetApi;
import android.os.Handler;
import android.view.View;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/platforms/Platform19.class */
@TargetApi(19)
public class Platform19 extends Platform9 {
    private Runnable decorViewSettings;
    private View decoreView;
    private Handler eventHandler;

    public Platform19(boolean z) {
        if (z) {
            this.eventHandler = new Handler();
        }
    }

    @Override // com.mojang.minecraftpe.platforms.Platform9, com.mojang.minecraftpe.platforms.Platform
    public void onAppStart(View view) {
        if (this.eventHandler == null) {
            return;
        }
        this.decoreView = view;
        this.decoreView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public void onSystemUiVisibilityChange(int i) {
                Platform19.this.eventHandler.postDelayed(Platform19.this.decorViewSettings, 500L);
            }
        });
        this.decorViewSettings = new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                Platform19.this.decoreView.setSystemUiVisibility(5894);
            }
        };
        this.eventHandler.post(this.decorViewSettings);
    }

    @Override // com.mojang.minecraftpe.platforms.Platform9, com.mojang.minecraftpe.platforms.Platform
    public void onViewFocusChanged(boolean z) {
        if (this.eventHandler == null || !z) {
            return;
        }
        this.eventHandler.postDelayed(this.decorViewSettings, 500L);
    }

    @Override // com.mojang.minecraftpe.platforms.Platform9, com.mojang.minecraftpe.platforms.Platform
    public void onVolumePressed() {
    }
}
