package com.example.retrofit_baisic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retrofit_baisic.api.ApiService;
import com.example.retrofit_baisic.model.Currentcy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txtsuccess, txtcode, txtinfo;
    Button btncallapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtsuccess = findViewById(R.id.txtsuccess);
        txtcode = findViewById(R.id.txtcode);
        txtinfo = findViewById(R.id.txtinfo);
        btncallapi = findViewById(R.id.btncallapi);

        btncallapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickApi();
            }
        });
    }

    //https://api.github.com/users/1
    private void clickApi() {
        ApiService.apiservice.convertUsdtoVnd1().enqueue(new Callback<Currentcy>() {
            @Override
            public void onResponse(Call<Currentcy> call, Response<Currentcy> response) {
                Toast.makeText(MainActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();
                Currentcy currentcy = response.body();
                if (currentcy != null){
                    txtsuccess.setText(currentcy.getId()+"");
                    txtcode.setText(currentcy.getNode_id()+"");
                    txtinfo.setText(currentcy.getFollowers_url());
                }
            }

            @Override
            public void onFailure(Call<Currentcy> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}