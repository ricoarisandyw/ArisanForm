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
import com.github.arisan.helper.ValueUpdater;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisan.helper.UriUtils;
import com.github.arisanform.helper.DBUtils;
import com.github.arisanform.model.Additional;
import com.github.arisanform.model.DB;
import com.github.arisanform.model.DataMaster;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.model.Order;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisanform.model.Radio;
import com.github.arisanform.model.Survey;
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
                addSurvey(new Survey());
            }
        });

        /*TESTING COLOR*/
        vDummyText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        vDummyText.setVisibility(View.GONE);
    }

    public void addSurvey(Survey survey){
        vForm.setVisibility(View.VISIBLE);

//        ArisanPreparation preparation = new ArisanPreparation(this);
//        preparation.setModel(survey);
//        preparation.fillData("data1",Survey.yesNo);

        List<ArisanFieldModel> list = new ArrayList<>();
        String[] kawin = {"Belum pernah kawin","Kawin","Duda/Janda", Model.OTHERS};

        ArisanFieldModel model = Radio.getField();
        model.setData(kawin);

        list.add(model);

//        preparation.fillData("data0",kawin);
//
//        preparation.setSubmit("Submit");
//        preparation.useSubmitButton(true);

        final ArisanForm form = new ArisanForm(this);
        form.setFieldData(list);
        form.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
            }
        });
        arisanAdapter = form.buildAdapter();
        vForm.setAdapter(arisanAdapter);
    }

    public void addDataBook(User user){
        vForm.setVisibility(View.VISIBLE);

        //SUCCESS null
        Log.d("__TO JSON",new Gson().toJson(user));

        //PREPARE DATA
        ArisanPreparation preparation = new ArisanPreparation(this);
        preparation.clearData();
        preparation.setModel(user);
        preparation.setSubmit("SUBMIT");
        preparation.setSubmitBackground(R.drawable.gradient);
        preparation.useSubmitButton(true);

        //Make builder
        ArisanForm arisanForm = new ArisanForm(this);

        arisanForm.addListener("data1", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                ListenerModel arisanListener = new ListenerModel();
                arisanListener.setCondition(true);
                if(value.contains("hallo")){
                    arisanListener.setCondition(false);
                    arisanListener.setMessage("Jangan pakai hallo");
                }
                return arisanListener;
            }
        });
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                Log.d("__RESPONE",response);
                vForm.setVisibility(View.GONE);
            }
        });
        arisanAdapter = arisanForm.buildAdapter();
        vForm.setAdapter(arisanAdapter);
    }

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

    public void addData(Order order){
        List<Additional> additionals = new ArrayList<>();
        Additional additional = new Additional();
        additional.setName("Misis");
        additional.setQuantity(3);
        additionals.add(additional);
        order.setAdditionals(additionals);

        vForm.setVisibility(View.VISIBLE);
        //PREPARE DATA
        ArisanPreparation preparation = new ArisanPreparation(this);
        preparation.setModel(order);
        preparation.fillData("menu",DataMaster.MENU);
        preparation.fillData("topping",DataMaster.TOPPING);
        preparation.setSubmitBackground(R.drawable.gradient);

        if(order.getId() == 0){
            preparation.setTitle("New Order");
            preparation.setSubmit("INSERT");
        }else{
            preparation.setTitle("Copy Order");
            preparation.setSubmit("COPY");
        }

        ArisanForm arisanForm = new ArisanForm(this);
        arisanForm.addViewMod("location", new ArisanListener.ViewMod() {
            @Override
            public Object modding(Object view) {
                EditText editText = (EditText)view;
                editText.setText("Anone");
                return editText;
            }
        });
        arisanForm.addListener("location", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                ListenerModel model = new ListenerModel();
                if(value.equals("rico")){
                    model.setMessage("username found");
                    model.setCondition(true);
                }else{
                    model.setMessage("username not found");
                    model.setCondition(false);
                }
                return model;
            }
        }).setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Order newOrder = gson.fromJson(response,Order.class);

                Order order = new Order();
                order.setId(orderList.size()+1);
                order.setOrderer("Mio");

                order = new ValueUpdater<Order>().update(order,newOrder,Order.class);
                orderList.add(order);
                preference.saveObj(DB.ORDER, orderList);
                refreshList();
                vForm.setVisibility(View.GONE);
            }
        });
        vForm.setAdapter(arisanForm.buildAdapter());
    }

    interface ArisanCallback{
        public void onSomething(String text);
    }

    public void refreshList() {
        adapter.notifyDataSetChanged();
    }
}
