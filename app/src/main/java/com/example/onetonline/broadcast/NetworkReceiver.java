package com.example.onetonline.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkReceiver extends BroadcastReceiver {
    private SyncTrigger syncTrigger;

    public NetworkReceiver(SyncTrigger syncTrigger) {
        this.syncTrigger = syncTrigger;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isAvailable()){
            syncTrigger.sync();
        }
    }

    public interface SyncTrigger{
        void sync();
    }
}
