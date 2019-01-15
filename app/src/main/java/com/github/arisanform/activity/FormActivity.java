package com.github.arisanform.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanPreparation;
import com.github.arisan.adapter.ArisanAdapter;
import com.github.arisanform.R;
import com.github.arisanform.model.Todo;

public class FormActivity extends AppCompatActivity {

    RecyclerView mForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mForm = findViewById(R.id.form);

        /*NOTED: if you want to change model, you can do re-assign YOUR MODEL
        because your model is stored in preference/local. example code bellow :
        ArisanPreparation arisanPreparation = new ArisanPreparation(this);
        arisanPreparation.setModel(new Todo());*/

        //Create Adapter
        ArisanForm arisanForm = new ArisanForm(this);
        arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                    @Override
                    public void onSubmit(String response) {
                        Intent data = new Intent();
                        data.setData(Uri.parse(response));
                        FormActivity.this.setResult(RESULT_OK,data);
                        FormActivity.this.finish();
                    }
                });
        //Build Adapter
        ArisanAdapter arisanAdapter = arisanForm.buildAdapter();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mForm.setLayoutManager(mLayoutManager);
        mForm.setAdapter(arisanAdapter);
    }
}
