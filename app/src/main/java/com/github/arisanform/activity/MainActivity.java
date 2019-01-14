package com.github.arisanform.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.arisanform.model.DataMaster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.github.arisan.ArisanForm;
import com.github.arisan.annotation.ArisanCode;
import com.github.arisan.helper.ObjectReader;
import com.github.arisanform.R;
import com.github.arisanform.model.Todo;
import com.github.arisanform.helper.PreferenceHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("dd-MM-yyyy");
    Gson gson = gsonBuilder.create();

    RecyclerView mTodo;
    TodoAdapter adapter;
    List<Todo> todoList = new ArrayList<>();

    int REQUEST_CODE = 1000;

    PreferenceHelper preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = new PreferenceHelper(this);

        mTodo = findViewById(R.id.todo_list);

        Type listType = new TypeToken<ArrayList<Todo>>(){}.getType();

        if (preference.load("todo", listType) != null)
            todoList = (List<Todo>) preference.load("todo", listType);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ArisanCode.REQUEST) {
            if (resultCode == RESULT_OK) {
                Log.d("__RESULT DATA", data.getData().toString());
                Todo todo = gson.fromJson(data.getData().toString(), Todo.class);
                todoList.add(todo);
                preference.update("todo", todoList);
                refreshTodoList();
                Log.d("__MESSAGE", "Added New Todo");
            }
        }
    }

    //BEGIN FORM
    public void addTodo(Todo todo) {
        ArisanForm arisanForm = new ArisanForm()
                .setIntent(this, FormActivity.class)
                .setModel(ObjectReader.getField(todo))
                .setTitle("Add Todo")
                .setSubmitText("Kirim");
        arisanForm.fillData("type",DataMaster.DUMMY_STRING_ARRAY);
        arisanForm.run();
    }

    public void refreshTodoList() {
        adapter = new TodoAdapter(this,todoList);
        mTodo.setAdapter(adapter);
    }
}
