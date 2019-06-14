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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.annotation.Model;
import com.github.arisan.helper.DateDeserializer;
import com.github.arisan.helper.FieldUtils;
import com.github.arisan.helper.RadioUtils;
import com.github.arisan.helper.ValueUpdater;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisan.helper.UriUtils;
import com.github.arisan.model.template.FormCheckbox;
import com.github.arisan.model.template.FormDate;
import com.github.arisan.model.template.FormSlider;
import com.github.arisanform.model.Additional;
import com.github.arisanform.model.ArisanText;
import com.github.arisanform.model.DB;
import com.github.arisanform.model.DataMaster;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.model.Order;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisanform.model.Radio;
import com.github.arisanform.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson;

    RecyclerView vList;
    RecyclerView vForm;
    TextView vDummyText;
    MyAdapter adapter;
    List<Order> orderList = new ArrayList<>();

    PreferenceHelper preference;
    Realm realm = Realm.getDefaultInstance();

    ArisanAdapter arisanAdapter;

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
        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForm();
            }
        });

        /*TESTING COLOR*/
        vDummyText.setVisibility(View.GONE);
    }

    public void showForm(){
        vForm.setVisibility(View.VISIBLE);

        List<ArisanFieldModel> list = new ArrayList<>();
        final String[] kawin = {"Belum pernah kawin","Kawin","Duda/Janda", Model.OTHERS};

        ArisanFieldModel model = Radio.getField();
        model.setData(RadioUtils.convertToRadio(kawin));
        model.setArisanListener(new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                if(value.equals("Kawin")){
                    ArisanForm form = new ArisanForm(getBaseContext());
                    form.copyAdapterFrom(arisanAdapter);

                    ArisanFieldModel new_field = FormDate.getModel();
                    new_field.setName("slider");
                    new_field.setLabel("Berapakah jumlah makanan anda yang biasanya anda makan ?");
                    FieldUtils.insertOrUpdateField(new_field,form.getFieldData());

                    ArisanFieldModel new_field2 = FormCheckbox.getModel();
                    new_field2.setName("New Field 2");
                    new_field2.setLabel("Berapakah makanan yang anda makan?");
                    new_field2.setData(kawin);
                    new_field2.setCheckboxListener(new ArisanListener.CheckboxCondition() {
                        @Override
                        public ListenerModel onChecked(String checked, List<String> all_checked) {
                            if(all_checked.contains("Kawin")){
                                Toast.makeText(MainActivity.this, "Kamu kawin", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, "Kamu belum kawin", Toast.LENGTH_SHORT).show();
                            }
                            return null;
                        }
                    });
                    FieldUtils.insertOrUpdateField(new_field2,form.getFieldData());

                    arisanAdapter = form.buildAdapter();
                    vForm.setAdapter(arisanAdapter);
                }else{
                    ArisanForm form = new ArisanForm(getBaseContext());
                    form.copyAdapterFrom(arisanAdapter);
                    FieldUtils.removeField("New Field",form.getFieldData());
                    FieldUtils.removeField("New Field 2",form.getFieldData());

                    arisanAdapter = form.buildAdapter();
                    vForm.setAdapter(arisanAdapter);
                }
                return new ListenerModel();
            }
        });

        list.add(model);
        list.add(FormDate.getModel());

        ArisanForm form = new ArisanForm(this);
        form.setFieldData(list);
        form.setOnSubmitListener(submitListener);
        arisanAdapter = form.buildAdapter();
        vForm.setAdapter(arisanAdapter);
    }

    ArisanAdapter.OnSubmitListener submitListener = new ArisanAdapter.OnSubmitListener() {
        @Override
        public void onSubmit(String response) {
            ArisanForm form = new ArisanForm(getBaseContext());
            form.copyAdapterFrom(arisanAdapter);
            arisanAdapter = form.buildAdapter();
            vForm.setAdapter(form.buildAdapter());
            Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ArisanCode.REQUEST_FILE){
            Uri uri = data.getData();
            if (uri != null) {
                UriUtils utils = new UriUtils(this,uri);
                String path = utils.getPath();
                Log.d("__Uri Path", utils.getUri().getPath());
                arisanAdapter.updateFile("photo",uri);
            }else{
                Log.d("__Uri", "Uri is null");
            }
        }
    }

    public void refreshList() {
        adapter.notifyDataSetChanged();
    }
}
