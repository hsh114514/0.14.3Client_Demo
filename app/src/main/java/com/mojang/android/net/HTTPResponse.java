package com.mojang.android.net;

import org.apache.http.Header;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/android/net/HTTPResponse.class */
public class HTTPResponse {
    public static final int ABORTED = 2;
    public static final int DONE = 1;
    public static final int IN_PROGRESS = 0;
    public static final int TIME_OUT = 3;
    Header[] headers;
    int status = 0;
    String body = "";
    int responseCode = -100;

    public String getBody() {
        return this.body;
    }

    public Header[] getHeaders() {
        return this.headers;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public int getStatus() {
        return this.status;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public void setHeaders(Header[] headerArr) {
        this.headers = headerArr;
    }

    public void setResponseCode(int i) {
        this.responseCode = i;
    }

    public void setStatus(int i) {
        this.status = i;
    }
}
