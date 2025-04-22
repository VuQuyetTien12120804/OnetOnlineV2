package com.example.lab1_lan2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtname, edtnumber;
    RadioGroup radgroup;
    Button btnadd;

    ArrayList<String> lvlist;
    ListviewAdapter lvadapter;
    ListView lv;

    ArrayList<String> splist;
    SpinnerAdapter spadapter;
    Spinner sp;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtname = findViewById(R.id.edtname);
        edtnumber = findViewById(R.id.edtnumber);
        radgroup = findViewById(R.id.radgroup);
        btnadd = findViewById(R.id.btnadd);
        sp = findViewById(R.id.sp);
        lv = findViewById(R.id.lv);

        splist = new ArrayList<>();
        splist.add("Ha Noi");
        splist.add("Hai Phong");
        splist.add("Lao Cai");
        splist.add("Yen Bai");
        spadapter = new SpinnerAdapter(MainActivity.this, R.layout.layoutspinner, splist);
        sp.setAdapter(spadapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = splist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                address = "";
            }
        });

        lvlist = new ArrayList<>();
        lvlist.add("tung");
        lvadapter = new ListviewAdapter(MainActivity.this, R.layout.layoutlistview, lvlist);
        lv.setAdapter(lvadapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String number = edtnumber.getText().toString();

                int id = radgroup.getCheckedRadioButtonId();
                RadioButton radselected = radgroup.findViewById(id);
                String gioitinh = radselected.getText().toString();

                lvlist.add(name+"-"+gioitinh+"-"+number+"-"+address);
                lvadapter.notifyDataSetChanged();
            }
        });
    }
}