package com.example.onetonline.business;

import static com.example.onetonline.utils.Constants.DEFAULT_AVATAR_FILENAME;

import android.graphics.Bitmap;

import com.example.onetonline.broadcast.NetworkReceiver;
import com.example.onetonline.broadcast.SyncService;
import com.example.onetonline.data.AvatarManager;
import com.example.onetonline.data.AvatarRepo;
import com.example.onetonline.data.User;
import com.example.onetonline.data.UserRepo;

import java.io.File;

public class SyncUseCase implements NetworkReceiver.SyncTrigger {
    private final UserRepo userRepo;
    private final AvatarRepo avatarRepo;
    private final AvatarManager avatarManager;

    public SyncUseCase(UserRepo userRepo, AvatarRepo avatarRepo, AvatarManager avatarManager) {
        this.userRepo = userRepo;
        this.avatarRepo = avatarRepo;
        this.avatarManager = avatarManager;
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
                        File avatarFile = avatarManager.convertBitmapToFile(bitmap, DEFAULT_AVATAR_FILENAME);
                        avatarRepo.uploadAvatar(user.id(), avatarFile, new AvatarRepo.UploadCallBack() {
                            @Override
                            public void onSuccess() {

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

            @Override
            public void onFailure(String err) {
                if(err.equals("409")){
                    String token = "Bearer" + userRepo.getToken();
                    userRepo.getUser(token, new UserRepo.GetUserCallBack() {
                        @Override
                        public void onSuccess(User user) {
                            userRepo.updateToLocal(user);
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
}
