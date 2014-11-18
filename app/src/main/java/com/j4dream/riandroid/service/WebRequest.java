package com.j4dream.riandroid.service;

import android.content.Context;
import android.os.AsyncTask;

import com.j4dream.riandroid.util;

import org.json.JSONObject;

/**
 * Created by Dream on 2014/11/11.
 */
public class WebRequest extends AsyncTask<Void, Void, JSONObject> {

    private Context context = null;
    private JSONObject response = null;
    private JSONObject request;
    private String method = null;
    private String url;
    public WebCallback callback = null;

    public WebRequest() {

    }

    public WebRequest(Context context, JSONObject request, String url, String method) {
        this.context = context;
        this.request = request;
        this.url = url + "?" + util.getCsrf(context);
        this.method = method;
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(Void... params){
        // TODO: attempt authentication against a network service.
        JSONObject resutl = null;
        try {
            if(method == "post") {
                resutl = new WebService(context).doLogin(request, url);
            } else {
                resutl = new WebService(context).get(url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resutl;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        callback.finishRequest(jsonObject);
    }

    @Override
    protected void onCancelled() {
    }
}
