package com.example.onetonline.presentation.view;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.business.Matrix;
import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.utils.Constants;
import com.example.onetonlinev2.R;
import com.example.onetonline.presentation.controller.*;

import java.util.ArrayList;
import java.util.List;


public class GamePlay extends BaseActivity implements GamePlayView{
    private PikachuAdapter pikachuAdapter;
    private List<Pikachu> pikachuList;
    private Matrix matrix;
    private int[][] pikachuMap;
    private TextView txtLevel, txtSearch, txtSwitch, txtPoint;
    private ImageButton btnSearch, btnSwitch, btnX;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        RecyclerView recyclerView = findViewById(R.id.recycleView);

        pikachuAdapter = new PikachuAdapter(pikachuList, matrix);
        recyclerView.setAdapter(pikachuAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.MAP_COL));
    }
    public void initWidget(){
        matrix = new Matrix(Constants.MAP_ROW + 2, Constants.MAP_COL + 2);
        pikachuList = new ArrayList<>();
        btnSearch = findViewById(R.id.btnSearch);
        btnSwitch = findViewById(R.id.btnSwitch);
        btnX = findViewById(R.id.btnX);
        progressBar = findViewById(R.id.progressBar);
        txtLevel = findViewById(R.id.txtLevel);
        txtSearch = findViewById(R.id.txtSearch);
        txtSwitch = findViewById(R.id.txtSwitch);
        txtPoint = findViewById(R.id.txtPoint);
    }

    @Override
    public void onButtonXClick() {

    }

    @Override
    public void onButtonSearchClick() {

    }

    @Override
    public void onButtonSwitchClick() {

    }
}
