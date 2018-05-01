package com.javacreativeapps.arisanform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javacreativeapps.arisan.ArisanForm;
import com.javacreativeapps.arisan.annotation.FormVar;
import com.javacreativeapps.arisan.helper.ObjectReader;
import com.javacreativeapps.arisan.model.ArisanField;
import com.javacreativeapps.arisan.helper.FieldFiller;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent(new Student());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FormVar.REQUEST){
            if(resultCode==RESULT_OK) {
                Student student = gson.fromJson(data.getData().toString(), Student.class);
                Toast.makeText(MainActivity.this, student.getName(), Toast.LENGTH_SHORT).show();
                addStudent(student);
            }
        }
    }

    public void addStudent(Student student){
        /*INITIALIZE INTENT*/
        ArisanForm addStudent = new ArisanForm();
        addStudent.intent(MainActivity.this, FormActivity.class);
        addStudent.setTitle("Add Student");

        /*MODIFY DATA*/
        List<ArisanField> list = ObjectReader.getField(student);
        //Set Default Data
        FieldFiller.fill(list,"name",student.getName());

        /*ERROR CONDITION*/
        if(student.getName()!=null&&student.getName().equalsIgnoreCase("rico"))
            FieldFiller.setError(list,"name","already exist");

        FieldFiller.fill(list,"graduated",student.isGraduated());

        /*LAST STEP*/
        addStudent.setData(list);
        addStudent.run();
    }
}
