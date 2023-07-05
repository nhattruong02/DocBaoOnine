package com.example.docbaoonline.API;

import com.example.docbaoonline.Model.BaiBao;
import com.example.docbaoonline.Model.BaiBaoUpload;
import com.example.docbaoonline.Model.ChuyenMuc;
import com.example.docbaoonline.Model.TaiKhoan;
import com.example.docbaoonline.Model.TaiKhoanBaiBao;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    public static final String BASE_URL = "https://funmintpage79.conveyor.cloud/";
    @GET("api/KTTK/TaiKhoans")
    Call<TaiKhoan> kttk(@Query("tk") String tk);

    @GET("api/GetTaiKhoans/TaiKhoans")
    Call<ArrayList<TaiKhoan>> getTaiKhoans();

    @POST("api/PostTaiKhoan/TaiKhoans")
    Call<TaiKhoan> postTaiKhoans(@Body TaiKhoan taiKhoan);

    @GET("api/GetChuyenMucs/ChuyenMucs")
    Call<ArrayList<ChuyenMuc>> getChuyenMucs();

    @POST("api/ThemBaiBao/BaiBaos")
    Call<BaiBaoUpload> postBaibaos(@Body BaiBaoUpload baiBao);

    @POST("api/THEMBAIBAOUPDATE/TaiKhoanBaiBaos")
    Call<Integer> postTKBB(@Query("idTK") int idTK,
                                  @Query("idBB") int idBB,
                                  @Query("trangthai") String trangthai);

    @GET("api/Getbbbytrangthai/BaiBaos")
    Call<ArrayList<BaiBaoUpload>> getBaibaobytt(@Query("trangthai") String trangthai,
                                                @Query("idtk") int idtk);
    @DELETE("api/DELETEBAIBAOBYID/TaiKhoanBaiBaos")
    Call<Integer> deleteBaiBao(@Query("idtk") int idtk,@Query("trangthai") String trangthai);

    @POST("api/ThemChuyenMuc/ChuyenMucs")
    Call<ChuyenMuc> postCM(@Body ChuyenMuc chuyenMuc);

    @PUT("api/PutChuyenMuc/ChuyenMucs/{id}")
    Call<ChuyenMuc> putCM(@Path("id") int id,
                          @Body ChuyenMuc chuyenMuc);

    @DELETE("api/DeleteChuyenMuc/ChuyenMucs/{id}")
    Call<ChuyenMuc> deleteCM(@Path("id") int id);
}
