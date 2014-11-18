package com.j4dream.riandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Dream on 2014/11/9.
 */
public class util {
    public static void simpleToast(Context context,String text) {
        Toast.makeText(context, text,
                Toast.LENGTH_SHORT).show();
    }

    public static void setCookies(Context context, String sessionId) {
        SharedPreferences sp = context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("cookies",sessionId);
        editor.commit();
    }

    public static String getCookies(Context context) {
        SharedPreferences sp = context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        return  sp.getString("cookies", "");
    }

    public static void setCsrf(Context context, String csrf) {
        SharedPreferences sp = context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("csrf",csrf);
        editor.commit();
    }
    public static String getCsrf(Context context) {
        SharedPreferences sp = context.getSharedPreferences("cookies",Context.MODE_PRIVATE);
        return  sp.getString("csrf", "");
    }
}
