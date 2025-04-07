package com.example.onetonline.presentation.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonlinev2.R;

public class PikachuViewHolder extends RecyclerView.ViewHolder {
    ImageButton imagePikachu;
    Pikachu pikachu;
    public PikachuViewHolder(@NonNull View itemView) {
        super(itemView);
        imagePikachu = itemView.findViewById(R.id.imgPikachu);
        imagePikachu.setOnClickListener(v -> {
            Toast.makeText(itemView.getContext(), "Pikachu id: " + pikachu.getImageID() + "Position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        });
    }
    public void bind(Pikachu pikachu){
        this.pikachu = pikachu;
        int number = pikachu.getImageID();
        if (number == 0){
            imagePikachu.setVisibility(View.INVISIBLE);
        }else {
            imagePikachu.setVisibility(View.VISIBLE);
            String imageName = "ic_" + number;
            int resID = itemView.getResources().getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());
            imagePikachu.setImageResource(resID);
        }

    }
}
