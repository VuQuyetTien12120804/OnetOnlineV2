package com.example.onetonline.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.onetonline.data.User;
import com.example.onetonline.data.userRanking;
import com.example.onetonlinev2.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<userRanking> {
    public UserAdapter(Context context, List<userRanking> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Lấy dữ liệu của User tại vị trí hiện tại
        userRanking user = getItem(position);

        // Kiểm tra nếu view có thể tái sử dụng, nếu không thì inflate layout mới
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_player, parent, false);
        }

        // Ánh xạ các thành phần trong layout
        TextView tvRank = convertView.findViewById(R.id.tvRank);
        ImageView ivAvatar = convertView.findViewById(R.id.ivAvatar);
        TextView tvPlayerName = convertView.findViewById(R.id.tvPlayerName);
        TextView tvPlayerScore = convertView.findViewById(R.id.tvPlayerScore);
        TextView tvLevel = convertView.findViewById(R.id.textViewLevel);

        // Gán dữ liệu từ User vào các view
        tvRank.setText(String.valueOf(position + 1)); // Xếp hạng = vị trí + 1
        // ivAvatar.setImageResource(R.drawable.icon_game); // Mặc định từ XML, hoặc thay bằng logic avatar động
        tvPlayerName.setText(user.userName());
        tvPlayerScore.setText("Score: " + user.score());
        tvLevel.setText(String.valueOf(user.level()));

        return convertView;
    }
}