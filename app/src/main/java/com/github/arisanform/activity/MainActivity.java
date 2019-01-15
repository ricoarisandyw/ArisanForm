package com.github.arisanform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.arisan.ArisanPreparation;
import com.github.arisanform.model.DataMaster;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisanform.R;
import com.github.arisanform.model.Todo;
import com.github.arisan.helper.PreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson = gsonBuilder.create();

    RecyclerView mTodo;
    TodoAdapter adapter;
    List<Todo> todoList = new ArrayList<>();

    PreferenceHelper preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new PreferenceHelper(this);

        mTodo = findViewById(R.id.todo_list);

        Type listType = new TypeToken<ArrayList<Todo>>(){}.getType();

        if (preference.loadObjList("todo", listType) != null)
            todoList = (List) preference.loadObjList("todo", listType);
        else
            todoList = new ArrayList<>();

        adapter = new TodoAdapter(this, todoList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mTodo.setLayoutManager(mLayoutManager);
        mTodo.setAdapter(adapter);

        FloatingActionButton vAddTodo = findViewById(R.id.add_todo);
        vAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo(new Todo());
            }
        });
        new TypeToken<String>(){}.getType();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ArisanCode.REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d("__RESULT DATA", data.getData().toString());
                Todo todo = gson.fromJson(data.getData().toString(), Todo.class);
                todoList.add(todo);
                preference.saveObj("todo", todoList);
                refreshTodoList();
                Log.d("__MESSAGE", "Added New Todo");
            }
        }
    }

    public void addTodo(Todo todo){
        //PREPARE DATA
        ArisanPreparation preparation = new ArisanPreparation(this);
        preparation.setModel(todo);
        preparation.fillData("type",DataMaster.DUMMY_STRING_ARRAY);
        if(todo.getTitle() == null){
            preparation.setTitle("Create TODO");
            preparation.setSubmit("INSERT");
        }else{
            preparation.setTitle("Update TODO");
            preparation.setSubmit("UPDATE");
        }

        Intent intent = new Intent(this,FormActivity.class);
        startActivityForResult(intent,ArisanCode.REQUEST);
    }

    public void refreshTodoList() {
        adapter = new TodoAdapter(this,todoList);
        mTodo.setAdapter(adapter);
    }
}
