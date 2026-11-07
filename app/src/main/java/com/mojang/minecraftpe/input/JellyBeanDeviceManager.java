package com.mojang.minecraftpe.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.input.InputManager;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/input/JellyBeanDeviceManager.class */
@TargetApi(16)
public class JellyBeanDeviceManager extends InputDeviceManager implements InputManager.InputDeviceListener {
    private final InputManager inputManager;

    JellyBeanDeviceManager(Context context) {
        this.inputManager = (InputManager) context.getSystemService("input");
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        onInputDeviceAddedNative(i);
        setDoubleTriggersSupportedNative(InputCharacteristics.allControllersHaveDoubleTriggers());
    }

    native void onInputDeviceAddedNative(int i);

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        onInputDeviceChangedNative(i);
        setDoubleTriggersSupportedNative(InputCharacteristics.allControllersHaveDoubleTriggers());
    }

    native void onInputDeviceChangedNative(int i);

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        onInputDeviceRemovedNative(i);
        setDoubleTriggersSupportedNative(InputCharacteristics.allControllersHaveDoubleTriggers());
    }

    native void onInputDeviceRemovedNative(int i);

    @Override // com.mojang.minecraftpe.input.InputDeviceManager
    public void register() {
        this.inputManager.getInputDeviceIds();
        this.inputManager.registerInputDeviceListener(this, null);
        setDoubleTriggersSupportedNative(InputCharacteristics.allControllersHaveDoubleTriggers());
    }

    native void setDoubleTriggersSupportedNative(boolean z);

    @Override // com.mojang.minecraftpe.input.InputDeviceManager
    public void unregister() {
        this.inputManager.unregisterInputDeviceListener(this);
    }
}
