package com.example.onetonline.presentation.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onetonline.presentation.BaseActivity;
import com.example.onetonline.presentation.controller.GamePlayController;
import com.example.onetonline.presentation.controller.PikachuAdapter;
import com.example.onetonline.presentation.model.GameBoardState;
import com.example.onetonline.presentation.model.Pikachu;
import com.example.onetonline.utils.Constants;
import com.example.onetonlinev2.R;

import java.util.Arrays;
import java.util.List;


public class GamePlay extends BaseActivity implements GamePlayListener {

    private GamePlayController gamePlayController;
    private PikachuAdapter adapter;
    private EditText txtLevel, txtSearch, txtSwitch;
    private TextView txtPoint;
    private ImageButton btnSearch, btnSwitch, btnX;
    private ProgressBar progressBar;

    private boolean isClassic;
    private boolean isContinue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initWidgets();
        isClassic = getIntent().getBooleanExtra("isClassic", false);
        isContinue = getIntent().getBooleanExtra("isContinue", false);
        gamePlayController = new GamePlayController(this);
        gamePlayController.initGame(isClassic, isContinue);

        btnSwitch.setOnClickListener(v -> gamePlayController.shuffleMap());
        btnSearch.setOnClickListener(v -> gamePlayController.handleSearchClick());
        btnX.setOnClickListener(v -> gamePlayController.handleXClick());

        // Lắng nghe thay đổi nội dung txtSearch
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("GamePlay", "txtSearch changed to: " + s.toString());
                gamePlayController.handleTxTSearch();
            }
        });

        // Lắng nghe thay đổi nội dung txtSwitch
        txtSwitch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("GamePlay", "txtSwitch changed to: " + s.toString());
                gamePlayController.handleTxtSwitch();
            }
        });

        txtLevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("GamePlay", "txtSwitch changed to: " + s.toString());
                gamePlayController.handleTxtLevel();
            }
        });
    }
    public void initWidgets(){
        btnSearch = findViewById(R.id.btnSearch);
        btnSwitch = findViewById(R.id.btnSwitch);
        btnX = findViewById(R.id.btnX);
        progressBar = findViewById(R.id.progressBar);
        txtLevel = findViewById(R.id.txtLevel);
        txtSearch = findViewById(R.id.txtSearch);
        txtSwitch = findViewById(R.id.txtSwitch);
        txtPoint = findViewById(R.id.txtPoint);
    }

    @SuppressLint({"NotifyDataSetChanged", "DefaultLocale"})
    @Override
    public void setUpBoard(GameBoardState state) {
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        if (adapter == null) {
            adapter = new PikachuAdapter(state.getPikachuList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.MAP_COL));
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setOnPikachuClickListener(position -> {
            Log.d("GamePlay", "Pikachu clicked at position: " + position);
            gamePlayController.handlePikachuClick(position);
        });

        Log.d("GamePlay matrix: ", Arrays.deepToString(state.getMatrix()));
        Log.d("GamePlay pikachu: ", state.getPikachuList().toString());
        txtLevel.setText(String.format("%02d", state.getLevel()));
        txtSearch.setText(String.format("%02d", state.getSearchCount()));
        txtSwitch.setText(String.format("%02d", state.getSwitchCount()));
        txtPoint.setText(String.valueOf(state.getScore()));
    }
    public void setScore(int score){
        txtPoint.setText(String.valueOf(score));
    }
    public void setLevel(int level){
        txtLevel.setText(String.format("%02d", level));
    }
    @Override
    public void onButtonXClick() {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onButtonSearchClick(GameBoardState state) {
        txtSearch.setText(String.format("%02d", state.getSearchCount()));
    }

    @Override
    public void txtSearchListener(GameBoardState state) {
        if (state.getSearchCount() == 0){
            btnSearch.setEnabled(false);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onButtonSwitchClick(GameBoardState state) {
        txtSwitch.setText(String.format("%02d", state.getSwitchCount()));
        System.out.println("switch count: " + state.getSwitchCount() );
    }

    @Override
    public void txtSwitchListener(GameBoardState state) {
        if (state.getSwitchCount() == 0){
            btnSwitch.setEnabled(false);
        }
    }


    public PikachuAdapter getAdapter() {
        return adapter;
    }
}
