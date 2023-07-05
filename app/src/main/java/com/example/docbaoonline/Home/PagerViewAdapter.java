package com.example.docbaoonline.Home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.docbaoonline.Fragment.TinTucFragment;
import com.example.docbaoonline.R;

public class PagerViewAdapter extends FragmentStateAdapter {


    public PagerViewAdapter(@NonNull Fragment fragment) {

        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Tab1Fragment();
            case 1:
                return new Tab2Fragment();
            case 2:
                return new Tab3Fragment();
            case 3:
                return new Tab4Fragment();
            case 4:
                return new Tab5Fragment();
            case 5:
                return new Tab6Fragment();
            case 6:
                return new Tab7Fragment();
            case 7:
                return new Tab8Fragment();
            case 8:
                return new Tab9Fragment();
            case 9:
                return new Tab10Fragment();
            default:
                return new Tab1Fragment();
        }
    }
    @Override
    public int getItemCount() {
        return 10;
    }
}
