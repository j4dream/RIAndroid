package com.j4dream.riandroid.service;


import android.content.Context;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.j4dream.riandroid.constants.ServerConstants;
import com.j4dream.riandroid.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
/**
 * Created by Dream on 2014/11/11.
 */
public class WebService {

    private Context context = null;

    public WebService(){

    }

    public WebService(Context context) {
        this.context = context;
    }

    public JSONObject doLogin(JSONObject requestJSON, String url) throws Exception {
        //参考
        //http://stackoverflow.com/questions/16719959/android-ssl-httpget-no-peer-certificate-error-or-connection-closed-by-peer-e
        JSONObject result = null;
        String requestUrl = ServerConstants.HOST_NAME + url;
        HttpPost request = new HttpPost(requestUrl);

        List formParams = new ArrayList();
        formParams.add(new BasicNameValuePair("j_username", requestJSON.getString("j_username")));
        formParams.add(new BasicNameValuePair("j_password", requestJSON.getString("j_password")));
        HttpEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

        request.setEntity(entity);
        HttpResponse httpResponse = getSSLClient().execute(request);
        Header[] cookies = httpResponse.getHeaders("Set-Cookie");
        String cookieStr = "";
        for (int i = 0; i < cookies.length; i++) {
            Header cookie = cookies[i];
            String cookieVal = cookie.getValue().split(";")[0];
            if(cookieVal.contains("csrf_token")){
                util.setCsrf(context, cookieVal);
            }
            cookieStr = cookieVal + ";" + cookieStr;
        }
        util.setCookies(context, cookieStr);
        String retSrc = EntityUtils.toString(httpResponse.getEntity());
        result = new JSONObject(retSrc);
        return result;
    }

    public JSONObject get(String url) throws Exception {
        //参考
        //http://stackoverflow.com/questions/16719959/android-ssl-httpget-no-peer-certificate-error-or-connection-closed-by-peer-e
        JSONObject result = null;
        String requestUrl = ServerConstants.HOST_NAME + url;

        HttpGet request = new HttpGet(requestUrl);
        request.addHeader("Cookie", util.getCookies(context));
        request.addHeader("Content-Type", "application/json");
        /*request.addHeader("Accept", "application/json");
        request.addHeader("X-Requested-With","XMLHttpRequest");*/

        HttpResponse httpResponse = getSSLClient().execute(request);
        String retSrc = EntityUtils.toString(httpResponse.getEntity());
        result = new JSONObject(retSrc);
        return result;
    }

    private DefaultHttpClient getSSLClient() throws Exception{
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                new SecureRandom());
        HttpClient client = new DefaultHttpClient();
        SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = client.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));
        DefaultHttpClient sslClient = new DefaultHttpClient(ccm,
                client.getParams());
        return sslClient;
    }

    private class CustomSSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        public CustomSSLSocketFactory(KeyStore truststore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new CustomX509TrustManager();
            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        public CustomSSLSocketFactory(SSLContext context)
                throws KeyManagementException, NoSuchAlgorithmException,
                KeyStoreException, UnrecoverableKeyException {
            super(null);
            sslContext = context;
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port,
                                   boolean autoClose) throws IOException, UnknownHostException {
            return sslContext.getSocketFactory().createSocket(socket, host, port,
                    autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }
    }

    private class CustomX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                       String authType) throws CertificateException { }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
}
