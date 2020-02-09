package com.github.arisanform.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.arisan.FormConfig;
import com.github.arisan.ArisanForm;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.adapter.CheckboxAdapter;
import com.github.arisan.helper.DummyCreator;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.Logger;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PermissionUtils;
import com.github.arisan.model.FormModel;
import com.github.arisanform.R;
import com.github.arisanform.model.AllField;
import com.github.arisanform.model.MyResponse;
import com.github.arisanform.model.Nature;
import com.github.arisanform.model.Url;
import com.github.arisanform.network.API;
import com.github.arisanform.network.Controller;

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

    ArisanForm vForm;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        vForm = findViewById(R.id.arisan_form);

        //First, you must set model
        vForm.setModels(new Nature());

        //Fill array data for checkbox, radio or spinner
        vForm.fillData("category",Nature.DATA_CATEGORY);
        vForm.fillData("label",Nature.DATA_LABEL);

        //Adding listener when value changed
        vForm.addListener("category", (value , adapter) -> {
            Toast.makeText(this, "You select "+value, Toast.LENGTH_SHORT).show();
//            CheckboxAdapter adapter_of_label = (CheckboxAdapter) adapter.findViewHolderByName("label").view.mCheckboxParent.getAdapter();
//            adapter_of_label.mDataset.remove(0);
//            adapter_of_label.notifyDataSetChanged();
        });

        //Setting Configuration
        FormConfig config = new FormConfig();
        config.title = "Nature Form";
        config.buttonBackground = R.drawable.btn_accent;
        config.textColor = R.color.font;
        config.buttonTextColor = R.color.white;

        vForm.setConfig(config);

        vForm.setOnSubmitListener(result -> {/*Do something with result*/});
        vForm.buildForm();

        //I have prepare permission for pick image
        PermissionUtils.askPermission(this, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ImagePickerUtils.ARISAN_REQUEST_IMAGE && resultCode == RESULT_OK){
            ImagePickerUtils imagePickerUtils = new ImagePickerUtils(this,data);
            vForm.updateImage(imagePickerUtils);
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
