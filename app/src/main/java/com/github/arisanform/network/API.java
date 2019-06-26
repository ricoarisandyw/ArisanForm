package com.github.arisanform.network;

import com.github.arisanform.model.MyResponse;
import com.github.arisanform.model.Url;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface API {
    @Multipart
    @POST("upload.php")
    Call<Boolean> uploadImage(@Part("file\"; filename=\"myfile.jpg\" ") RequestBody file);

    @GET("test")
    Call<MyResponse<Url>> test();

    @Multipart
    @POST("upload")
    Call<MyResponse<Url>> upload(@Part MultipartBody.Part file);
}
