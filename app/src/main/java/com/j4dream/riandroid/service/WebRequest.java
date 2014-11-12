package com.j4dream.riandroid.service;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dream on 2014/11/11.
 */
public class WebRequest extends AsyncTask<Void, Void, Boolean> {
    private JSONObject response = null;
    private JSONObject request;
    private String url;
    public WebRequest() {

    }

    public WebRequest(JSONObject request, String url) {
        this.request = request;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        WebService web = new WebService();
        try {
            response = web.doPost(request, url);
            if (response.getInt("code") == 1) {
                return true;
            } else if (response.getInt("code") == 0) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // TODO: register the new account here.
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

    }

    @Override
    protected void onCancelled() {
    }

}
