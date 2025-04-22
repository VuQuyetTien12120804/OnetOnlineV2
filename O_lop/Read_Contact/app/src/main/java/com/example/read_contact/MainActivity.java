package com.example.read_contact;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnread, btnreadsms, btncalllog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWigets();
        getPermission();
        getPermission1();
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Show_Contact.class);
                startActivity(intent);
            }
        });
        btnreadsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Show_sms.class);
                startActivity(intent);
            }
        });
        btncalllog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = new String[]{
                        CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION
                };
                Cursor c3 = getContentResolver().query(CallLog.CONTENT_URI, projection, CallLog.Calls.DURATION+ "<?",
                        new String[]{"10"}, CallLog.Calls.DATE+"asc");
                c3.moveToFirst();
                String s = "";
                while(!c3.isAfterLast()){
                    for (int i=0;i<c3.getColumnCount();i++){
                        s = s+c3.getString(i) +"-";
                    }
                    s = s + "\n";
                    c3.moveToNext();
                }
                c3.close();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);
            return;
        }
    }

    private void getPermission1() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_SMS}, 1);
            return;
        }
    }

    private void initWigets() {
        btnread = findViewById(R.id.btnread);
        btnreadsms = findViewById(R.id.btnreadsms);
        btncalllog = findViewById(R.id.btncalllog);
    }
}