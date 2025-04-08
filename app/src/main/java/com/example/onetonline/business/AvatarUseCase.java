package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.DEFAULT_AVATAR_FILENAME;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AvatarUseCase {
    private final AvatarManager avatarManager;
    private final AvatarRepo avatarRepo;
    private final Context context;

    public AvatarUseCase(Context context) {
        this.context = context;
        this.avatarManager = new AvatarManager(context);
        this.avatarRepo = new AvatarRepo();
    }

    public interface AvatarCallBack {
        void onSuccess(Bitmap bitmap);
        void onFailure(String err);
    }

    public void loadAvatarFromServer(String userName, AvatarCallBack callBack){
        avatarRepo.getAvatar(userName, new AvatarRepo.GetCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                avatarManager.saveImage(bitmap, DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        callBack.onSuccess(bitmap);
                    }

                    @Override
                    public void onFailure(String err) {
                        callBack.onFailure(err);
                    }
                });
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure(err);
            }
        });
    }

    public void loadAvatarFromLocal(AvatarCallBack callback) {
        avatarManager.loadImage(DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callback.onSuccess(bitmap);
            }
            @Override
            public void onFailure(String err) {
                callback.onFailure(err);
            }
        });
    }

    public void saveAvatar(Bitmap bitmap, String userName, AvatarCallBack callBack) {
        avatarManager.saveImage(bitmap, DEFAULT_AVATAR_FILENAME, new AvatarManager.AvatarCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                callBack.onSuccess(bitmap);
                ExecutorService executor = Executors.newFixedThreadPool(1);
                executor.submit(() -> {
                    avatarRepo.deleteAvatar(userName, new AvatarRepo.DeleteCallBack() {
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
                        avatarRepo.uploadAvatar(userName, avatarFile, new AvatarRepo.UploadCallBack() {
                            @Override
                            public void onSuccess() {
                                Log.i("AvatarUseCase", "Avatar uploaded successfully");
                            }

                            @Override
                            public void onFailure(String err) {
                                callBack.onFailure("Failed to upload: " + err);
                            }
                        });
                    }
                });
                executor.shutdown();
            }

            @Override
            public void onFailure(String err) {
                callBack.onFailure("Failed to save image: " + err);
            }
        });

    }
}
