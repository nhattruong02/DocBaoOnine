package com.example.docbaoonline.Controller;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.MainActivity;
import com.example.docbaoonline.Model.TaiKhoan;
import com.example.docbaoonline.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class XemThoiTiet extends AppCompatActivity {
    ImageView imghinh;
    TextView txttrangthai,txtnhietdo,txtdoam,txtmay,txtgio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xem_thoi_tiet);
        init();
        getThoitiet();
    }

    private void getThoitiet() {
        RequestQueue requestQueue = Volley.newRequestQueue(XemThoiTiet.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=66701fb179c870fca12c545b30118b5a";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String trangthai = jsonObjectWeather.getString("main");
                    String hinh = jsonObjectWeather.getString("icon");
                    Picasso.with(XemThoiTiet.this).load("https://openweathermap.org/img/wn/"+hinh+".png").into(imghinh);
                    txttrangthai.setText(trangthai);
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    int nhietdo = jsonObjectMain.getInt("temp");
                    int doam = jsonObjectMain.getInt("humidity");
                    txtdoam.setText(String.valueOf(doam) + "%");
                    txtnhietdo.setText( String.valueOf(nhietdo).substring(0,2) + "°C");
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String gio = jsonObjectWind.getString("speed");
                    txtgio.setText(gio+"m/s");
                    JSONObject jsonObjectCould = jsonObject.getJSONObject("clouds");
                    String may = jsonObjectCould.getString("all");
                    txtmay.setText(may+"%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void init(){
        imghinh = (ImageView) findViewById(R.id.img_hinh);
        txttrangthai = (TextView) findViewById(R.id.txt_trang_thai);
        txtnhietdo = (TextView) findViewById(R.id.txt_nhiet_do);
        txtdoam = (TextView) findViewById(R.id.txt_do_am);
        txtmay = (TextView) findViewById(R.id.txt_may);
        txtgio = (TextView) findViewById(R.id.txt_gio);
    }
}
