package com.j4dream.riandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.j4dream.riandroid.service.WebCallback;
import com.j4dream.riandroid.service.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;


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
            JSONObject requestJSON = new JSONObject();
            try {
                requestJSON.put("j_username", email);
                requestJSON.put("j_password", pass);
                WebRequest req = new WebRequest(getApplicationContext(), requestJSON, "j_spring_security_check.json", "post");
                req.callback = LoginActivity.this;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            }
        });

        btnForgotPass = (Button) findViewById(R.id.fogot_pass_btn);
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebRequest req = new WebRequest(getApplicationContext(), null, "lists/5465ba78e4b0a90bb0a0cd09/campaigns.json","get");
                req.callback = LoginActivity.this;
            }
        });
    }

    @Override
    public void finishRequest(JSONObject json){
        try {
            if(json.getInt("code") == 0) {
                util.simpleToast(this, "Login success.");
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
