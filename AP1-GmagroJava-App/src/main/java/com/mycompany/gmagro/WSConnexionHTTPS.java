/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gmagro;

import okhttp3.Cookie;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author AMedassi
 */
public class WSConnexionHTTPS {

    private static WSConnexionHTTPS instance = null;

    private String base_url = "https://sio.jbdelasalle.com/~ageneste/gmagrowsjava/index.php?";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    private WSConnexionHTTPS() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
        };
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        OkHttpClient.Builder newBuilder = new OkHttpClient.Builder();
        newBuilder.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0]);
        newBuilder.hostnameVerifier((hostname, session) -> true);
        newBuilder.cookieJar(new CookieJar() {
            List<Cookie> cookies;

            @Override
            public void saveFromResponse(HttpUrl hu, List<Cookie> list) {
                this.cookies = list;
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl hu) {
                if (cookies != null) {
                    return cookies;
                } else {
                    return new ArrayList<>();
                }
            }
        });

        client = newBuilder.build();
    }

    public static WSConnexionHTTPS getInstance() {
        if (instance == null) {
            try {
                instance = new WSConnexionHTTPS();
            } catch (NoSuchAlgorithmException | KeyManagementException ex) {
                Logger.getLogger(WSConnexionHTTPS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public String get(String url) throws IOException {
        System.out.println("WSGET: "+base_url+url);
        Request request = new Request.Builder()
                .url(base_url + url)
                .build();

        try ( Response response = client.newCall(request).execute()) {
            String ret = response.body().string() ;
            System.out.println("WGRESPONSE: "+ret);
            return ret;
        }
    }

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(base_url + url)
                .post(body)
                .build();
        try ( Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

}
