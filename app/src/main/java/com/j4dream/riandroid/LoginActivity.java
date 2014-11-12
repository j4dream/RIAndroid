package com.j4dream.riandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j4dream.riandroid.service.WebService;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity {

    private EditText etEmail;
    private EditText etPass;
    private Button btnLogin;
    private Button btnForgotPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                WebService web = new WebService();
                JSONObject requestJSON = new JSONObject();
                try {
                    requestJSON.put("name", email);
                    requestJSON.put("password", pass);
                    response = web.doPost(requestJSON, "users/information.json?csrf_token=");
                    if (response.getInt("code") == 1) {
                       util.simpleToast(LoginActivity.this,"Login fail：" + response.getString("message"));
                    } else if (response.getInt("code") == 0) {
                        util.simpleToast(LoginActivity.this,"Login fail");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
