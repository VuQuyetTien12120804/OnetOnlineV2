package com.example.onetonline.broadcast;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.onetonlinev2.R;

public class MusicGameService extends Service {
    MediaPlayer musicGame;
    public MusicGameService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //hàm khởi tạo service
    @Override
    public void onCreate() {
        super.onCreate();
        musicGame = MediaPlayer.create(this, R.raw.game_music);
        musicGame.setLooping(true);
    }

    //hàm chạy service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        musicGame.start();
        return super.onStartCommand(intent, flags, startId);
    }

    //hàm dừng service
    @Override
    public void onDestroy() {
        super.onDestroy();
        musicGame.stop();
    }
}