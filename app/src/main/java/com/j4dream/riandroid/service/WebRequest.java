package com.j4dream.riandroid.service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;

/**
 * Created by Dream on 2014/11/11.
 */
public class WebRequest extends AsyncTask<Void, Void, JSONObject> {

    private JSONObject response = null;
    private JSONObject request;
    private String url;
    public WebCallback callback = null;

    public WebRequest() {

    }

    public WebRequest(JSONObject request, String url) {
        this.request = request;
        this.url = url;
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
            resutl = new WebService().doPost(request, url);
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
