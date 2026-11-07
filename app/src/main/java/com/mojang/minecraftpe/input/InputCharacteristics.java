package com.mojang.minecraftpe.input;

import android.os.Build;
import android.view.InputDevice;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/input/InputCharacteristics.class */
public class InputCharacteristics {
    public static boolean allControllersHaveDoubleTriggers() {
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        boolean supportsDoubleTriggers = false;
        for (int id : InputDevice.getDeviceIds()) {
            InputDevice device = InputDevice.getDevice(id);
            if (device == null || device.isVirtual() || device.getControllerNumber() <= 0 || (device.getSources() & 1025) == 0) {
                continue;
            }
            boolean[] supportedTriggerKeys = device.hasKeys(102, 103, 104, 105);
            supportsDoubleTriggers = supportedTriggerKeys.length == 4;
            for (boolean supported : supportedTriggerKeys) {
                if (!supported) {
                    supportsDoubleTriggers = false;
                    break;
                }
            }
            if (!supportsDoubleTriggers && supportedTriggerKeys[0] && supportedTriggerKeys[1]) {
                boolean supportsL2 = device.getMotionRange(17) != null || device.getMotionRange(23) != null;
                boolean supportsR2 = device.getMotionRange(18) != null || device.getMotionRange(22) != null;
                supportsDoubleTriggers = supportsL2 && supportsR2;
            }
            if (supportsDoubleTriggers && device.getName().contains("EI-GP20")) {
                supportsDoubleTriggers = false;
            }
            if (!supportsDoubleTriggers) {
                return false;
            }
        }
        return supportsDoubleTriggers;
    }
}
