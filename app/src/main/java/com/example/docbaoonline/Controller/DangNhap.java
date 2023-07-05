package com.example.docbaoonline.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.MainActivity;
import com.example.docbaoonline.Model.TaiKhoan;
import com.example.docbaoonline.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {
    Button btndangnhap,btndangky;
    EditText edttk,edtmk;
    private ArrayList<TaiKhoan> listTaikhoan = new ArrayList<>();
    public static String ten;
    public static int idtk;
    public static String loaitk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);
        init();
        getListTaiKhoan(ApiService.BASE_URL);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentDangKy();
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckInternet.checkI(DangNhap.this)){
                    intentDangNhap();
                }
                else{
                    Toast.makeText(DangNhap.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getListTaiKhoan(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<TaiKhoan>> call = apiservice.getTaiKhoans();
        call.enqueue(new Callback<ArrayList<TaiKhoan>>() {
            @Override
            public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                listTaikhoan = response.body();
            }
            @Override
            public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
            }
        });
    }

    private void intentDangNhap() {
        String strtaikhoan = edttk.getText().toString().trim();
        String strmatkhau = edtmk.getText().toString().trim();
        if(listTaikhoan == null || listTaikhoan.isEmpty()){
        }
        boolean hasTaiKhoan = false;
        for (TaiKhoan taiKhoan : listTaikhoan){
            if (strtaikhoan.equals(taiKhoan.getTaiKhoan1().trim()) && strmatkhau.equals(taiKhoan.getMatKhau().trim())){
                ten = taiKhoan.getHoTen();
                idtk = taiKhoan.getIdTK();
                loaitk = taiKhoan.getLoaiTK();
                hasTaiKhoan = true;
                break;
            }
        }
        if(hasTaiKhoan){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Sai tài khoản mật khẩu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void intentDangKy() {
        Intent intent = new Intent(this,DangKy.class);
        startActivity(intent);
    }

    private void init(){
        btndangnhap = (Button) findViewById(R.id.btndangnhap);
        btndangky = (Button) findViewById(R.id.btndangky);
        edttk = (EditText) findViewById(R.id.edt_tk);
        edtmk = (EditText) findViewById(R.id.edt_mk);
    }


}