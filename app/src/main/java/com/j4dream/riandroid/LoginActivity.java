package com.j4dream.riandroid;

import android.app.Activity;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


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
                requestJSON.put("j_username", "dream.long@radicasys.com");
                requestJSON.put("j_password", "123");
                WebRequest req = new WebRequest(requestJSON, "j_spring_security_check.json?csrf_token=");
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
                new NetPing().execute();
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

    //ping
    public String Ping(String str) {
        String resault = "";
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());

            if (status == 0) {
                resault = "success";
            } else {
                resault = "faild";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return resault;
    }

    private class NetPing extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = "";
            s = Ping("app.rimanggis.com");
            Log.i("ping", s);
            return s;
        }
    }

}
