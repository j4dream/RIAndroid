package com.j4dream.riandroid;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dream on 2014/11/9.
 */
public class util {
    public static void simpleToast(Context context,String text) {
        Toast.makeText(context, text,
                Toast.LENGTH_SHORT).show();
    }
}
