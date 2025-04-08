package com.example.onetonline.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.example.onetonlinev2.R;

public class DialogExitConfirm {
    public static void showExitConfirmationDialog(Context context, ExitDialogListener listener) {
        // Inflate the dialog view
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_exit_confirmation, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        // Apply animations and border
        if (dialog.getWindow() != null) {
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogBounceAnimation;
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_border);
        }

        // Bind views
        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        // Handle "No" button click
        btnNo.setOnClickListener(view -> dialog.dismiss());

        // Handle "Yes" button click
        btnYes.setOnClickListener(view -> {
            dialog.dismiss();
            if (listener != null) {
                listener.onExitConfirmed();
            }
        });

        // Show dialog
        dialog.show();
    }

    public interface ExitDialogListener {
        void onExitConfirmed();
    }
}
