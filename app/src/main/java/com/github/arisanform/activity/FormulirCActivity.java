package com.github.arisanform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.GsonUtils;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisan.model.ListenerModel;
import com.github.arisanform.R;
import com.github.arisanform.model.FormC;
import com.github.arisanform.model.abid.Pertanyaan;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormulirCActivity extends AppCompatActivity {
    RecyclerView vForm;
    ArisanAdapter adapter;
    FormC c;
    int iteration = 1;
    ArisanForm form;
    ArisanAdapter arisanAdapter;
    PreferenceHelper preference;
    Map<String,String> dataList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: ubah ke R.layout.activity_formulir_c
        //setContentView(R.layout.activity_formulir_c);
        setContentView(R.layout.activity_main);

        c = new FormC(this);
        preference = new PreferenceHelper(this);

        //TODO: ubah ke R.id.recycler_view_formulir_c
        //vForm = findViewById(R.id.recycler_view_formulir_c);
        vForm = findViewById(R.id.data_form);

        vForm.setLayoutManager(new LinearLayoutManager(this));
        nextForm();

        //TODO: hapus nanti
        //vForm.setVisibility(View.VISIBLE);
    }

    //TODO: code untuk memunculkan form
    public void nextForm(){
        preference.save("saved_position", String.valueOf(1));
        form = new ArisanForm(this);
        form.setTitle("Form Data C");
        form.setBackground(R.drawable.btn_primary);
        form.setOnSubmitListener(submitListener);
        switch (iteration){
            case 1:{condition_1();form.setSubmitText("NEXT >");break;}
            case 2:{condition_2();form.setSubmitText("NEXT >");break;}
            case 3:{condition_3();form.setSubmitText("NEXT >");break;}
            case 4:{condition_4();form.setSubmitText("NEXT >");break;}
            case 5:{condition_5();form.setSubmitText("NEXT >");break;}
            case 6:{condition_6();form.setSubmitText("FINISH");break;}
        }
        rebuild(form);
        vForm.setVisibility(View.VISIBLE);
    }

    ArisanAdapter.OnSubmitListener submitListener = new ArisanAdapter.OnSubmitListener() {
        @Override
        public void onSubmit(String response) {
            //===START OF PROJECT ABID
            Map<String,String> data = GsonUtils.convertToMap(response);
            dataList.putAll(data);
            if(iteration<6){
                iteration++;
                nextForm();
            }else{
                saveToRealm();
                //TODO: ubah Main ke SignatureActivity.class
                //startActivity(new Intent(FormulirCActivity.this,SignatureActivity.class));
                startActivity(new Intent(FormulirCActivity.this,MainActivity.class));
            }
            //===END OF PROJECT ABID
        }
    };

    public void saveToRealm(){
        //Menyimpan ke shared preference
        ArrayList<Pertanyaan> pertanyaanList = new ArrayList<>();
        PreferenceHelper helper = new PreferenceHelper(this);

        for(String key:dataList.keySet()){
            preference.save(key,dataList.get(key));
            String jawaban = dataList.get(key);

            if(jawaban.contains("[")){//if answer is array
                jawaban = jawaban.replace("[","");
                jawaban = jawaban.replace("]","");
            }

            Pertanyaan pertanyaan = new Pertanyaan(key,c.getMap(key).getLabel(),jawaban);
            pertanyaanList.add(pertanyaan);
        }

        helper.saveObj("list_pertanyaan",pertanyaanList);
    }

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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
        form.addListener("104", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value, ArisanAdapter adapter) {
                Toast.makeText(FormulirCActivity.this, value, Toast.LENGTH_SHORT).show();
                form.copyFieldFromAdapter(adapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.tidak)),form.getFieldData(),c.getMap("104_1"));
                rebuild(form);
                return null;
            }
        });
        form.addCheckboxListener("108", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("201_1")
                );
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("202")
                );
                rebuild(form);
                return null;
            }
        });
        form.addCheckboxListener("204", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked,ArisanAdapter adapter) {
                if(just_checked.equals(c.Str(R.string.autoimun))){
                    //go to signature
                    submitListener.onSubmit(arisanAdapter.getResult());
                    Toast.makeText(getBaseContext(), "Anda diarahkan ke halaman selanjutnya", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(FormulirCActivity.this,SignatureActivity.class));
                    finish();
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
        form.addCheckboxListener("205", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked, ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(all_checked.size()>0,form.getFieldData(),
                        c.getMap("205_1")
                );
                ConditionUtils.whenShow(all_checked.size()>0,form.getFieldData(),
                        c.getMap("205_2")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("205_2", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                if(value.equals(c.Str(R.string.autoimun))){
                    //go to signature
                    //startActivity(new Intent(FormulirCActivity.this,SignatureActivity.class));
                    finish();
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                if(value.equals(c.Str(R.string.autoimun))){
                    //go to signature
                    //startActivity(new Intent(FormulirCActivity.this,SignatureActivity.class));
                    finish();
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("207_1")
                );
                rebuild(form);
                return null;
            }
        });
        form.addCheckboxListener("210", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked, ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(all_checked.contains(c.Str(R.string.minuman_ringan)),form.getFieldData(),
                        c.getMap("210_0_1")
                );
                ConditionUtils.whenShow(all_checked.contains(c.Str(R.string.minuman_ringan)),form.getFieldData(),
                        c.getMap("210_0_2")
                );
                ConditionUtils.whenShow(all_checked.contains(c.Str(R.string.minuman_ringan)),form.getFieldData(),
                        c.getMap("210_0_3")
                );
                rebuild(form);
                return null;
            }
        });
        form.addListener("211", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onChecked(String just_checked, List<String> all_checked,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.ya)),form.getFieldData(),
                        c.getMap("402_4_1"));
                rebuild(form);
                return null;
            }
        });
        form.addListener("404", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
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
            public ListenerModel onChecked(String just_checked, List<String> all_checked,ArisanAdapter adapter) {
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
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.bekerja)),form.getFieldData(),
                        c.getMap("601_1"));
                rebuild(form);
                return null;
            }
        });
        form.addListener("602", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value,ArisanAdapter adapter) {
                form.copyFieldFromAdapter(arisanAdapter);
                ConditionUtils.whenShow(value.equals(c.Str(R.string.bekerja)),form.getFieldData(),
                        c.getMap("602_1"));
                rebuild(form);
                return null;
            }
        });
    }

    public void scrollTo(){
        int saved_position = Integer.parseInt(preference.load("saved_position"));
        vForm.scrollToPosition(saved_position);
    }
}
