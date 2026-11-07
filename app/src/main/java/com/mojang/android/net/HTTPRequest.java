package com.mojang.android.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/* JADX INFO: loaded from: dex_minecraftpe.jar:com/mojang/android/net/HTTPRequest.class */
public class HTTPRequest {
    HttpRequestBase mHTTPRequest = null;
    String mURL = "";
    String mRequestBody = "";
    String mCookieData = "";
    String mRequestContentType = "text/plain";
    HTTPResponse mResponse = new HTTPResponse();

    private void addBodyToRequest(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase) {
        if (this.mRequestBody != "") {
            try {
                StringEntity stringEntity = new StringEntity(this.mRequestBody);
                stringEntity.setContentType(this.mRequestContentType);
                httpEntityEnclosingRequestBase.setEntity(stringEntity);
                httpEntityEnclosingRequestBase.addHeader("Content-Type", this.mRequestContentType);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void addHeaders() {
        this.mHTTPRequest.addHeader("User-Agent", "MCPE/Android");
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, 3000);
        this.mHTTPRequest.setParams(basicHttpParams);
        if (this.mCookieData != null && this.mCookieData.length() > 0) {
            this.mHTTPRequest.addHeader("Cookie", this.mCookieData);
        }
        this.mHTTPRequest.addHeader("Charset", "utf-8");
    }

    private void createHTTPRequest(String str) {
        synchronized (this) {
            if (str.equals("DELETE")) {
                this.mHTTPRequest = new HttpDelete(this.mURL);
            } else if (str.equals("PUT")) {
                HttpPut httpPut = new HttpPut(this.mURL);
                addBodyToRequest(httpPut);
                this.mHTTPRequest = httpPut;
            } else if (str.equals("GET")) {
                this.mHTTPRequest = new HttpGet(this.mURL);
            } else {
                if (!str.equals("POST")) {
                    throw new InvalidParameterException("Unknown request method " + str);
                }
                HttpPost httpPost = new HttpPost(this.mURL);
                addBodyToRequest(httpPost);
                this.mHTTPRequest = httpPost;
            }
        }
    }

    public void abort() {
        synchronized (this) {
            this.mResponse.setStatus(2);
            if (this.mHTTPRequest != null) {
                this.mHTTPRequest.abort();
            }
        }
    }

    public HTTPResponse send(String str) {
        HTTPResponse hTTPResponse;
        createHTTPRequest(str);
        addHeaders();
        if (this.mResponse.getStatus() == 2) {
            hTTPResponse = this.mResponse;
        } else {
            try {
                HttpResponse httpResponseExecute = HTTPClientManager.getHTTPClient().execute(this.mHTTPRequest);
                this.mResponse.setResponseCode(httpResponseExecute.getStatusLine().getStatusCode());
                this.mResponse.setBody(EntityUtils.toString(httpResponseExecute.getEntity()));
                this.mResponse.setStatus(1);
                this.mResponse.setHeaders(httpResponseExecute.getAllHeaders());
                hTTPResponse = this.mResponse;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                this.mHTTPRequest = null;
                hTTPResponse = this.mResponse;
            } catch (ConnectTimeoutException e2) {
                this.mResponse.setStatus(3);
                this.mHTTPRequest = null;
                hTTPResponse = this.mResponse;
            } catch (IOException e3) {
                e3.printStackTrace();
                this.mHTTPRequest = null;
                hTTPResponse = this.mResponse;
            }
        }
        return hTTPResponse;
    }

    public void setContentType(String str) {
        this.mRequestContentType = str;
    }

    public void setCookieData(String str) {
        this.mCookieData = str;
    }

    public void setRequestBody(String str) {
        this.mRequestBody = str;
    }

    public void setURL(String str) {
        this.mURL = str;
    }
}
