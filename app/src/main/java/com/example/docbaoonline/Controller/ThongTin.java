package com.example.docbaoonline.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docbaoonline.MainActivity;
import com.example.docbaoonline.R;

public class ThongTin extends AppCompatActivity {
    ImageView imgquaylai;
    TextView txtdaxem,txtlich,txtthoitiet,txtvitri,txtdangxuat,txtten,txtdaluu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thong_tin);
        init();
        txtten.setText(DangNhap.ten);
        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txtdaxem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, BaiBaoDaDoc.class);
                startActivity(intent);
            }
        });
        txtlich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, XemLich.class);
                startActivity(intent);
            }
        });
        txtthoitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, XemThoiTiet.class);
                startActivity(intent);
            }
        });
        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, DangNhap.class);
                startActivity(intent);
            }
        });
        txtvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, XemViTri.class);
                startActivity(intent);
            }
        });
        txtdaluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin.this, BaiBaoDaLuu.class);
                startActivity(intent);
            }
        });
    }
    private void init(){
        txtten = findViewById(R.id.txt_ten_tt);
        imgquaylai = findViewById(R.id.img_quay_lai);
        txtdaxem = findViewById(R.id.txt_da_xem);
        txtlich = findViewById(R.id.txt_lich_viet);
        txtthoitiet = findViewById(R.id.txt_thoi_tiet);
        txtdangxuat = findViewById(R.id.txt_dang_xuat);
        txtvitri = findViewById(R.id.txt_vi_tri);
        txtdaluu = findViewById(R.id.txt_da_luu);
    }
}