package com.example.docbaoonline.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.docbaoonline.API.ApiService;
import com.example.docbaoonline.API.RetrofitClient;
import com.example.docbaoonline.Adapter.ChuyenMucAdapter;
import com.example.docbaoonline.Controller.CheckInternet;
import com.example.docbaoonline.Controller.DangNhap;
import com.example.docbaoonline.Home.Tab10Fragment;
import com.example.docbaoonline.Home.Tab1Fragment;
import com.example.docbaoonline.Home.Tab2Fragment;
import com.example.docbaoonline.Home.Tab3Fragment;
import com.example.docbaoonline.Home.Tab4Fragment;
import com.example.docbaoonline.Home.Tab5Fragment;
import com.example.docbaoonline.Home.Tab6Fragment;
import com.example.docbaoonline.Home.Tab7Fragment;
import com.example.docbaoonline.Home.Tab8Fragment;
import com.example.docbaoonline.Home.Tab9Fragment;
import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.ChuyenMuc;
import com.example.docbaoonline.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuyenMucFragment extends Fragment {
    private ArrayList<ChuyenMuc> listcChuyenMucs = new ArrayList<>();
    GridView gridView;
    ChuyenMucAdapter adapter;
    ProgressDialog progressDialog;
    static String link;
    public static int macm;
    RelativeLayout rl_admin;
    EditText edttimkiem;
    ImageView imgthem;
    View view;
    ApiService apiservice = RetrofitClient.getClient(ApiService.BASE_URL).create(ApiService.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chuyen_muc, container, false);
        init();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        if (CheckInternet.checkI(getActivity())) {
            getListChuyenmuc();
            if (DangNhap.loaitk.equals("Admin")) {
                imgthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        themChuyenmuc();
                    }
                });
                gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if(listcChuyenMucs.get(i).getIdcm() >10) {
                            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                            ChuyenMuc cm = listcChuyenMucs.get(i);
                            MenuInflater inflater = popupMenu.getMenuInflater();
                            inflater.inflate(R.menu.menu_sua_xoa, popupMenu.getMenu());
                            popupMenu.show();
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    switch (menuItem.getItemId()) {
                                        case R.id.menu_sua:
                                            int macmsua = cm.getIdcm();
                                            suaChuyenmuc(macmsua, cm);
                                            break;
                                        case R.id.menu_xoa:
                                            progressDialog.show();
                                            int macm = cm.getIdcm();
                                            deleteChuyenmuc(macm, cm);
                                            break;
                                    }
                                    return false;
                                }
                            });
                        }
                        return true;
                    }
                });
            } else {
                imgthem.setVisibility(View.GONE);
            }
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (listcChuyenMucs.get(i).getTenCM().equals("Nổi Bật")) {
                        fragmentTransaction.replace(R.id.content, new Tab1Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Mới Nhất")) {
                        fragmentTransaction.replace(R.id.content, new Tab2Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Thể Thao")) {
                        fragmentTransaction.replace(R.id.content, new Tab3Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Du Lịch")) {
                        fragmentTransaction.replace(R.id.content, new Tab4Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Giải Trí")) {
                        fragmentTransaction.replace(R.id.content, new Tab5Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Giáo Dục")) {
                        fragmentTransaction.replace(R.id.content, new Tab6Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Sức Khoẻ")) {
                        fragmentTransaction.replace(R.id.content, new Tab7Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Đời Sống")) {
                        fragmentTransaction.replace(R.id.content, new Tab8Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Cười")) {
                        fragmentTransaction.replace(R.id.content, new Tab9Fragment()).commit();
                    } else if (listcChuyenMucs.get(i).getTenCM().equals("Kinh Doanh")) {
                        fragmentTransaction.replace(R.id.content, new Tab10Fragment()).commit();
                    } else {
                        macm = listcChuyenMucs.get(i).getIdcm();
                        link = listcChuyenMucs.get(i).getLink().trim();
                        fragmentTransaction.replace(R.id.content, new ChuyenMucKhacFragment()).commit();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Không có kết nối internet", Toast.LENGTH_SHORT).show();
        }
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
        return view;
    }
    private void filter(String text) {
        ArrayList<ChuyenMuc> filteredList = new ArrayList<>();
        for (ChuyenMuc cm: listcChuyenMucs) {
            if (cm.getTenCM().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(cm);
            }
        }
        adapter.filterList(filteredList);
    }

    private void deleteChuyenmuc(int macm, ChuyenMuc chuyenMuc) {
        Call<ChuyenMuc> call = apiservice.deleteCM(macm);
        call.enqueue(new Callback<ChuyenMuc>() {
            @Override
            public void onResponse(Call<ChuyenMuc> call, Response<ChuyenMuc> response) {
                if (response.body() != null) {
                    Toast.makeText(getActivity(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                    listcChuyenMucs.remove(chuyenMuc);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ChuyenMuc> call, Throwable t) {


            }
        });
    }

    private void suaChuyenmuc(int macmsua, ChuyenMuc chuyenMuc) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_sua_chuyen_muc);
        EditText edtten = dialog.findViewById(R.id.edt_tencm_sua);
        EditText edthinh = dialog.findViewById(R.id.edt_hinhcm_sua);
        EditText edtlink = dialog.findViewById(R.id.edt_linkcm_sua);
        Button btnsua = dialog.findViewById(R.id.btnsua);
        Button btnhuy = dialog.findViewById(R.id.btnhuysua);
        edtten.setText(chuyenMuc.getTenCM());
        edthinh.setText(chuyenMuc.getHinh());
        edtlink.setText(chuyenMuc.getLink());
        dialog.show();
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String ten = edtten.getText().toString().trim();
                String hinh = edthinh.getText().toString().trim();
                String link = edtlink.getText().toString().trim();
                ChuyenMuc cm = new ChuyenMuc(macmsua, ten, hinh, link);
                Call<ChuyenMuc> call = apiservice.putCM(macmsua, cm);
                call.enqueue(new Callback<ChuyenMuc>() {
                    @Override
                    public void onResponse(Call<ChuyenMuc> call, Response<ChuyenMuc> response) {
                        if (response.code() == 204) {
                            Toast.makeText(getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            getListChuyenmuc();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChuyenMuc> call, Throwable t) {
                    }
                });
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void themChuyenmuc() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_chuyen_muc);
        EditText edtten = dialog.findViewById(R.id.edt_tencm);
        EditText edthinh = dialog.findViewById(R.id.edt_hinhcm);
        EditText edtlink = dialog.findViewById(R.id.edt_linkcm);
        Button btnthem = dialog.findViewById(R.id.btnthem);
        Button btnhuy = dialog.findViewById(R.id.btnhuythem);
        dialog.show();
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String ten = edtten.getText().toString().trim();
                String hinh = edthinh.getText().toString().trim();
                String link = edtlink.getText().toString().trim();
                ChuyenMuc chuyenMuc = new ChuyenMuc(0, ten, hinh, link);
                Call<ChuyenMuc> call = apiservice.postCM(chuyenMuc);
                call.enqueue(new Callback<ChuyenMuc>() {
                    @Override
                    public void onResponse(Call<ChuyenMuc> call, Response<ChuyenMuc> response) {
                        if (response.body() != null) {
                            Toast.makeText(getActivity(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                            getListChuyenmuc();
                            progressDialog.dismiss();
                            dialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Chuyên mục đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("1111",response.body()+"");
                    }

                    @Override
                    public void onFailure(Call<ChuyenMuc> call, Throwable t) {

                    }
                });

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });
    }


    private void init() {
        gridView = (GridView) view.findViewById(R.id.gv_chuyen_muc);
        imgthem = view.findViewById(R.id.img_them);
        edttimkiem = view.findViewById(R.id.edt_tim_kiem_cm);
    }

    private void getListChuyenmuc() {
        Call<ArrayList<ChuyenMuc>> call = apiservice.getChuyenMucs();
        call.enqueue(new Callback<ArrayList<ChuyenMuc>>() {
            @Override
            public void onResponse(Call<ArrayList<ChuyenMuc>> call, Response<ArrayList<ChuyenMuc>> response) {
                listcChuyenMucs = response.body();
                adapter = new ChuyenMucAdapter(getActivity(), R.layout.dong_chuyen_muc, listcChuyenMucs);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<ChuyenMuc>> call, Throwable t) {
            }
        });
    }
}
