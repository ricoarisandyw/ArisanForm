package com.github.arisanform.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.helper.GetRealPath;
import com.github.arisanform.model.Todo;
import com.github.arisanform.network.API;
import com.github.arisanform.network.Controller;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {

    RecyclerView mForm;
    ArisanAdapter arisanAdapter;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mForm = findViewById(R.id.form);

        /*NOTED: if you want to change model, you can do re-assign YOUR MODEL
        because your model is stored in preference/local. example code bellow :
        ArisanPreparation arisanPreparation = new ArisanPreparation(this);
        arisanPreparation.setModel(new Todo());*/

        //Create Adapter
        ArisanForm arisanForm = new ArisanForm(this);
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                    @Override
                    public void onSubmit(String response) {
                        Intent data = new Intent();
                        data.setData(Uri.parse(response));
                        FormActivity.this.setResult(RESULT_OK,data);
                        FormActivity.this.finish();
                        //uploadFile(uri);
                    }
                });
        //Build Adapter
        arisanAdapter = arisanForm.buildAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mForm.setLayoutManager(mLayoutManager);
        mForm.setAdapter(arisanAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ArisanCode.REQUEST_FILE){
            if(resultCode == RESULT_OK){
                String PathHolder = data.getData().getPath();
                Toast.makeText(FormActivity.this, PathHolder, Toast.LENGTH_LONG).show();
                uri = data.getData();
                Log.d("__REAL PATH",new GetRealPath(this).getRealPathFromURI(uri));
                Log.d("__File",data.getData().getPath());
                arisanAdapter.updateFile("attachment",new GetRealPath(this).getRealPathFromURI(uri));

            }
        }
    }

    public void uploadFile(Uri fileUri){
        File file = new File(new GetRealPath(this).getRealPathFromURI(fileUri));

        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);

        new Controller().getInstance().create(API.class).uploadImage(requestFile).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}
