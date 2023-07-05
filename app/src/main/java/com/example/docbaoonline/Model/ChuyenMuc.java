package com.example.docbaoonline.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChuyenMuc {
    @SerializedName("idCM")
    private int idcm;
    private String tenCM;
    private String hinh;
    private String link;

    public ChuyenMuc(int idcm, String tenCM, String hinh, String link) {
        this.idcm = idcm;
        this.tenCM = tenCM;
        this.hinh = hinh;
        this.link = link;
    }

    @Override
    public String toString() {
        return "ChuyenMuc{" +
                "idcm=" + idcm +
                ", tenCM='" + tenCM + '\'' +
                ", hinh='" + hinh + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public int getIdcm() {
        return idcm;
    }

    public void setIdcm(int idcm) {
        this.idcm = idcm;
    }

    public String getTenCM() {
        return tenCM;
    }

    public void setTenCM(String tenCM) {
        this.tenCM = tenCM;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
