package com.example.onetonline.broadcast;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.example.onetonline.business.SyncUseCase;
import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.UserRepo;

public class SyncService extends Service {
    private NetworkReceiver networkReceiver;
    private UserRepo userRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        UserRepo userRepo = new UserRepo(this);
        AvatarManager avatarManager = new AvatarManager(this);
        SyncUseCase syncUseCase = new SyncUseCase(userRepo, new AvatarRepo(), avatarManager, this);
        networkReceiver = new NetworkReceiver(syncUseCase);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
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
        }
        if (userRepo != null){
            userRepo.close();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
