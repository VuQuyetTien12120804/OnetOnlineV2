package com.example.onetonline.broadcast;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.onetonline.business.LoginUseCase;
import com.example.onetonline.business.SyncUseCase;
import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.OTPRepo;
import com.example.onetonline.data.UserRepo;

public class SyncService extends Service {
    private NetworkReceiver networkReceiver;
    private LoginUseCase loginUseCase;

    @Override
    public void onCreate() {
        super.onCreate();
        UserRepo userRepo = new UserRepo(this);
        AvatarManager avatarManager = new AvatarManager(this);
        SyncUseCase syncUseCase = new SyncUseCase(userRepo, new AvatarRepo(), avatarManager);
        networkReceiver = new NetworkReceiver(syncUseCase);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
        Log.i("SyncService", "Service started and NetworkReceiver registered");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null) {
            unregisterReceiver(networkReceiver);
            Log.i("SyncService", "Service stopped and NetworkReceiver unregistered");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
