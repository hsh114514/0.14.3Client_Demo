# JNI: libminecraftpe.so / libfmod.so / libMod.so resolve these by name.
-keep class com.mojang.** { *; }
-keep class org.fmod.** { *; }
-keep class com.irene.algui.** { *; }

# Provided at runtime by the org.apache.http.legacy optional library.
-dontwarn org.apache.http.**
