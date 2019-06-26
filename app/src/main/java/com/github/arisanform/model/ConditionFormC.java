package com.github.arisanform.model;

import android.content.Context;
import android.widget.Toast;

import com.github.arisan.ArisanForm;
import com.github.arisan.ArisanListener;
import com.github.arisan.helper.FieldUtils;
import com.github.arisan.model.ArisanFieldModel;
import com.github.arisan.model.ListenerModel;
import com.github.arisanform.R;
import com.github.arisanform.activity.FormRebuilder;

import java.util.List;

public class ConditionFormC {

    FormC c;
    Context context;
    ArisanForm form;
    FormRebuilder rebuilder;

    public ConditionFormC(FormRebuilder rebuilder, ArisanForm form) {
        this.context = (Context)rebuilder;
        c = new FormC(context);
        this.form = form;
    }

    public void MyToast(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public void showWhen(boolean correct, List<ArisanFieldModel> existing, ArisanFieldModel... model){
        if(correct){
            for(ArisanFieldModel m:model){
                FieldUtils.INSTANCE.insertOrUpdateField(m,existing);
            }
        }else{
            for(ArisanFieldModel m:model){
                FieldUtils.INSTANCE.removeField(m.getName(),existing);
            }
        }
        //Rebuild here
    }



    /*public ArisanForm condition_1(){
        form.addListener("103", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                MyToast(value);
                showWhen(value.equals(c.Str(R.string.tidak_bekerja)),form.getFieldData(),
                            c.getMap("103_1"),
                            c.getMap("103_2")
                        );
                return null;
            }
        });
        form.addCheckboxListener("108", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                MyToast(just_checked);
                showWhen(all_checked.contains(c.Str(R.string.asuransi_lain)),form.getFieldData(),
                        c.getMap("108_1")
                );
                return null;
            }
        });
        return form;
    }

    public ArisanForm condition_2(final ArisanForm form){
        form.addListener("201", new ArisanListener.Condition() {
            @Override
            public ListenerModel onValue(String value) {
                if(value.equals(c.Str(R.string.ya))){
                    FieldUtils.insertOrUpdateField(c.getMap("201_1"),form.getFieldData());
                    FieldUtils.insertOrUpdateField(c.getMap("202"),form.getFieldData());
                }else{
                    FieldUtils.removeField("202",form.getFieldData());
                    FieldUtils.removeField("201_1",form.getFieldData());
                }
                return null;
            }
        });
        form.addCheckboxListener("204", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                if(just_checked.equals(c.Str(R.string.autoimun))){
                    //Go to signature
                }else{
                    showWhen(all_checked.contains(c.Str(R.string.autoimun)),form.getFieldData(),
                            null);
                    //context.startActivity(new Intent());
                    showWhen(all_checked.contains(c.Str(R.string.lain)),form.getFieldData(),
                            c.getMap("204_1"));
                }
                return null;
            }
        });
        form.addCheckboxListener("205_2", new ArisanListener.CheckboxCondition() {
            @Override
            public ListenerModel onChecked(String just_checked, List<String> all_checked) {
                showWhen(!all_checked.contains(c.Str(R.string.asuransi_lain)),form.getFieldData(),
                        c.getMap("205_3")
                );
                return null;
            }
        });

        return form;
    }

    public static void addOthers(){

    }*/
}
