package com.example.demologin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText txtemail, txtpass;
    Button btnSigup;
    SQLiteDatabase mydatabase;
    User user;

    @Override
    protected void onStart() {
        super.onStart();
        Cursor c = mydatabase.query("qluser", null, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            Toast.makeText(MainActivity.this, "Tài khoản đăng nhập thành công", Toast.LENGTH_SHORT).show();
            String email = c.getString(0);
            String pass = c.getString(1);
            int level = c.getInt(2);
            int isDeleted = c.getInt(3);
            user = new User(email, pass, level, isDeleted);
            c.close();
            Intent myintent = new Intent(MainActivity.this, ManHinhGame.class);
            myintent.putExtra("user", user);
            startActivity(myintent);
        } else {
            Toast.makeText(MainActivity.this, "Hãy đăng nhập tài khoản", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtemail = findViewById(R.id.txtemail);
        txtpass = findViewById(R.id.txtpass);
        btnSigup = findViewById(R.id.btnSignup);

        mydatabase = openOrCreateDatabase("qluser.db", MODE_PRIVATE, null);
        try {
            String sql = "CREATE TABLE IF NOT EXISTS qluser(" +
                    "email TEXT PRIMARY KEY, " +
                    "password TEXT, " +
                    "level INTEGER, " +
                    "isDeleted INTEGER)";
            mydatabase.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", "Bảng đã tồn tại");
        }

        nhanIntent();

        btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("email", email);
                contentValues.put("password", pass);
                contentValues.put("level", 1);
                contentValues.put("isDeleted", 0);

                String msg;
                if (mydatabase.insert("qluser", null, contentValues) == -1) {
                    msg = "Đăng ký tài khoản không thành công";
                } else {
                    msg = "Đăng ký tài khoản thành công";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                onStart();
            }
        });
    }

    private void nhanIntent() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("userSignOut");
        if (user != null && user.getDeleted() == 1) {
            int n = mydatabase.delete("qluser", "email = ?", new String[]{user.getEmail()});
            if (n > 0) {
                Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đăng xuất không thành công", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
