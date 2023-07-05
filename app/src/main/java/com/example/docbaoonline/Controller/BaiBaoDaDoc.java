package com.example.docbaoonline.Controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.Adapter.BaiBaoAdapter;
import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.BaiBaoUpload;
import com.example.docbaoonline.Model.TaiKhoanBaiBao;
import com.example.docbaoonline.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiBaoDaDoc extends AppCompatActivity {
    ListView lvdadoc;
    BaiBaoAdapter adapter;
    private ArrayList<BaiBao> arrbaibaodadoc = new ArrayList<>();
    EditText edttimkiem;
    ImageView imgdelete;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bai_bao_da_doc);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        getBbdadoc(ApiService.BASE_URL);
        imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                AlertDialog.Builder dialog = new AlertDialog.Builder(BaiBaoDaDoc.this);
                dialog.setMessage("Bạn có đồng ý xoá bài báo đã đọc không?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBaibao(ApiService.BASE_URL);
                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        edttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
        lvdadoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BaiBaoDaDoc.this, DocBao.class);
                intent.putExtra("link", arrbaibaodadoc.get(i).getLink());
                startActivity(intent);
            }
        });
    }

    private void deleteBaibao(String baseurl) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        String trangthai = "đã đọc";
        Call<Integer> call = apiservice.deleteBaiBao(DangNhap.idtk,trangthai);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                    arrbaibaodadoc.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(BaiBaoDaDoc.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void filter(String text) {
        ArrayList<BaiBao> filteredList = new ArrayList<>();
        for (BaiBao bb : arrbaibaodadoc) {
            if (bb.getTen().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(bb);
            }
        }
        adapter.filterList(filteredList);
    }

    private void getBbdadoc(String baseurl) {
        progressDialog.show();
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<ArrayList<BaiBaoUpload>> call = apiservice.getBaibaobytt("đã đọc", DangNhap.idtk);
        call.enqueue(new Callback<ArrayList<BaiBaoUpload>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiBaoUpload>> call, Response<ArrayList<BaiBaoUpload>> response) {
                ArrayList<BaiBaoUpload> bbs = new ArrayList<>();
                bbs = response.body();
                for (int i = 0; i < bbs.size(); i++) {
                    String ten = bbs.get(i).getTenBB().toString().trim();
                    String hinh = bbs.get(i).getHinhBB().toString().trim();
                    String tg = "";
                    String link = bbs.get(i).getLinkBB().toString().trim();
                    BaiBao baiBao = new BaiBao(ten, hinh, tg, link);
                    arrbaibaodadoc.add(baiBao);
                }
                adapter = new BaiBaoAdapter(BaiBaoDaDoc.this, R.layout.dong_bai_bao, arrbaibaodadoc);
                lvdadoc.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<ArrayList<BaiBaoUpload>> call, Throwable t) {
            }
        });
    }

    private void init() {
        lvdadoc = findViewById(R.id.lv_da_doc);
        edttimkiem = findViewById(R.id.edt_tim_kiem);
        imgdelete = findViewById(R.id.img_delete);
    }
}
