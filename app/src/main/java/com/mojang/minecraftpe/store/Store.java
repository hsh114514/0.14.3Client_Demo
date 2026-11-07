package com.mojang.minecraftpe.store;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/minecraftpe/store/Store.class */
public class Store {
    private StoreListener listener;

    public Store(StoreListener storeListener) {
        this.listener = storeListener;
    }

    public void destructor() {
        System.out.println("Store: Destructor");
    }

    public String getStoreId() {
        System.out.println("Store: Get store ID");
        return "Placeholder store ID";
    }

    public void purchase(String str) {
        System.out.println("Store: Purchase " + str);
    }

    public void queryProducts(String[] strArr) {
        System.out.println("Store: Query products");
    }

    public void queryPurchases() {
        System.out.println("Store: Query purchases");
    }
}
