package com.github.arisanform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisan.helper.DateDeserializer;
import com.github.arisan.helper.ValueUpdater;
import com.github.arisanform.model.DB;
import com.github.arisanform.model.DataMaster;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.model.Order;
import com.github.arisan.helper.PreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson;

    RecyclerView vList;
    RecyclerView vForm;
    MyAdapter adapter;
    List<Order> orderList = new ArrayList<>();

    PreferenceHelper preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new PreferenceHelper(this);

        gsonBuilder.registerTypeAdapter(Date.class,new DateDeserializer("dd-MM-yyyy","HH:mm"));
        gson = gsonBuilder.create();

        vList = findViewById(R.id.data_list);
        vForm = findViewById(R.id.data_form);

        vForm.setLayoutManager(new LinearLayoutManager(this));
        //Get Stored Data
        orderList = (List) preference.loadObjList(DB.ORDER, new TypeToken<ArrayList<Order>>(){}.getType());
        if(orderList ==null)
            orderList = new ArrayList<>();

        adapter = new MyAdapter(this, orderList);
        adapter.setOnEditListener(new MyAdapter.OnEditListener() {
            @Override
            public void onEdit(Order order) {
                addData(order);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        vList.setLayoutManager(mLayoutManager);
        vList.setAdapter(adapter);

        FloatingActionButton vAdd = findViewById(R.id.add_todo);
        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { addData(new Order());
            }
        });

        gsonTest();
    }

    public void gsonTest(){
        Date a;
        String myDate = "\"01-01-2015\"";
        String myHour = "\"12:34\"";

        //Test 1
        a = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer("dd-MM-yyyy","HH:mm")).create().fromJson(myDate,Date.class);
        Log.d("__Test 1",a.toString());
        //Test2
        a = new GsonBuilder().setDateFormat("dd-MM-yyyy").create().fromJson(myDate,Date.class);
        Log.d("__Test 2",a.toString());

    }

    public void addData(Order order){
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

        ArisanForm arisanForm = new ArisanForm(this).setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
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

    public void refreshList() {
        adapter.notifyDataSetChanged();
//        adapter = new MyAdapter(this, orderList);
//        vList.setAdapter(adapter);
    }
}
