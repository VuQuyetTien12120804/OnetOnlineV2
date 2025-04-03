package com.example.onetonline.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AvatarManager {
    public static void saveImage(Context context, Bitmap bitmap, String fileName) {
        // Tạo file trong thư mục internal storage
        File file = new File(context.getFilesDir(), fileName + ".png");

        try {
            // Mở luồng ghi file
            FileOutputStream outputStream = new FileOutputStream(file);

            // Nén và ghi bitmap vào file
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            // Đóng luồng
            outputStream.flush();
            outputStream.close();

            // In đường dẫn để kiểm tra (tùy chọn)
            System.out.println("Ảnh đã được lưu tại: " + file.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImage(Context context, String fileName) {
        // Lấy đường dẫn file từ Internal Storage
        File file = new File(context.getFilesDir(), fileName + ".png");

        // Kiểm tra file có tồn tại không
        if (file.exists()) {
            // Đọc file thành Bitmap
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            return null;
        }
    }
}
