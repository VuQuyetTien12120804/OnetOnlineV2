package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtid, edtname, edtnumber;
    Button btninsert, btndelete, btnupdate, btnquery;
    ListView lv;
    ArrayAdapter<String> myadapter;
    ArrayList<String> mylist;
    SQLiteDatabase mydatabase;

    @Override
    protected void onStart() {
        super.onStart();
        Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
        if (c.getCount()>0){
            Toast.makeText(MainActivity.this, "Tai khoan dang nhap thanh cong", Toast.LENGTH_SHORT).show();
            c.moveToFirst();
            String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2);
        }
        else Toast.makeText(MainActivity.this, "Hay dang nhap tai khoan", Toast.LENGTH_SHORT).show();
        c.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtid = findViewById(R.id.edtid);
        edtname = findViewById(R.id.edtname);
        edtnumber = findViewById(R.id.edtnumber);
        btninsert = findViewById(R.id.btninsert);
        btndelete = findViewById(R.id.btndelete);
        btnupdate = findViewById(R.id.btnupdate);
        btnquery = findViewById(R.id.btnquery);
        lv = findViewById(R.id.lv);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(myadapter);

        mydatabase = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);
        try{
            String sql = "CREATE TABLE tbllop(malop TEXT primary key, tenlop TEXT, siso Integer, lop Integer default 1)";
            mydatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error", "Table đã tồn tại");
        }

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtid.getText().toString();
                String tenlop = edtname.getText().toString();
                int siso = Integer.parseInt(edtnumber.getText().toString());

                insert(malop, tenlop, siso);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtid.getText().toString();
                int n = mydatabase.delete("tbllop", "malop = ?", new String[]{malop});
                String msg = "";
                if (n==0){
                    msg = "No Record to Delete";
                }else{
                    msg = n + "Record is deleted";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int siso = Integer.parseInt(edtnumber.getText().toString());
                String malop = edtid.getText().toString();
                ContentValues contentValues = new ContentValues();
                contentValues.put("siso", siso);
                int n = mydatabase.update("tbllop", contentValues, "malop  = ?", new String[]{malop});
                String msg = "";
                if (n==0){
                    msg = "No Record to Update";
                }else{
                    msg = n + "Record is updated";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();
                Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
                c.moveToFirst();
                String data = "";
                while(c.isAfterLast() == false){
                    data = c.getString(0) + " - " + c.getString(1) + " - " +
                            c.getString(2);
                    c.moveToNext();
                    mylist.add(data);
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });
    }

    public void insert(String malop, String tenlop, int siso){
        ContentValues contentValues = new ContentValues();
        contentValues.put("malop", malop);
        contentValues.put("tenlop", tenlop);
        contentValues.put("siso", siso);

        String msg = "";
        if (mydatabase.insert("tbllop", null, contentValues) == -1){
            msg = "Fail to Insert Record";
        }else{
            msg = "Insert Record Sucessfully";
        }
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}