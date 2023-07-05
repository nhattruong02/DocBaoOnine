package com.example.docbaoonline.Model;

public class TaiKhoanBaiBao {
    private int idTK;
    private int idBB;
    private String trangthai;

    public TaiKhoanBaiBao(int idTK, int idBB, String trangthai) {
        this.idTK = idTK;
        this.idBB = idBB;
        this.trangthai = trangthai;
    }


    public int getIdTK() {
        return idTK;
    }

    public void setIdTK(int idTK) {
        this.idTK = idTK;
    }

    public int getIdBB() {
        return idBB;
    }

    public void setIdBB(int idBB) {
        this.idBB = idBB;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
