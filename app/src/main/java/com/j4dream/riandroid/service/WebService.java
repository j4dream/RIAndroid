package com.j4dream.riandroid.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.j4dream.riandroid.constants.ServerConstants;

import java.io.IOException;

/**
 * Created by Dream on 2014/11/11.
 */
public class WebService {
    public JSONObject doPost(JSONObject requestJSON, String url) {
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

}
