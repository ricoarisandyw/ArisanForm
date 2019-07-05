package com.github.arisanform.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.github.arisan.helper.DateDeserializer;
import com.github.arisan.helper.DummyCreator;
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
import com.github.arisanform.model.ManyField;
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
import java.util.Date;
import java.util.List;

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

        RealmList<String> data;
        String[] arr_str = {"satu","dua","tiga"};
        String json = new Gson().toJson(arr_str);
        data = new Gson().fromJson(json,new TypeToken<RealmList<String>>(){}.getType());
        for(String d:data){
            System.out.println("__DATA "+d);
        }

        askPermission();

        preference = new PreferenceHelper(this);

        gsonBuilder.registerTypeAdapter(Date.class,new DateDeserializer("dd-MM-yyyy","HH:mm"));
        gson = gsonBuilder.create();

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
                form = new ArisanForm(MainActivity.this);
                form.setTitle("ALL FIELD");
                form.setBackground(R.drawable.btn_success);
                form.setLabelColor(R.color.colorDanger);
//                form.setFieldData(DummyCreator.fillDummyArray(ObjectReader.getField(new AllField())));
                form.setModel(new ManyField());

                String[] baru = {"baru","satu","dua"};
                form.fillData("checkbox",baru);

                form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                    @Override
                    public void onSubmit(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.e("__RESPONSE", response);
                        vForm.setVisibility(View.GONE);
                        my_data = new Gson().fromJson(response,AllField.class);

                        File imgFile = new File(my_data.getImage());

                        if(imgFile.exists()){
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            ImageView myImage = (ImageView) findViewById(R.id.data_image);
                            myImage.setImageBitmap(myBitmap);
                        }else{
                            Log.d("IMAGE__","NOT FOUND");
                        }
                    }
                });
                arisanAdapter = form.buildAdapter();
                vForm.setAdapter(arisanAdapter);
                vForm.setVisibility(View.VISIBLE);
//                form();
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

    /*

    public void lapak(){
        form = new ArisanForm(this);
        form.setOnSubmitListener(submitListener);
        form.setFieldData(ObjectReader.getField(new KKK()));
        form.addChildListener("data_kk","search", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
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

    //TODO: code untuk memunculkan form
    public void nextForm(){
        form = new ArisanForm(this);
        preference.save("saved_position", String.valueOf(1));
        preparation.setTitle("Form C ke "+iteration);
        form.setOnSubmitListener(submitListener);
        switch (iteration){
            case 1:{condition_1();break;}
            case 2:{condition_2();break;}
            case 3:{condition_3();break;}
            case 4:{condition_4();break;}
            case 5:{condition_5();break;}
            case 6:{condition_6();break;}
        }
        rebuild(form);
        vForm.setVisibility(View.VISIBLE);
    }

    ArisanAdapter.OnSubmitListener submitListener = new ArisanAdapter.OnSubmitListener() {
        @Override
        public void onSubmit(String response) {
            //===START OF PROJECT ABID
            Map<String,String> data = GsonUtils.convertToMap(response);
            //Menyimpan ke shared preference
            for(String s:data.keySet()){
                preference.save(s,data.get(s));
            }
            iteration++;
            nextForm();
            //===END OF PROJECT ABID
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ArisanCode.REQUEST_FILE){
            try {
                Uri uri = data.getData();
                if (uri != null) {
                    UriUtils utils = new UriUtils(this, uri);
                    String path = utils.getRealPath();
                    Log.d("__Uri Path", utils.getUri().getRealPath());
                    arisanAdapter.updateFile("photo", uri);
                } else {
                    Log.d("__Uri", "Uri is null");
                }
            }catch (Exception ignore){Log.e("URI ERROR","Failed to get URI");}
        }
    }

    @Override
    public void rebuild(ArisanForm arisan) {
        //Get existing
        //Add new
        arisanAdapter = form.buildAdapter();
        vForm.swapAdapter(arisanAdapter,true);
        scrollTo();
    }

    public void condition_1(){
        form.setFieldData(c.all_1());
        form.addListener("103", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                if(!value.equals(Model.OTHERS)) {
                    form.copyFieldFromAdapter(arisanAdapter);
                    ConditionUtils.whenShow(!(value.equals(Model.OTHERS) || value.equals(c.Str(R.string.tidak_bekerja))), form.getFieldData(),
                            c.getMap("103_1"));
                    rebuild(form);
                    scrollTo();
                }
                return null;
            }
        });
        form.addCheckboxListener("108", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                Log.d("__DATA",new Gson().toJson(all_checked));
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(all_checked.contains(c.Str(R.string.asuransi_lain)),form.getFieldData(),
                        c.getMap("108_1"));
                rebuild(form);
                return null;
            }
        });
    }

    public void condition_2(){
        form.setFieldData(c.all_2());
        form.addListener("201", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                cond.MyToast(value);
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("201_1")
                        );
                rebuild(form);
                return null;
            }
        });
        form.addCheckboxListener("204", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                if(just_checked.equals(c.Str(R.string.autoimun))){
                    //go to signature
                    submitListener.onSubmit(arisanAdapter.getResult());
                    Toast.makeText(MainActivity.this, "Anda diarahkan ke halaman selanjutnya", Toast.LENGTH_SHORT).show();
                }else{
                    form.copyFieldFromAdapter(arisanAdapter);
                    ConditionUtils.whenShow(all_checked.contains(c.Str(R.string.lain)),form.getFieldData(),
                            c.getMap("204_1")
                    );
                    rebuild(form);
                }
                return null;
            }
        });
        form.addListener("205_2", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                if(value.equals(c.Str(R.string.autoimun))){
                    //go to signature
                }else{
                    form.copyFieldFromAdapter(arisanAdapter);
                    ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                            c.getMap("205_3")
                    );
                    rebuild(form);
                }
                return null;
            }
        });
        form.addListener("206_1", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                if(value.equals(c.Str(R.string.autoimun))){
                    //go to signature
                }else{
                    form.copyFieldFromAdapter(arisanAdapter);
                    ConditionUtils.whenShow(value.equals(c.Str(R.string.kanker)),form.getFieldData(),
                            c.getMap("206_0_1")
                    );
                    rebuild(form);
                }
                return null;
            }
        });
        form.addListener("206_2", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("206_2_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("207", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("207_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("211", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.lain)),form.getFieldData(),
                        c.getMap("211_0_1")
                );
                rebuild(form);
                return null;
            }
        });


    }

    public void condition_3(){
        form.setFieldData(c.all_3());
        form.addListener("301", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya_sebutkan_minimal_3)),form.getFieldData(),
                        c.getMap("301_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("303", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("303_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("304", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("304_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("306", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("306_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("307", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("307_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("308", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("308_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("307", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.contains(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("307_1")
                );
                rebuild(form);
                return null;
            }
        });
    }

    public void condition_4(){
        form.setFieldData(c.all_4());
        form.addCheckboxListener("402", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                boolean condition = all_checked.contains(c.Str(R.string.lain))||all_checked.contains(c.Str(R.string.obat_china));
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(condition
                        ,form.getFieldData(),
                            c.getMap("402_0_1"));
                rebuild(form);
                return null;
            }
        });
        form.addListener("402_4", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("402_4_1"));
                rebuild(form);
                return null;
            }
        });
        form.addListener("404", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(value.equals(c.Str(R.string.lain))),form.getFieldData(),
                        c.getMap("404_0_1"));
                rebuild(form);
                return null;
            }
        });
    }

    public void condition_5(){
        form.setFieldData(c.all_5());
        form.addCheckboxListener("501", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                boolean condition = all_checked.contains(c.Str(R.string.lain))||all_checked.contains(c.Str(R.string.obat_china));
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(condition
                        ,form.getFieldData(),
                        c.getMap("501_1"));
                rebuild(form);
                return null;
            }
        });
    }

    public void condition_6(){
        form.setFieldData(c.all_6());
        form.addListener("601", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.bekerja)),form.getFieldData(),
                        c.getMap("601_1"));
                rebuild(form);
                return null;
            }
        });
        form.addListener("602", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.bekerja)),form.getFieldData(),
                        c.getMap("602_1"));
                rebuild(form);
                return null;
            }
        });
    }
*/

    public void scrollTo(){
        int saved_position = Integer.parseInt(preference.load("saved_position"));
        vForm.scrollToPosition(saved_position);
    }

    @Override
    public void rebuild(ArisanForm form) {

    }

    public RealmList<String> convertArrayToRealm(List<String> list){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(list),new TypeToken<RealmList<String>>(){}.getType());
    }
}
