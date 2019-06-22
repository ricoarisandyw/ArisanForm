package com.github.arisanform.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.DateDeserializer;
import com.github.arisan.helper.GsonUtils;
import com.github.arisan.helper.ObjectReader;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.helper.DummyCreator;
import com.github.arisanform.model.AllField;
import com.github.arisanform.model.ConditionFormC;
import com.github.arisanform.model.FormC;
import com.github.arisanform.model.KKK;
import com.github.arisan.helper.PreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FormRebuilder{

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson;
    ArisanPreparation preparation;

    RecyclerView vList;
    RecyclerView vForm;
    TextView vDummyText;

    PreferenceHelper preference;

    ArisanAdapter arisanAdapter;
    ArisanForm  form;
    FormC c;
    ConditionFormC cond;

    int iteration =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new PreferenceHelper(this);

        gsonBuilder.registerTypeAdapter(Date.class,new DateDeserializer("dd-MM-yyyy","HH:mm"));
        gson = gsonBuilder.create();

        vList = findViewById(R.id.data_list);
        vForm = findViewById(R.id.data_form);
        vDummyText = findViewById(R.id.dummy_text);

        vForm.setLayoutManager(new LinearLayoutManager(this));

        //Get Stored Data
        //FLOATING ADD
        FloatingActionButton vAdd = findViewById(R.id.add_todo);
//        vAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nextForm();
//            }
//        });
        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                form();
            }
        });

        preparation = new ArisanPreparation(this);
        preparation.useTitle(true);
        preparation.useSubmitButton(true);

        /*TESTING COLOR*/
        vDummyText.setVisibility(View.GONE);

        c = new FormC(this);
        cond = new ConditionFormC(this,form);
    }

    public void form(){
        form = new ArisanForm(this);
        form.setModel(new AllField());
        form.setTitle("ALL FIELD FORM");
        form.setSubmitText("SUBMIT");
        DummyCreator.fillDummyArray(form.getFieldData());
        form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                Log.e("__RESPONSE",response);
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
                    String path = utils.getPath();
                    Log.d("__Uri Path", utils.getUri().getPath());
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
}
