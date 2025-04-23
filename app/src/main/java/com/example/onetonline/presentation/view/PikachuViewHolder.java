package com.example.onetonline.presentation.view;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonlinev2.R;

public class PikachuViewHolder extends RecyclerView.ViewHolder {
    private ImageButton imagePikachu;
    private View overlay;
    public PikachuViewHolder(@NonNull View itemView) {
        super(itemView);
        imagePikachu = itemView.findViewById(R.id.imgPikachu);
        overlay = itemView.findViewById(R.id.overlay);
    }
    public void bind(Pikachu pikachu){
        int number = pikachu.getImageID();
        if (number == 0){
            imagePikachu.setVisibility(View.GONE);
            overlay.setVisibility(View.GONE);
        }else {
            imagePikachu.setVisibility(View.VISIBLE);
            String imageName = "ic_" + number;
            int resID = itemView.getResources().getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());
            imagePikachu.setImageResource(resID);
            overlay.setVisibility(pikachu.isSelected() ? View.VISIBLE : View.GONE);
            Log.d("ViewHolder: ", "Pikachu at (" + pikachu.getxPoint() + "," + pikachu.getyPoint() + ")");
        }
    }
}
