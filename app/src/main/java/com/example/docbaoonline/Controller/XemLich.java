package com.example.docbaoonline.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.docbaoonline.MainActivity;
import com.example.docbaoonline.R;

import java.util.Calendar;

public class XemLich extends AppCompatActivity {
    TextView ngay,thang,nam;
    ImageView imgthoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xem_lich);
        init();
        Calendar calendar = Calendar.getInstance();
        ngay.setText(calendar.get(Calendar.DATE) +"");
        thang.setText(calendar.get(Calendar.MONTH)+1 +"");
        nam.setText(calendar.get(Calendar.YEAR) +"");
        imgthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(XemLich.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        imgthoat = findViewById(R.id.img_quay_lai_tt);
        ngay = findViewById(R.id.txt_ngayn);
        thang = findViewById(R.id.txt_thangn);
        nam = findViewById(R.id.txt_namn);
    }

}
