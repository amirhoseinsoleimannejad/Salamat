package com.example.amhso.salamat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.amhso.salamat.service.MessageService;

public class autostart extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1)
    {
//        /****** For Start Activity *****/
//        Intent i = new Intent(context, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

        /***** For start Service  ****/
        Intent myIntent = new Intent(context, MessageService.class);
        context.startService(myIntent);
    }
}
