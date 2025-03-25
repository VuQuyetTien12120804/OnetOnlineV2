package com.example.onetonline.presentation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.onetonlinev2.R;

public class DialogHelper {
    public static void showScrollableAlertDialog(Context context) {
        // Tạo View từ layout
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_scrollable, null);

        // Khởi tạo AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Tạo AlertDialog
        AlertDialog dialog = builder.create();

        // Áp dụng hiệu ứng cho dialog
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;

        // Ánh xạ các thành phần
        TextView tvTitle = dialogView.findViewById(R.id.tvTitle);
        Button btnClose = dialogView.findViewById(R.id.btnClose);

        // Cài đặt nội dung
        tvTitle.setText("Helper");

        // Áp dụng viền cho AlertDialog
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_border);

        // Xử lý nút đóng
        btnClose.setOnClickListener(view -> dialog.dismiss());

        // Hiển thị AlertDialog
        dialog.show();
    }
}
