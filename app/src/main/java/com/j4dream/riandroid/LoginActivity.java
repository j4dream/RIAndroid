package com.j4dream.riandroid;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.j4dream.riandroid.service.WebCallback;
import com.j4dream.riandroid.service.WebRequest;
import com.j4dream.riandroid.service.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class LoginActivity extends Activity implements WebCallback {

    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;
    private Button btnForgotPass;
    private WebRequest  req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        etEmail = (EditText)findViewById(R.id.login_email);
        etPass = (EditText)findViewById(R.id.login_pass);
        btnLogin = (Button)findViewById(R.id.login_btn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String email = etEmail.getText().toString();
            String pass = etPass.getText().toString();
            JSONObject response = null;
            JSONObject requestJSON = new JSONObject();
            try {
                requestJSON.put("name", email);
                requestJSON.put("password", pass);
                WebRequest req = new WebRequest(requestJSON, "users/information.json?csrf_token=");
                req.callback = LoginActivity.this;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            }
        });
    }

    @Override
    public void finishRequest(JSONObject json) {
        Log.i("tag",json.toString());
        try {
            util.simpleToast(this,json.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
