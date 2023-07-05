package com.example.docbaoonline.Model;

import com.google.gson.annotations.SerializedName;

public class BaiBao {
    private String ten;
    private String hinh;
    private String thoigian;
    private String link;

    public BaiBao(String ten, String hinh, String thoigian, String link) {
        this.ten = ten;
        this.hinh = hinh;
        this.thoigian = thoigian;
        this.link = link;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
