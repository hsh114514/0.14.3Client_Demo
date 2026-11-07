package com.mojang.minecraftpe.store;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/store/StoreFactory.class */
public class StoreFactory {
    public static Store createAmazonAppStore(StoreListener storeListener) {
        return new Store(storeListener);
    }

    public static Store createGooglePlayStore(String str, StoreListener storeListener) {
        return new Store(storeListener);
    }

    public static Store createSamsungAppStore(StoreListener storeListener) {
        return new Store(storeListener);
    }
}
