package com.digitalcreative.aplikasidatamining.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverForegroundServiceRestart_t0 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Service Stops! Oooooooooooooppppssssss!!!!");

        Log.i(BroadcastReceiverForegroundServiceRestart_t0.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, ForegroundService_t0.class));;
    }
}