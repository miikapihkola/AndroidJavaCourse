package com.example.androidjavacourse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AirplanemodeReceiver extends BroadcastReceiver {

    public static final String TAG ="ApmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        boolean state = intent.getBooleanExtra("state", false);
        Log.d(TAG,"Apm change detected, State: " + state);

        if(state) {
            Toast.makeText(context, context.getResources().getString(R.string.apm_state_true), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, context.getResources().getString(R.string.apm_state_false), Toast.LENGTH_LONG).show();
        }
    }
}