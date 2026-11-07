package com.mojang.minecraftpe;

import android.annotation.SuppressLint;
import android.os.Build;
import com.mojang.minecraftpe.platforms.Platform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/HardwareInformation.class */
@SuppressLint({"DefaultLocale"})
public class HardwareInformation {
    private static final CPUInfo cpuInfo = getCPUInfo();

    /* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/HardwareInformation$CPUInfo.class */
    public static class CPUInfo {
        private final Map<String, String> cpuLines;
        private final int numberCPUCores;

        public CPUInfo(Map<String, String> map, int i) {
            this.cpuLines = map;
            this.numberCPUCores = i;
        }

        String getCPULine(String str) {
            return this.cpuLines.containsKey(str) ? this.cpuLines.get(str) : "";
        }

        int getNumberCPUCores() {
            return this.numberCPUCores;
        }
    }

    public static String getAndroidVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static String getCPUFeatures() {
        return cpuInfo.getCPULine("Features");
    }

    public static CPUInfo getCPUInfo() {
        new StringBuffer();
        HashMap map = new HashMap();
        int i = 0;
        int i2 = 0;
        if (new File("/proc/cpuinfo").exists()) {
            i = 0;
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                Pattern patternCompile = Pattern.compile("(\\w*)\\s*:\\s([^\\n]*)");
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    Matcher matcher = patternCompile.matcher(line);
                    if (matcher.find()) {
                        if (matcher.groupCount() == 2) {
                            if (!map.containsKey(matcher.group(1))) {
                                map.put(matcher.group(1), matcher.group(2));
                            }
                            if (matcher.group(1).contentEquals("processor")) {
                                i2++;
                            }
                        }
                    }
                }
                i = i2;
                if (bufferedReader != null) {
                    i = i2;
                    bufferedReader.close();
                    i = i2;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new CPUInfo(map, i);
    }

    public static String getCPUName() {
        return cpuInfo.getCPULine("Hardware");
    }

    public static String getCPUType() {
        return Platform.createPlatform(false).getABIS();
    }

    public static String getDeviceModelName() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2.toUpperCase() : str.toUpperCase() + " " + str2;
    }

    public static String getLocale() {
        return Locale.getDefault().toString();
    }

    public static int getNumCores() {
        return cpuInfo.getNumberCPUCores();
    }
}
