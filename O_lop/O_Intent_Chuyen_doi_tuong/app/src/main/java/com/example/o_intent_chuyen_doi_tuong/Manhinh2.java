package com.example.o_intent_chuyen_doi_tuong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Manhinh2 extends AppCompatActivity {
    TextView txv;
    Button btnback;
    Intent intent;
     ArrayList<Student> arrayList = new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh2);
        txv = findViewById(R.id.txv);
        btnback = findViewById(R.id.btnback);

        arrayList.add(new Student("abc", 2004));
        arrayList.add(new Student("bc", 2005));
        arrayList.add(new Student("c", 2006));

        intent = getIntent();

        Bundle bundle = intent.getBundleExtra("bundle");

        String ht = bundle.getString("hoten");
        int ns = bundle.getInt("namsinh");

//        Student sv = (Student) bundle.getSerializable("sinhvien");
//        String hoten = sv.getHoten();
//        int namsinh = sv.getNamsinh();

        txv.setText("Ho ten: " + ht + ", Nam sinh: " + ns);

//        Student sv = (Student) intent.getSerializableExtra("sinhvien");
//        String hoten = sv.getHoten();
//        int namsinh = sv.getNamsinh();

//        txv.setText("Ho ten: " + ht + ", Nam sinh: " + ns);

//        for (int i=0;i<arrayList.size();i++){
//            if (arrayList.get(i).getHoten().equalsIgnoreCase(ht)){
//                sv = arrayList.get(i);
//                break;
//            }
//        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(Manhinh2.this, MainActivity.class);
//                intent1.putExtra("namsinh", ns);
//                startActivity(intent1);
                finish();
            }
        });
    }
}