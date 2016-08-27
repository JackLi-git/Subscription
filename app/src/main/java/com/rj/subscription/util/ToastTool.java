package com.rj.subscription.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by lijunyan on 2016/8/23.
 */
public class ToastTool {
    public static void show(Context context,String s,int i){
        Toast.makeText(context,s,i).show();
    }
}
