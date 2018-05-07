package com.javacreativeapps.arisanform;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.javacreativeapps.arisan.ArisanForm;
import com.javacreativeapps.arisan.annotation.FormVar;
import com.javacreativeapps.arisan.helper.ObjectReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder();
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

        if (preference.load("todo", listType) != null)
            todoList = (List<Todo>) preference.load("todo", listType);
        else
            todoList = new ArrayList<>();

        adapter = new TodoAdapter(this, todoList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mTodo.setLayoutManager(mLayoutManager);
        mTodo.setAdapter(adapter);

        FloatingActionButton textView = findViewById(R.id.add_todo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo(new Todo());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FormVar.REQUEST) {
            if (resultCode == RESULT_OK) {
                Todo todo = gson.fromJson(data.getData().toString(), Todo.class);
                todoList.add(todo);
                preference.update("todo", todoList);
                refreshTodoList();
                Toast.makeText(MainActivity.this, "Added New Todo", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addTodo(Todo todo) {
        ArisanForm arisanForm = new ArisanForm();
        arisanForm.setIntent(this, FormActivity.class);
        arisanForm.setData(ObjectReader.getField(todo));
        arisanForm.setTitle("Add Todo");
        arisanForm.run();
    }

    public void refreshTodoList() {
        adapter.notifyDataSetChanged();
    }
}
