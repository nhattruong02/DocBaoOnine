package com.example.docbaoonline.Controller;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.Model.TaiKhoan;
import com.example.docbaoonline.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity {
    Button btndk,btnthoat;
    EditText edttkdk,edtmkdk,edthoten,edtgt,edtns,edtsdt;
    DatePickerDialog.OnDateSetListener dateOnClickListener;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckInternet.checkI(DangKy.this)){
                    progressDialog.show();
                    String tk = edttkdk.getText().toString().trim();
                    ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);
                    Call<TaiKhoan> call = apiservice.kttk(tk);
                    call.enqueue(new Callback<TaiKhoan>() {
                        @Override
                        public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                            if(response.body() == null){
                                postTaiKhoan(ApiService.BASE_URL);
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(DangKy.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TaiKhoan> call, Throwable t) {

                        }
                    });
                }
                else{
                    Toast.makeText(DangKy.this, "Không có kết nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThoat();
            }
        });
        edtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int nam = cal.get(Calendar.YEAR);
                int thang = cal.get(Calendar.MONTH);
                int ngay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        DangKy.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateOnClickListener,
                        nam,thang,ngay);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateOnClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                thang += 1;
                String date = ngay +"/" + thang+"/" + nam;
                edtns.setText(date);
            }
        };
    }

    private void postTaiKhoan(String baseurl) {
        String strtkdk = edttkdk.getText().toString().trim();
        String strmkdk = edtmkdk.getText().toString().trim();
        String strhoten = edthoten.getText().toString().trim();
        String strgt = edtgt.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date ns = null;
        try {
            ns = format.parse(edtns.getText().toString().trim());
        } catch (Exception e) {
        }
        String strsdt = edtsdt.getText().toString().trim();
        TaiKhoan taiKhoan = new TaiKhoan(0,strtkdk,strmkdk,strhoten,strgt,ns,strsdt,"Người Dùng");
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<TaiKhoan> call = apiservice.postTaiKhoans(taiKhoan);
        call.enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                if(response.body() != null){
                    progressDialog.dismiss();
                    Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangKy.this,DangNhap.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
            }
        });
    }

    private void intentThoat() {
        Intent intent = new Intent(this,DangNhap.class);
        startActivity(intent);
    }



    private void init(){
        btndk = (Button) findViewById(R.id.btndangkytk);
        btnthoat = (Button) findViewById(R.id.btnthoatdk);
        edttkdk = (EditText) findViewById(R.id.edt_tkdk);
        edtmkdk = (EditText) findViewById(R.id.edt_mkdk);
        edthoten = (EditText) findViewById(R.id.edt_htdk);
        edtgt = (EditText) findViewById(R.id.edt_gtdk);
        edtsdt = (EditText) findViewById(R.id.edt_sdtdk);
        edtns =(EditText)  findViewById(R.id.edt_nsdk);
    }
}
