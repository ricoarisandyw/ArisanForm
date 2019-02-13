package com.github.arisanform.network;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @Multipart
    @POST("upload.php")
    Call<Boolean> uploadImage(@Part("file\"; filename=\"myfile.jpg\" ") RequestBody file);
}
