package com.j4dream.riandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.j4dream.riandroid.view.DBList;
import com.j4dream.riandroid.view.SildingMenu;


public class MainActivity extends Activity {

    private SildingMenu mLeftMenu;
    private DBList dbListMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mLeftMenu = (SildingMenu)findViewById(R.id.id_menu);
    }

    public void toggleMemu(View view){
        mLeftMenu.toggle();
    }

    public void clickLeftMemuItem(View view) {
        
        util.simpleToast(this, "Click Item");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
