package com.example.docbaoonline.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.Controller.BaiBaoDaDoc;
import com.example.docbaoonline.Controller.DangNhap;
import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.BaiBaoUpload;
import com.example.docbaoonline.Model.TaiKhoanBaiBao;
import com.example.docbaoonline.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiBaoAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<BaiBao> baiBaoList;
    SimpleDateFormat format;
    ProgressDialog progressDialog;

    public BaiBaoAdapter(Context context, int layout, List<BaiBao> baiBaoList) {
        this.context = context;
        this.layout = layout;
        this.baiBaoList = baiBaoList;
    }

    public void filterList(ArrayList<BaiBao> filterlist) {
        baiBaoList = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return baiBaoList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHoder {
        ImageView imghinh, ictime, icluu;
        TextView txtten, txtthoigian;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaiBaoAdapter.ViewHoder hoder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            hoder = new BaiBaoAdapter.ViewHoder();
            hoder.imghinh = view.findViewById(R.id.img_hinh_bao);
            hoder.txtten = view.findViewById(R.id.txt_ten);
            hoder.txtthoigian = view.findViewById(R.id.txt_thoi_gian);
            hoder.ictime = view.findViewById(R.id.ic_time);
            hoder.icluu = view.findViewById(R.id.ic_luu);
            view.setTag(hoder);
        } else {
            hoder = (BaiBaoAdapter.ViewHoder) view.getTag();
        }
        BaiBao baiBao = baiBaoList.get(i);
        if (baiBao.getHinh().isEmpty()) {

        } else {
            Picasso.with(context).load(baiBao.getHinh()).into(hoder.imghinh);
        }
        if(baiBao.getTen().length() < 70) {
            hoder.txtten.setText(baiBao.getTen());
        }else{
            hoder.txtten.setText(baiBao.getTen().substring(0,55) +" ...");
        }
        long tgiantv = 0;
        long tghientai = 0;
        long tgianbaitao = 0;
        Date tgian = null;
        format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
        try {
            tghientai = System.currentTimeMillis();
            tgian = format.parse(baiBao.getThoigian().trim());
            tgianbaitao = tgian.getTime();
        } catch (ParseException e) {
        }
        tgiantv = tghientai - tgianbaitao;
        if (TimeUnit.MILLISECONDS.toMinutes(tgiantv) <= 60) {
            hoder.txtthoigian.setText(TimeUnit.MILLISECONDS.toMinutes(tgiantv) + " phút trước");
        } else if (TimeUnit.MILLISECONDS.toHours(tgiantv) <= 24) {
            hoder.txtthoigian.setText(TimeUnit.MILLISECONDS.toHours(tgiantv) + " giờ trước");
        } else if (TimeUnit.MILLISECONDS.toHours(tgiantv) > 24) {
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String tgian1 = "";
            try {
                tgian1 = format1.format(format.parse(baiBao.getThoigian().trim()));
            } catch (ParseException e) {
            }
            hoder.txtthoigian.setText(tgian1);
            if (tgian1.equals("")) {
                hoder.ictime.setVisibility(View.INVISIBLE);
            }
        }
        hoder.icluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait ...");
                progressDialog.show();
                String ten = baiBao.getTen();
                String tgian1 = baiBao.getThoigian();
                String link = baiBao.getLink();
                String hinh =  baiBao.getHinh();
                Date tgianbb = null;
                SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                try {
                    tgianbb = format.parse(tgian1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                BaiBaoUpload bb = new BaiBaoUpload(0,1,ten,tgianbb,link,hinh);
                postbaibao(ApiService.BASE_URL,bb);
            }
        });
        return view;
    }
    private void postbaibao(String baseurl,BaiBaoUpload baiBao) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<BaiBaoUpload> call = apiservice.postBaibaos(baiBao);
        call.enqueue(new Callback<BaiBaoUpload>() {
            @Override
            public void onResponse(Call<BaiBaoUpload> call, Response<BaiBaoUpload> response) {
                if(response.body() != null) {
                    int idbb = response.body().getIdBB();
                    String trangthai = "đã lưu";
                    postTaikhoanbb(ApiService.BASE_URL, idbb, trangthai);
                }
            }

            @Override
            public void onFailure(Call<BaiBaoUpload> call, Throwable t) {
                Log.e("99", t + "");
            }
        });
    }

    private void postTaikhoanbb(String baseurl,int idbb,String trangthai) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiservice.postTKBB(DangNhap.idtk,idbb,trangthai);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == 1){
                    Toast.makeText(context, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(context, "Bài báo đã được lưu!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

}
