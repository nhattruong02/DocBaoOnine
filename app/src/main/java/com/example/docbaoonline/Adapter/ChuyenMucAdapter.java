package com.example.docbaoonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.ChuyenMuc;
import com.example.docbaoonline.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChuyenMucAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<ChuyenMuc> chuyemucList;
    public ChuyenMucAdapter(Context context, int layout, List<ChuyenMuc> chuyemucList) {
        this.context = context;
        this.layout = layout;
        this.chuyemucList = chuyemucList;
    }
    public void filterList(ArrayList<ChuyenMuc> filterlist) {
        chuyemucList = filterlist;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return chuyemucList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHoder{
        ImageView imghinh;
        TextView txtten;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChuyenMucAdapter.ViewHoder hoder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            hoder = new ChuyenMucAdapter.ViewHoder();
            hoder.imghinh = view.findViewById(R.id.img_hinh_cm);
            hoder.txtten = view.findViewById(R.id.tv_ten_cm);
            view.setTag(hoder);
        } else {
            hoder = (ChuyenMucAdapter.ViewHoder) view.getTag();
        }
        ChuyenMuc chuyenMuc = chuyemucList.get(i);
        Picasso.with(context).load(chuyenMuc.getHinh()).into(hoder.imghinh);
        hoder.txtten.setText(chuyenMuc.getTenCM());
        return view;
    }
}
