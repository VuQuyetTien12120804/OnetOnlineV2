package com.example.list_view_basic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    String myphone[] = {"Điện thoại Samsung", "Điện thoại Iphone", "Điện thoại Xiaomi", "Điện thoại Bphone", "Điện thoại Nokia"};
    ArrayAdapter<String> myadapter;
    ListView lv1;
    TextView txtselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtselect = findViewById(R.id.txtselect);
        lv1 = findViewById(R.id.lv1);

        myadapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, myphone);
        lv1.setAdapter(myadapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtselect.setText("Vị trí: "+position+" "+myphone[position]);
            }
        });
    }
}