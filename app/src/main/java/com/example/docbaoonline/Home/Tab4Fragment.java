package com.example.docbaoonline.Home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.Adapter.BaiBaoAdapter;
import com.example.docbaoonline.Controller.CheckInternet;
import com.example.docbaoonline.Controller.DangNhap;
import com.example.docbaoonline.Controller.DocBao;
import com.example.docbaoonline.Controller.XMLDOMParse;
import com.example.docbaoonline.Fragment.ChuyenMucFragment;
import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.BaiBaoUpload;
import com.example.docbaoonline.Model.TaiKhoanBaiBao;
import com.example.docbaoonline.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab4Fragment extends Fragment {
    View view;
    ListView lvdulich;
    BaiBaoAdapter baiBaoAdapter;
    ArrayList<BaiBao> listbaibao = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab4, container, false);
        init();
        if(CheckInternet.checkI(getActivity())){
            ReadData readData = new ReadData();
            readData.execute("https://vnexpress.net/rss/du-lich.rss");
            baiBaoAdapter = new BaiBaoAdapter(getActivity(), R.layout.dong_bai_bao ,listbaibao);
            lvdulich.setAdapter(baiBaoAdapter);
            baiBaoAdapter.notifyDataSetChanged();
            lvdulich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DocBao.class);
                    String ten = listbaibao.get(i).getTen();
                    String tgian = listbaibao.get(i).getThoigian();
                    Date tgian1 = null;
                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
                    try {
                        tgian1 = format.parse(tgian);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String link = listbaibao.get(i).getLink();
                    String hinh = listbaibao.get(i).getHinh().toString().trim();
                    BaiBaoUpload baiBao = new BaiBaoUpload(0,4,ten,tgian1,link,hinh);
                    postbaibao(ApiService.BASE_URL,baiBao);
                    intent.putExtra("link",link);
                    getActivity().startActivity(intent);

                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void postbaibao(String baseurl,BaiBaoUpload baiBao) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<BaiBaoUpload> call = apiservice.postBaibaos(baiBao);
        call.enqueue(new Callback<BaiBaoUpload>() {
            @Override
            public void onResponse(Call<BaiBaoUpload> call, Response<BaiBaoUpload> response) {
                int idbb = response.body().getIdBB();
                String trangthai = "đã đọc";
                postTaikhoanbb(ApiService.BASE_URL,idbb,trangthai);
            }

            @Override
            public void onFailure(Call<BaiBaoUpload> call, Throwable t) {

            }
        });
    }

    private void postTaikhoanbb(String baseurl,int idbb,String trangthai) {
        ApiService apiservice = RetrofitClient.getClient(baseurl).create(ApiService.class);
        Call<Integer> call = apiservice.postTKBB(DangNhap.idtk,idbb,trangthai);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }
    public class ReadData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParse parse = new XMLDOMParse();
            Document document = parse.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdecription = document.getElementsByTagName("description");
            String hinh = "";
            String tenbaibao = "";
            String link = "";
            String ngay = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListdecription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    hinh = matcher.group(1);
                }
                Element element = (Element) nodeList.item(i);
                tenbaibao = parse.getValue(element, "title");
                link = parse.getValue(element, "link");
                ngay = parse.getValue(element, "pubDate");
                listbaibao.add(new BaiBao(tenbaibao, hinh, ngay, link));
                baiBaoAdapter = new BaiBaoAdapter(getActivity(), R.layout.dong_bai_bao, listbaibao);
                lvdulich.setAdapter(baiBaoAdapter);
                baiBaoAdapter.notifyDataSetChanged();
            }
            super.onPostExecute(s);
        }

        private String docNoiDung_Tu_URL(String theUrl) {
            StringBuilder content = new StringBuilder();

            try {
                // đạo tối tượng URL
                URL url = new URL(theUrl);
                // Tạo 1 kết nối
                URLConnection urlConnection = url.openConnection();

                // đưa url vừa nhận vào 1 bufferedreader
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // đọc nội dung trong đường dẫn url nhận được
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line + "\n");
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }

    private void init() {
        lvdulich = (ListView) view.findViewById(R.id.lv_du_lich);
    }
}
