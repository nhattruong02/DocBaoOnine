package com.example.docbaoonline.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.docbaoonline.Controller.XemLich;
import com.example.docbaoonline.Controller.XemThoiTiet;
import com.example.docbaoonline.Controller.XemViTri;
import com.example.docbaoonline.R;

public class TienIchFragment extends Fragment {
    View view;
    TextView imglich,imgthoitiet,imgvitri;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_tien_ich,container,false);
        init();
        imgvitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentVitri();
            }
        });
        imglich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentlich();
            }
        });
        imgthoitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentthoitiet();
            }
        });
        return view;

    }

    private void intentthoitiet() {
        Intent intent = new Intent(getActivity(), XemThoiTiet.class);
        getActivity().startActivity(intent);
    }

    private void intentlich() {
        Intent intent = new Intent(getActivity(), XemLich.class);
        getActivity().startActivity(intent);
    }

    private void intentVitri() {
        Intent intent = new Intent(getActivity(),XemViTri.class);
        getActivity().startActivity(intent);
    }

    private void init(){
        imglich = view.findViewById(R.id.img_lich);
        imgthoitiet = view.findViewById(R.id.img_thoi_tiet);
        imgvitri = view.findViewById(R.id.img_vi_tri);
    }
}
