package com.example.docbaoonline.Model;

import java.util.Date;

public class TaiKhoan {
    private int idTK;
    private String taiKhoan1;
    private String matKhau;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private String soDT;
    private String loaiTK;

    public TaiKhoan(int idTK, String taiKhoan1, String matKhau, String hoTen, String gioiTinh, Date ngaySinh, String soDT, String loaiTK) {
        this.idTK = idTK;
        this.taiKhoan1 = taiKhoan1;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.soDT = soDT;
        this.loaiTK = loaiTK;
    }

    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public String getTaiKhoan1() {
        return taiKhoan1;
    }

    public void setTaiKhoan1(String taiKhoan1) {
        this.taiKhoan1 = taiKhoan1;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getLoaiTK() {
        return loaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        this.loaiTK = loaiTK;
    }
}
