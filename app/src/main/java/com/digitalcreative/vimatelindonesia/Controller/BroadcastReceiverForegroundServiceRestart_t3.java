package com.digitalcreative.vimatelindonesia.Controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverForegroundServiceRestart_t3 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(BroadcastReceiverForegroundServiceRestart_t3.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, ForegroundService_t3.class));;
    }
}