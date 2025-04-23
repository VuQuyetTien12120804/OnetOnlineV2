package com.example.onetonline.presentation.controller;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.presentation.view.PikachuViewHolder;
import com.example.onetonline.utils.Constants;
import com.example.onetonlinev2.R;

import java.util.ArrayList;
import java.util.List;

public class PikachuAdapter extends RecyclerView.Adapter<PikachuViewHolder> {
    private List<Pikachu> pikachuList;
    private OnPikachuClickListener onPikachuClickListener;
    public interface OnPikachuClickListener{
        public void onPikachuClick(int position);
    }
    public void setOnPikachuClickListener(OnPikachuClickListener listener){
        this.onPikachuClickListener = listener;
        Log.d("PikachuAdapter", "setOnPikachuClickListener: " + (listener != null));
    }

    public PikachuAdapter(List<Pikachu> list) {
        this.pikachuList = list;
    }

    public void updatePikachuList(List<Pikachu> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return pikachuList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Pikachu oldPikachu = pikachuList.get(oldItemPosition);
                Pikachu newPikachu = newList.get(newItemPosition);
                return oldPikachu != null && newPikachu != null &&
                        oldPikachu.getxPoint() == newPikachu.getxPoint() &&
                        oldPikachu.getyPoint() == newPikachu.getyPoint();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Pikachu oldPikachu = pikachuList.get(oldItemPosition);
                Pikachu newPikachu = newList.get(newItemPosition);
                return oldPikachu != null && newPikachu != null &&
                        oldPikachu.getImageID() == newPikachu.getImageID() &&
                        oldPikachu.isSelected() == newPikachu.isSelected();
            }
        });
        pikachuList = new ArrayList<>(newList);
        diffResult.dispatchUpdatesTo(this);
        Log.d("GamePlayAdapter", "Updated pikachuList, size: " + pikachuList.size());
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
        holder.itemView.setOnClickListener(v -> {
            Log.d("PikachuAdapter", "Click event triggered for position: " + position);
            if (onPikachuClickListener != null) {
                Log.d("PikachuAdapter", "Clicked position: " + position);
                onPikachuClickListener.onPikachuClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return pikachuList.size();
    }

}
