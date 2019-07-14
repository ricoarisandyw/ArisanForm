package com.github.arisanform.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.DummyCreator;
import com.github.arisan.helper.GsonUtils;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.Logger;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.helper.PermissionUtils;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisanform.R;
import com.github.arisanform.model.AllField;
import com.github.arisanform.model.ConditionFormC;
import com.github.arisanform.model.FormC;
import com.github.arisanform.model.KK;
import com.github.arisanform.model.KKK;
import com.github.arisanform.model.MyResponse;
import com.github.arisanform.model.Url;
import com.github.arisanform.model.probolinggo.model.BedaIdentitas;
import com.github.arisanform.model.probolinggo.model.KTP;
import com.github.arisanform.model.probolinggo.model.SKCK;
import com.github.arisanform.network.API;
import com.github.arisanform.network.Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FormRebuilder{

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson;
    ArisanPreparation preparation;

    RecyclerView vList;
    RecyclerView vForm;
    TextView vDummyText;
    ImageView vImage;

    PreferenceHelper preference;

    ArisanAdapter arisanAdapter;
    ArisanForm  form;
    FormC c;
    ConditionFormC cond;
    AllField my_data;

    int iteration =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: For Abid Project Purpose
//        startActivity(new Intent(this,FormulirCActivity.class));
//        finish();

        preference = new PreferenceHelper(this);

        RealmList<String> data;
        String[] arr_str = {"satu","dua","tiga"};
        String json = new Gson().toJson(arr_str);
        data = new Gson().fromJson(json,new TypeToken<RealmList<String>>(){}.getType());
        for(String d:data){
            System.out.println("__DATA "+d);
        }

        askPermission();

        vList = findViewById(R.id.data_list);
        vForm = findViewById(R.id.data_form);
        vDummyText = findViewById(R.id.dummy_text);
        vImage = findViewById(R.id.data_image);

        vForm.setLayoutManager(new LinearLayoutManager(this));

        //Get Stored Data
        //FLOATING ADD
        FloatingActionButton vAdd = findViewById(R.id.add_todo);

        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArisanForm form = new ArisanForm(MainActivity.this);
                form.setFieldData(DummyCreator.fillDummyArray(ObjectReader.getField(new AllField())));
                form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                    @Override
                    public void onSubmit(String response) {
                        Log.d("__Arisan","Data "+response);
                    }
                });
                arisanAdapter = form.buildAdapter();
                vForm.setAdapter(arisanAdapter);
                vForm.setVisibility(View.VISIBLE);
            }
        });
    }

    public void askPermission(){
        final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        PermissionUtils.askPermission(this,PERMISSIONS);
    }

    public void form(){
        form = new ArisanForm(this);
        form.setFieldData(DummyCreator.fillDummyArray(ObjectReader.getField(new KTP())));
        form.setTitle("FORM KTP");
        form.setSubmitText("SUBMIT");
        form.setBackground(R.drawable.btn_success);
        form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                form = new ArisanForm(MainActivity.this);
                form.setFieldData(DummyCreator.fillDummyArray(ObjectReader.getField(new KK())));
                form.setTitle("FORM KK");
                form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                    @Override
                    public void onSubmit(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.e("__RESPONSE",response);

                        form = new ArisanForm(MainActivity.this);
                        form.setFieldData(DummyCreator.fillDummyArray(ObjectReader.getField(new SKCK())));
                        form.setTitle("FORM KK");
                        form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                            @Override
                            public void onSubmit(String response) {
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                Log.e("__RESPONSE",response);

                                form = new ArisanForm(MainActivity.this);
                                form.setModel(DummyCreator.fillDummyArray(ObjectReader.getField(new BedaIdentitas())));
                                form.setTitle("FORM BEDA IDENTITAS");
                                form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                                    @Override
                                    public void onSubmit(String response) {
                                        vForm.setVisibility(View.GONE);
                                    }
                                });
                                arisanAdapter = form.buildAdapter();
                                vForm.setAdapter(arisanAdapter);
                            }
                        });
                        arisanAdapter = form.buildAdapter();
                        vForm.setAdapter(arisanAdapter);
                    }
                });
                arisanAdapter = form.buildAdapter();
                vForm.setAdapter(arisanAdapter);

//                API api = new Controller().getInstance().create(API.class);
//                api.test().enqueue(new Callback<MyResponse<Url>>() {
//                    @Override
//                    public void onResponse(Call<MyResponse<Url>> call, Response<MyResponse<Url>> response) {
//                        Log.d("Retroift Response",new Gson().toJson(response));
//                    }
//
//                    @Override
//                    public void onFailure(Call<MyResponse<Url>> call, Throwable t) {
//
//                    }
//                });
//                vForm.setVisibility(View.GONE);
            }
        });
        form.addChildListener("one_to_many", "search", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                if(value.equals("1234")){
                    for(ArisanFieldModel model:adapter.getData()){
                        if(model.getName().equals("name")){
                            model.setValue("rico");
                            adapter.notifyDataSetChanged();
                        }
                    }
                    return new ListenerModel("jangan menggunakan 1234",false);
                }
                return null;
            }
        });
        form.addListener("edit_text", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value, ArisanAdapter adapter) {
                if(value.equals("1234")){
                    for(ArisanFieldModel model:adapter.getData()){
                        try {
                            if (model.getName().equals("name")) {
                                model.setValue("rico");
                                adapter.notifyDataSetChanged();
                            }
                        }catch (Exception ignored){}
                    }
                }
                return null;
            }
        });

        arisanAdapter = form.buildAdapter();

        vForm.setAdapter(arisanAdapter);

        vForm.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ImagePickerUtils.ARISAN_REQUEST_IMAGE && resultCode == RESULT_OK){
            ImagePickerUtils imagePickerUtils = new ImagePickerUtils(this,data);
            arisanAdapter.updateImage(imagePickerUtils);

            File file = imagePickerUtils.getFile();

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part file_body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            new Controller().getInstance().create(API.class).upload(file_body).enqueue(new Callback<MyResponse<Url>>() {
                @Override
                public void onResponse(Call<MyResponse<Url>> call, Response<MyResponse<Url>> response) {
                    if(response.isSuccessful()) Logger.d(response.body());
                    else Logger.d("FAILED TO UPLOAD FILE");
                }

                @Override
                public void onFailure(Call<MyResponse<Url>> call, Throwable t) {
                    Logger.d("SOMETHING WRONG");
                }
            });
        }else{
            Logger.d("NO PICK");
        }
    }

    ArisanAdapter.OnSubmitListener submitListener = new ArisanAdapter.OnSubmitListener() {
        @Override
        public void onSubmit(String response) {
            Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
        }
    };

    public void lapak(){
        form = new ArisanForm(this);
        form.setOnSubmitListener(submitListener);
        form.setFieldData(ObjectReader.getField(new KKK()));
        form.addChildListener("data_kk","search", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter arisanAdapter) {
                if(value.equals(String.valueOf(1234))){
                    form.copyFieldFromAdapter(arisanAdapter);
                    form.fillData("name","Rico Arisandy Wijaya");
                    rebuild(form);
                }else{
                    ListenerModel listenerModel = new ListenerModel();
                    listenerModel.setMessage("Tidak ditemukan");
                    listenerModel.setCondition(false);
                    return listenerModel;
                }
                return null;
            }
        });

        arisanAdapter = form.buildAdapter();

        vForm.setAdapter(arisanAdapter);
        vForm.setVisibility(View.VISIBLE);
    }

    public void scrollTo(){
        int saved_position = Integer.parseInt(preference.load("saved_position"));
        vForm.scrollToPosition(saved_position);
    }

    @Override
    public void rebuild(ArisanForm form) {
        arisanAdapter = form.buildAdapter();
        vForm.swapAdapter(arisanAdapter,true);
        scrollTo();
    }

    public RealmList<String> convertArrayToRealm(List<String> list){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(list),new TypeToken<RealmList<String>>(){}.getType());
    }
}
