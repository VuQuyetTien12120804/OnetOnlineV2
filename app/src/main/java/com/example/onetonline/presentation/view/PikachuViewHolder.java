package com.example.onetonline.presentation.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.presentation.model.Pikachu;

public class PikachuViewHolder extends RecyclerView.ViewHolder {
    Pikachu pikachu;
    public PikachuViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void bind(Pikachu pikachu){
        this.pikachu = pikachu;
    }
}
