package com.example.intent_filter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    WebView myweb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myweb = findViewById(R.id.myweb);

        Intent myintent = getIntent();
        Uri mylink = myintent.getData();
        try{
            myweb.loadUrl(mylink.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}