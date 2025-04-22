package com.example.lab1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtname, edtnumber;
    RadioGroup radgroup;
    CheckBox chksach, chkbao, chkphim;
    Button btnadd;

    ArrayList<String> spinnerlist;
    SpinnerAdapter spadapter;
    Spinner sp;

    ArrayList<String> listview;
    ListviewAdapter lvadapter;
    ListView lv;

    String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtname = findViewById(R.id.edtname);
        edtnumber = findViewById(R.id.edtnumber);
        radgroup = findViewById(R.id.radgroup);
        sp = findViewById(R.id.sp);
        chksach = findViewById(R.id.chksach);
        chkbao = findViewById(R.id.chkbao);
        chkphim = findViewById(R.id.chkphim);
        btnadd = findViewById(R.id.btnadd);
        lv = findViewById(R.id.lv);

        spinnerlist = new ArrayList<>();
        listview = new ArrayList<>();

        spinnerlist.add("Ha Noi");
        spinnerlist.add("Hai Phong");
        spinnerlist.add("Hai Duong");
        spinnerlist.add("Nam Dinh");
        spadapter = new SpinnerAdapter(MainActivity.this, R.layout.layout_spinner, spinnerlist);
        sp.setAdapter(spadapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                address = spinnerlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                address = "";
            }
        });
        listview.add("tung");
        lvadapter = new ListviewAdapter(MainActivity.this, R.layout.layout_listview, listview);
        lv.setAdapter(lvadapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String number = edtnumber.getText().toString();

                int id = radgroup.getCheckedRadioButtonId();
                RadioButton radselected = findViewById(id);
                String gioitinh = radselected.getText().toString();

                String sothich = "";
                if (chksach.isChecked()){
                    sothich += chksach.getText().toString()+" ";
                }
                if (chkbao.isChecked()){
                    sothich += chkbao.getText().toString()+" ";
                }
                if (chkphim.isChecked()){
                    sothich += chkphim.getText().toString();
                }

                listview.add(name+"-"+gioitinh+"-"+number+"-"+address+"-"+sothich);
                lvadapter.notifyDataSetChanged();
            }
        });
    }
}