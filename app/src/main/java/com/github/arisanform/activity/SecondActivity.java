package com.github.arisanform.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.arisan.FormConfig;
import com.github.arisan.ArisanForm;
import com.github.arisan.helper.DummyCreator;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.Logger;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PermissionUtils;
import com.github.arisanform.R;
import com.github.arisanform.model.AllField;
import com.github.arisanform.model.MyResponse;
import com.github.arisanform.model.Url;
import com.github.arisanform.network.API;
import com.github.arisanform.network.Controller;
import com.github.arisanform.probolinggo.FormActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    ArisanForm myLayout;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //PROBOLINGGO
        startActivity(new Intent(this, FormActivity.class));
        finish();

        myLayout = findViewById(R.id.inflater_view);

        myLayout.setFieldModels(DummyCreator.fillDummyArray(ObjectReader.getField(new AllField())));

        String[] arr = {"Mobil","Motor","Pesawat","Roket"};

        myLayout.addListener("radio", (value ,adapter) -> {
            myLayout.updateValue("search",value);
            myLayout.updateData("radio",arr);
            myLayout.showSubmitProgress(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    myLayout.showSubmitProgress(false);
                }
            },3000);
        });

        FormConfig config = new FormConfig();
        config.buttonBackground = R.drawable.btn_success;
        config.labelColor = R.color.orange;
        config.textColor = R.color.colorDanger;

        myLayout.setConfig(config);
        myLayout.addListener("image",(value,adapter) -> {
            File file = new File(value);

            if(file.exists()) {
                Toast.makeText(this, "File Found", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();

        });


        myLayout.setOnSubmitListener(result -> Log.d("__RESULT",result));
        myLayout.buildForm();

        PermissionUtils.askPermission(this, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImagePickerUtils.ARISAN_REQUEST_IMAGE && resultCode == RESULT_OK){
            ImagePickerUtils imagePickerUtils = new ImagePickerUtils(this,data);
            myLayout.updateImage(imagePickerUtils);
            uploadFile(imagePickerUtils);
        }else{
            Logger.d("NO PICK");
        }
    }

    public void uploadFile(ImagePickerUtils imagePickerUtils){
        //kalau gini bisa.
        //File file = imagePickerUtils.getFile();
        File file = new File(imagePickerUtils.getUri().getPath());

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part file_body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        List<MultipartBody.Part> partList = new ArrayList<>();
        partList.add(file_body);

        new Controller().getInstance().create(API.class).upload(partList).enqueue(new Callback<MyResponse<Url>>() {
            @Override
            public void onResponse(Call<MyResponse<Url>> call, Response<MyResponse<Url>> response) {
                if(response.isSuccessful()) {
                    Logger.d(response.body());
                }
                else {
                    try {
                        Logger.d("FAILED TO UPLOAD FILE : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<Url>> call, Throwable t) {
                Logger.d("SOMETHING WRONG");
            }
        });
    }
}
