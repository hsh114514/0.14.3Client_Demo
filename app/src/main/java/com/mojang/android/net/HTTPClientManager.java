package com.mojang.android.net;

import android.util.Log;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/android/net/HTTPClientManager.class */
public class HTTPClientManager {
    static HTTPClientManager instance = new HTTPClientManager();
    HttpClient mHTTPClient;
    String mHttpClient;

    private HTTPClientManager() {
        this.mHTTPClient = null;
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(basicHttpParams, "utf-8");
        ConnManagerParams.setTimeout(basicHttpParams, 30000L);
        basicHttpParams.setBooleanParameter("http.protocol.expect-continue", false);
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        try {
            schemeRegistry.register(new Scheme("https", NoCertSSLSocketFactory.createDefault(), 443));
        } catch (Exception e) {
            Log.e("MCPE_ssl", "Couldn't create SSLSocketFactory");
        }
        this.mHTTPClient = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
    }

    public static HttpClient getHTTPClient() {
        return instance.mHTTPClient;
    }
}
