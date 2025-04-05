package com.example.onetonline.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AvatarManager {
    private final Context context;

    public AvatarManager(Context context) {
        this.context = context;
    }

    public interface AvatarCallback {
        void onSuccess(Bitmap bitmap);
        void onFailure(String err);
    }

    public void saveImage(Bitmap bitmap, String fileName, AvatarCallback callback) {
        File file = new File(context.getFilesDir(), fileName + ".png");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            callback.onSuccess(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure("Failed to save image: " + e.getMessage());
        }
    }

    public void loadImage(String fileName, AvatarCallback callback) {
        File file = new File(context.getFilesDir(), fileName + ".png");
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap != null) {
                callback.onSuccess(bitmap);
            } else {
                callback.onFailure("Failed to decode image");
            }
        } else {
            callback.onFailure("Image not found");
        }
    }
}