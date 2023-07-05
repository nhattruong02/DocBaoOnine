package com.example.docbaoonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;
import com.example.docbaoonline.Fragment.TienIchFragment;
import com.example.docbaoonline.Fragment.ChuyenMucFragment;
import com.example.docbaoonline.Fragment.TinTucFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private TinTucFragment tintucFragment = new TinTucFragment();
    private TienIchFragment tienichFragment = new TienIchFragment();
    private ChuyenMucFragment chuyenmucFragment = new ChuyenMucFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,tintucFragment).commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_tin_tuc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,tintucFragment).commit();
                        return true;
                    case R.id.menu_chuyen_muc:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,chuyenmucFragment).commit();
                        return true;
                    case R.id.menu_tien_ich:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content,tienichFragment).commit();
                        return true;
                }
                return true;
            }
        });
    }

}