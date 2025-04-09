package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.DEFAULT_AVATAR_FILENAME;
import static com.example.onetonline.utils.Constants.SYNC_SUCCESS_ACTION;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.onetonline.broadcast.NetworkReceiver;
import android.graphics.Bitmap;
import com.example.onetonline.broadcast.NetworkReceiver;
import com.example.onetonline.broadcast.SyncService;
import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncUseCase implements NetworkReceiver.SyncTrigger {
    private final UserRepo userRepo;
    private final AvatarRepo avatarRepo;
    private final AvatarManager avatarManager;
    private final Context context;

    public SyncUseCase(UserRepo userRepo, AvatarRepo avatarRepo, AvatarManager avatarManager, Context context) {
        this.userRepo = userRepo;
        this.avatarRepo = avatarRepo;
        this.avatarManager = avatarManager;
        this.context = context;
    }

    @Override
    public void sync() {
        User user = userRepo.getUserLocal();
        userRepo.sync(user, new UserRepo.UpdateUserCallBack() {
            @Override
            public void onSuccess() {
                avatarManager.loadImage(DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        ExecutorService executor = Executors.newFixedThreadPool(1);
                        executor.submit(() -> {
                            avatarRepo.deleteAvatar(user.userName(), new AvatarRepo.DeleteCallBack() {
                                @Override
                                public void onSuccess() {
                                    Log.i("AvatarUseCase", "Old avatar deleted");
                                }

                                @Override
                                public void onFailure(String err) {
                                    Log.e("AvatarUseCase", "Failed to delete avatar: " + err);
                                }
                            });
                            File avatarFile = avatarManager.convertBitmapToFile(bitmap, DEFAULT_AVATAR_FILENAME);
                            if (avatarFile != null) {
                                avatarRepo.uploadAvatar(user.userName(), avatarFile, new AvatarRepo.UploadCallBack() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("AvatarUseCase", "Avatar uploaded successfully");
                                    }

                                    @Override
                                    public void onFailure(String err) {
                                        Log.i("AvatarUseCase", "Avatar uploaded failed");
                                    }
                                });
                            }
                        });
                        executor.shutdown();
                    }

                    @Override
                    public void onFailure(String err) {

                    }
                });
            }

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    String token = "Bearer " + userRepo.getToken();
                    userRepo.getUser(token, new UserRepo.GetUserCallBack() {
                        @Override
                        public void onSuccess(User user) {
                            userRepo.updateToLocal(user);
                            onSyncSuccess();
                        }

                        @Override
                        public void onFailure(String err) {

                        }
                    });
                    avatarRepo.getAvatar(user.id(), new AvatarRepo.GetCallBack() {
                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            avatarManager.saveImage(bitmap, DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
                                @Override
                                public void onSuccess(Bitmap bitmap) {

                                }

                                @Override
                                public void onFailure(String err) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(String err) {

                        }
                    });
                }
            }
        });
    }

    public void onSyncSuccess(){
        Intent i = new Intent(SYNC_SUCCESS_ACTION);
        LocalBroadcastManager.getInstance(context). sendBroadcast(i);
    }
}
