package com.j4dream.riandroid.service;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dream on 2014/11/16.
 * 回掉接口
 */
public interface WebCallback {
    void finishRequest(JSONObject json);
}
