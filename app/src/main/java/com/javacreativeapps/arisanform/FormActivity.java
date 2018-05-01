package com.javacreativeapps.arisanform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.javacreativeapps.arisan.adapter.ArisanAdapter;

public class FormActivity extends AppCompatActivity {

    RecyclerView mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mForm = findViewById(R.id.form);

        //Create Adapter
        final ArisanAdapter arisanAdapter = new ArisanAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mForm.setLayoutManager(mLayoutManager);
        mForm.setAdapter(arisanAdapter);
    }
}
