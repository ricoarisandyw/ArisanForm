# ArisanForm

Arisan is library to generate Form using Model. You only need to setting form in your Model. 
Arisan is taken from Indonesian game name also creator last name "Arisandy Wijaya".
Want to contribute?

## Version 0.2-alpha

### What's New?
* Set Error Message in Edit Text
* EditText for Number and Password
* Add Default Value
* Title and Submit button is generated in ArisanAdapter
* Boolean type using Switch

### TODO
* Create Sample TODO List Apps
* Multiselect (First step is single data like String only or Integer only)
  * checkbox
  * radio
* Send File

### BUG. If you know, please help me
* Unsorted Field (Booelan always on begining and Integer always on last)
I can put priority/step variable in @Form. But, I think is better if we can sort field on model without set field priority.

## DOWNLOAD

```maven
implementation 'com.github.ricoarisandyw:ArisanForm:0.2-alpha'
```

## HOW TO USE?

1. Create Model with @Form in the variable.
example 
```java
public class Student{@Form
    private String name;

    @Form(type = Form.PASSWORD)
    private int password;

    @Form(type = Form.BOOLEAN)
    private boolean graduated;

    int number;//Not Generated
}
```

2. Create FormActivity with RecycleView Inside
3. Create builder from activity that will send data to FormActivity

```java
//in MainActivity.java
        /*INITIALIZE INTENT*/
        ArisanForm addStudent = new ArisanForm();
        addStudent.intent(MainActivity.this, FormActivity.class);
        addStudent.setTitle("Add Student");

        /*MODIFY DATA*/
        List<ArisanField> list = ObjectReader.getField(student);
        //Set Default Data
        // Noted : Not Handle NPE(Null Pointer Exception)
        FieldFiller.fill(list,"name",student.getName());
        FieldFiller.fill(list,"graduated",student.isGraduated());

        /*ERROR CONDITION*/
        if(student.getName()!=null&&student.getName().equalsIgnoreCase("rico"))
            FieldFiller.setError(list,"name","already exist");
        
        /*LAST STEP*/
        addStudent.setData(list);
        addStudent.run();
```

4. Add this code in FormActivity to generate Form
```java
        //in FormActivity.java
        //Create Adapter
        final ArisanAdapter arisanAdapter = new ArisanAdapter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mForm.setLayoutManager(mLayoutManager);
        mForm.setAdapter(arisanAdapter);
```
5. Get Result from FormActivity

```java
    //in MainActivity.java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FormVar.REQUEST){
            if(resultCode==RESULT_OK) {
                //Do something with result
                Student student = gson.fromJson(data.getData().toString(), Student.class);
                Toast.makeText(MainActivity.this, student.getName(), Toast.LENGTH_SHORT).show();
                addStudent(student);
            }
        }
    }
```

## LICENSE

Copyright 2018 Rico Arisandy Wijaya

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
