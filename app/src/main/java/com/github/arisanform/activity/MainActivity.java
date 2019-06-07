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
import com.github.arisan.helper.DateDeserializer;
import com.github.arisan.helper.ValueUpdater;
import com.github.arisan.model.ArisanListenerModel;
import com.github.arisan.helper.UriUtils;
import com.github.arisanform.model.Additional;
import com.github.arisanform.model.Book;
import com.github.arisanform.model.DB;
import com.github.arisanform.model.DataMaster;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.model.Order;
import com.github.arisan.helper.PreferenceHelper;
import com.github.arisanform.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson;

    RecyclerView vList;
    RecyclerView vForm;
    TextView vDummyText;
    MyAdapter adapter;
    List<Order> orderList = new ArrayList<>();
    ArisanAdapter arisanAdapter;

    PreferenceHelper preference;
    Realm realm = Realm.getDefaultInstance();

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
        /*orderList = (List) preference.loadObjList(DB.ORDER, new TypeToken<ArrayList<Order>>(){}.getType());
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
        vList.setAdapter(adapter);*/

        //FLOATING ADD
        FloatingActionButton vAdd = findViewById(R.id.add_todo);
        vAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataBook(new User());
            }
        });

        /*TESTING COLOR*/
        vDummyText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        vDummyText.setVisibility(View.GONE);

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

        ArisanForm arisanForm = new ArisanForm(this);
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
            @Override
            public void onSubmit(String response) {
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                Log.d("__RESPONE",response);
//                User newOrder = gson.fromJson(response,User.class);
//
//                Order order = new Order();
//                order.setId(orderList.size()+1);
//                order.setOrderer("Mio");
//
//                order = new ValueUpdater<Order>().update(order,newOrder,Order.class);
//                orderList.add(order);
//                preference.saveObj(DB.ORDER, orderList);
//                refreshList();
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
        arisanForm.addErrorListener("location", new ArisanListener.ErrorCondition() {
            @Override
            public ArisanListenerModel onError(String value) {
                ArisanListenerModel model = new ArisanListenerModel();
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
