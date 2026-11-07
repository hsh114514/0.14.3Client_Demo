package com.mojang.minecraftpe.input;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/input/InputDeviceManager.class */
public abstract class InputDeviceManager {

    /* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/input/InputDeviceManager$DefaultDeviceManager.class */
    public static class DefaultDeviceManager extends InputDeviceManager {
        private DefaultDeviceManager() {
        }

        @Override // com.mojang.minecraftpe.input.InputDeviceManager
        public void register() {
            Log.w("MCPE", "INPUT Noop register device manager");
        }

        @Override // com.mojang.minecraftpe.input.InputDeviceManager
        public void unregister() {
            Log.w("MCPE", "INPUT Noop unregister device manager");
        }
    }

    public static InputDeviceManager create(Context context) {
        return Build.VERSION.SDK_INT >= 16 ? new JellyBeanDeviceManager(context) : new DefaultDeviceManager();
    }

    public abstract void register();

    public abstract void unregister();
}
