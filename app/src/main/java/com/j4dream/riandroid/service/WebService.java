package com.j4dream.riandroid.service;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.j4dream.riandroid.constants.ServerConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
/**
 * Created by Dream on 2014/11/11.
 */
public class WebService {
    public JSONObject doPost(JSONObject requestJSON, String url) throws IOException {
        JSONObject result = null;
        String requestUrl = ServerConstants.HOST_NAME + url;

        HttpPost request = new HttpPost(requestUrl);
        request.addHeader("Content-Type", "application/json");
        try {
            StringEntity se = new StringEntity(requestJSON.toString());
            System.out.println(requestJSON.toString());
            request.setEntity(se);
            HttpResponse httpResponse = new DefaultHttpClient().execute(request);
            String retSrc = EntityUtils.toString(httpResponse.getEntity());
            result = new JSONObject(retSrc);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public JSONObject getHttps() throws Exception{
        String https = "https://app.rimanggis.com/manggis_web/users/information.json?csrf_token=";
        JSONObject jsonResult;
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
        HttpsURLConnection conn = (HttpsURLConnection)new URL(https).openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        String jsonStr = "";
        while ((line = br.readLine()) != null)
            jsonStr += line;
        System.out.println(jsonStr);
        jsonResult = new JSONObject(jsonStr);
        return jsonResult;
    }

    private class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }
    }

    private class MyTrustManager implements X509TrustManager{
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
