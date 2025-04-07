package com.example.onetonline.presentation.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.business.Matrix;
import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.presentation.view.PikachuViewHolder;
import com.example.onetonline.utils.Constants;
import com.example.onetonlinev2.R;

import java.util.ArrayList;
import java.util.List;

public class PikachuAdapter extends RecyclerView.Adapter<PikachuViewHolder> {
    private List<Pikachu> pikachuList;
    private Matrix matrix;
    private OnPikachuClickListener onPikachuClickListener;
    public interface OnPikachuClickListener{
        public void onPikachuClick(int position);
    }
    public PikachuAdapter(List<Pikachu> list, Matrix matrix) {
        this.pikachuList = list;
        this.matrix = matrix;
        pikachuList = new ArrayList<>();
        int[][] gameMatrix = matrix.getMatrix();
        for (int i = 0; i < Constants.MAP_ROW + 2; i++) {
            for (int j = 0; j < Constants.MAP_COL + 2; j++) {
                pikachuList.add(new Pikachu(gameMatrix[i][j]));
            }
        }
    }

    @NonNull
    @Override
    public PikachuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pikachu, parent, false);
        return new PikachuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PikachuViewHolder holder, int position) {
        Pikachu pikachu = pikachuList.get(position);
        holder.bind(pikachu);

    }

    @Override
    public int getItemCount() {
        return pikachuList.size();
    }
}
