package com.example.onetonline.broadcast;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.onetonline.business.LoginUseCase;
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
        loginUseCase = new LoginUseCase(userRepo, new OTPRepo(), new AvatarRepo());
        networkReceiver = new NetworkReceiver(loginUseCase);
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
