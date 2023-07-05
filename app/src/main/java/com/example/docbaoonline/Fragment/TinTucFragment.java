package com.example.docbaoonline.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.docbaoonline.Controller.ThongTin;
import com.example.docbaoonline.Home.PagerViewAdapter;
import com.example.docbaoonline.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import de.hdodenhof.circleimageview.CircleImageView;

public class TinTucFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PagerViewAdapter pagerViewAdapter;
    private CircleImageView circleImageView;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_tin_tuc,container,false);
        init();
        viewPager.setSaveEnabled(false);
        pagerViewAdapter = new PagerViewAdapter(this);
        viewPager.setAdapter(pagerViewAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Nổi Bật");
                    break;
                case 1:
                    tab.setText("Mới Nhất");
                    break;
                case 2:
                    tab.setText("Thể Thao");

                    break;
                case 3:
                    tab.setText("Du Lịch");

                    break;
                case 4:
                    tab.setText("Giải Trí");

                    break;
                case 5:
                    tab.setText("Giáo Dục");
                    break;
                case 6:
                    tab.setText("Sức Khoẻ");
                    break;
                case 7:
                    tab.setText("Đời Sống");
                    break;
                case 8:
                    tab.setText("Cười");
                    break;
                case 9:
                    tab.setText("Kinh Doanh");
                    break;
            }
        }).attach();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentThongTin();
            }
        });
        return view;
    }
    private void intentThongTin(){
        Intent intent = new Intent(getContext(), ThongTin.class);
        getActivity().startActivity(intent);
    }
    private void init(){
        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = view.findViewById(R.id.view_pager);
        circleImageView = view.findViewById(R.id.circle_img);
    }

}
