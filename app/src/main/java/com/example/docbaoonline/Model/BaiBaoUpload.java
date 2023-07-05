package com.example.docbaoonline.Model;

import java.util.Date;

public class BaiBaoUpload {
    private int idBB;
    private int idCM;
    private String tenBB;
    private Date tGian;
    private String linkBB;
    private String hinhBB;

    public BaiBaoUpload(int idBB, int idCM, String tenBB, Date tGian, String linkBB, String hinhBB) {
        this.idBB = idBB;
        this.idCM = idCM;
        this.tenBB = tenBB;
        this.tGian = tGian;
        this.linkBB = linkBB;
        this.hinhBB = hinhBB;
    }

    public int getIdBB() {
        return idBB;
    }

    public void setIdBB(int idBB) {
        this.idBB = idBB;
    }

    public int getIdCM() {
        return idCM;
    }

    public void setIdCM(int idCM) {
        this.idCM = idCM;
    }

    public String getTenBB() {
        return tenBB;
    }

    public void setTenBB(String tenBB) {
        this.tenBB = tenBB;
    }

    public Date gettGian() {
        return tGian;
    }

    public void settGian(Date tGian) {
        this.tGian = tGian;
    }

    public String getLinkBB() {
        return linkBB;
    }

    public void setLinkBB(String linkBB) {
        this.linkBB = linkBB;
    }

    public String getHinhBB() {
        return hinhBB;
    }

    public void setHinhBB(String hinhBB) {
        this.hinhBB = hinhBB;
    }
}
