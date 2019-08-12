package com.github.arisanform.probolinggo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.FormConfig;
import com.github.arisan.adapter.FormAdapter;
import com.github.arisan.helper.ImagePickerUtils;
import com.github.arisan.helper.Logger;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.helper.RadioUtils;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.model.ListenerModel;
import com.github.arisanform.R;
import com.github.arisanform.probolinggo.form.FormKK;
import com.github.arisanform.probolinggo.form.FormKTP;
import com.github.arisanform.probolinggo.form.FormSKCK;
import com.github.arisanform.probolinggo.model.BedaIdentitas;
import com.github.arisanform.probolinggo.model.KTP;
import com.github.arisanform.probolinggo.model.MasterData;
import com.github.arisanform.probolinggo.model.Penduduk;
import com.github.arisanform.probolinggo.model.SKCK;
import com.github.arisanform.probolinggo.network.BaseRetrofitResponse;
import com.github.arisanform.probolinggo.network.DbKK;
import com.github.arisanform.probolinggo.network.DbKTP;
import com.github.arisanform.probolinggo.network.DbSKCK;
import com.github.arisanform.probolinggo.network.FindNIKService;
import com.github.arisanform.probolinggo.utils.GsonTools;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {

    ArisanForm vForm;
    FormConfig mConfig;

    PreferenceHelper dbPreference;

    KTP ktp;
    SKCK skck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Change to Probolinggo Activity
        setContentView(R.layout.activity_second); //<------This!
        vForm = findViewById(R.id.inflater_view); //<------This!

        dbPreference = new PreferenceHelper(this);

        mConfig = new FormConfig();
        mConfig.background = R.drawable.btn_accent;
        mConfig.submitText = "SELESAI";

        //final String category = dbPreference.load(MasterData.CATEGORY);
        final String category = MasterData.KTP;
        if(category.equals(MasterData.KTP)){
            vForm.setModels(new FormKTP());
            FormKTPConfig();
        }else if(category.equals(MasterData.SKCK)){
            vForm.setModels(new FormSKCK());
            FormSKCKConfig();
        }else if(category.equals(MasterData.KK)){
            vForm.setModels(new FormKK());
            FormKKConfig();
        }else if(category.equals(MasterData.BEDA_IDENTITAS)){
            vForm.setModels(new BedaIdentitas());
            FormBedaIdentitasConfig();
        }

        vForm.setConfig(mConfig);
        vForm.buildForm();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ImagePickerUtils.ARISAN_REQUEST_IMAGE && resultCode == RESULT_OK){
            ImagePickerUtils imagePickerUtils = new ImagePickerUtils(this,data);
            UriUtils uri = new UriUtils(this,imagePickerUtils.getUri());
            File file = new File(uri.getUri().getPath());
            if(!file.exists()){
                Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "File ditemukan", Toast.LENGTH_SHORT).show();
            }

            vForm.updateImage(imagePickerUtils);
        }else{
            Logger.d("NO PICK");
        }
    }

    void FormBedaIdentitasConfig(){
        
    }

    void FormKKConfig(){
        vForm.fillData("desa_pengantar", MasterData.DAFTAR_DESA);
        mConfig.title = "FORM FormKK";
        vForm.setOnSubmitListener(new FormAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Map<String,String> map = GsonTools.convertToProbolinggo(response);
                Log.d("__RESPONSE FormKK",new Gson().toJson(map));
                FormKK formKK = new Gson().fromJson(response,FormKK.class);
                vForm.showSubmitProgress(true);
                DbKK.insert(formKK.toKK(), new BaseRetrofitResponse() {
                    @Override
                    public void OnResponse(boolean success, Object data) {
                        if(success){
                            doneActivity();
                        }else{
                            Toast.makeText(FormActivity.this, "Failed to create, check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                        vForm.showSubmitProgress(false);
                    }
                });
            }
        });
    }

    void FormSKCKConfig(){
        vForm.fillData("penduduk_nik_agama", MasterData.Array.AGAMA);
        vForm.fillData("penduduk_nik_jenis_kelamin", MasterData.Array.JENIS_KELAMIN);
        vForm.fillData("tipe_pengajuan",MasterData.Array.TIPE_PENGAJUAN);
        vForm.fillData("desa_pengantar",MasterData.DAFTAR_DESA);
        mConfig.title = "FORM FormSKCK";
        vForm.addListener("penduduk_nik", new ArisanListener.OnCondition() {
            @Override
            public ListenerModel onValue(String value) {
                try {
                    Response<Penduduk> response = new FindNIKService().getInstance().create(FindNIKService.API_FIND.class).findNik(value).execute();
                    if(response.isSuccessful()&&response.body()!=null){
                        vForm.setModels(FormSKCK.fromSKCK(response.body().toSKCK()));
                        FormSKCKConfig();
                        vForm.buildForm();
                        return null;
                    }else{
                        Toast.makeText(FormActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        ListenerModel listenerModel = new ListenerModel();
                        listenerModel.setCondition(false);
                        listenerModel.setMessage("Not Found");
                        return listenerModel;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });
        vForm.setOnSubmitListener(new FormAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(final String response) {
                FormSKCK formResponse = new Gson().fromJson(response, FormSKCK.class);
                skck = formResponse.toSKCK();
                vForm.showSubmitProgress(true);
                DbSKCK.insert(skck, new BaseRetrofitResponse() {
                    @Override
                    public void OnResponse(boolean success, Object data) {
                        skck = (SKCK) data;
                        if (success) {
                            doneActivity();
                        } else {
                            Toast.makeText(FormActivity.this, "Failed to create, check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                        vForm.showSubmitProgress(false);
                    }
                });
                Log.d("__response", response);
            }
        });
    }

    void FormKTPConfig(){
        mConfig.title = "FORM FormKTP";
        vForm.fillData("tipe_pengajuan", MasterData.Array.TIPE_PENGAJUAN);
        vForm.fillData("nik_agama", MasterData.Array.AGAMA);
        vForm.fillData("nik_jenis_kelamin", MasterData.Array.JENIS_KELAMIN);
        vForm.fillData("nik_status_pernikahan", MasterData.Array.STATUS_PERNIKAHAN);
        vForm.fillData("desa_pengantar",MasterData.DAFTAR_DESA);
        vForm.addListener("nik", new ArisanListener.OnCondition() {
        @Override
            public ListenerModel onValue(final String value) {
            Log.d("__Log","Click NIK Search -> " + value);
                new FindNIKService().getInstance().create(FindNIKService.API_FIND.class).findNik(value).enqueue(new Callback<Penduduk>() {
                    @Override
                    public void onResponse(Call<Penduduk> call, Response<Penduduk> response) {
                        if(response.isSuccessful()) {
                            Logger.s("Find Penduduk >>",response.body());

                            FormKTP formKTP = FormKTP.fromKTP(response.body().toKTP());
                            formKTP.setNik(value);

                            vForm.setModels(formKTP);
                            //FormKTPConfig();
                            vForm.notifyValue();
                            Toast.makeText(FormActivity.this, "Pencarian Selesai.", Toast.LENGTH_SHORT).show();
                        }else{
                            Logger.e(response.body());
                            ListenerModel model = new ListenerModel();
                            model.setCondition(false);
                            model.setMessage("NIK tidak ditemukan, Silahkan tambahkan data anda");
                        }
                    }

                    @Override
                    public void onFailure(Call<Penduduk> call, Throwable t) {
                        Log.d("__response","Find NIK FAIL");
                    }
                });
                return new ListenerModel();
            }
        });
        vForm.setOnSubmitListener(new FormAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(final String response) {
                FormKTP formResponse = new Gson().fromJson(response, FormKTP.class);
                ktp = formResponse.toKTP();
                vForm.showSubmitProgress(true);
                DbKTP.insert(ktp, new BaseRetrofitResponse() {
                    @Override
                    public void OnResponse(boolean success, Object data) {
                        ktp = (KTP) data;
                        if (success) {
                            doneActivity();
                        } else {
                            Toast.makeText(FormActivity.this, "Failed to create, check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                        vForm.showSubmitProgress(false);
                    }
                });
                Log.d("__response", response);
            }
        });
    }

    public void doneActivity(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
