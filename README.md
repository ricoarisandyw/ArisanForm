# ArisanForm

Convert Model into Form

## Version 0.11

## Download

use jitpack.io

```maven
implementation 'com.github.ricoarisandyw:ArisanForm:0.11'
```

## How to use?

### 1. Create Model with annotation @Form in the variable.
```java
public class Todo {
    //Not generated in form
    int id;
    @Form(label="Insert Title *",position = 0)
    String title;
    @Form(position = 1)
    String note;
    @Form(category = Form.NUMBER)
    int quantity;
    @Form(category = Form.BOOLEAN,position = 3)
    boolean urgent;
    @Form(category = Form.BOOLEAN,position = 2)
    boolean important;
    @Form(category=Form.DATE,label = "Start Date",dateFormat="yyyy-MM-dd")
    Date startDate;
    @Form(category=Form.SPINNER)
    String category;
}
```

List of @Form variable

| Form Type  | Default | Note |
| --------- | --------- | ------ |
| category   | Form.TEXT | editText |
| label  | field name | |
| position  | -1 | it means random sequence |
| dateFormat | dd-MM-yyyy | use it just for Date category |
| required  | false | (WIP) |

List of @Form category

| Form Type | Status |
| --------- | ------ |
| TEXT      | enable |
| PASSWORD  | enable |
| NUMBER    | enable |
| SPINNER   | enable |
| DATE      | enable |
| BOOLEAN   | enable |
| CHECKBOX  | wip |
| TIME      | wip |

### 2. Prepare the data
```java
//Prepare your model. example :
Todo todo = new Todo();
todo.title = "Create my first form";
//ArisanPreparation will save your model data into local SharedPreference
ArisanPreparation preparation = new ArisanPreparation(context);
        preparation.setTitle("Create TODO");
        preparation.setSubmit("ADD TODO");
        preparation.setModel(todo);
        //fill data with String[] for spinner
        preparation.fillData("category",DataMaster.DUMMY_STRING_ARRAY);
```

### 3. Build your adapter and assign to your RecyclerView

```java
    ArisanForm arisanForm = new ArisanForm(context);
    arisanForm.setOnSubmitListener(new ArisanAdapter.OnSubmitListener() {
                @Override
                public void onSubmit(String response) {
                    //TODO: do something with your response here.
                    //sorry, but response is still json category
                }
            });
    //Build Adapter
    ArisanAdapter adapter = arisanForm.buildAdapter();

    recycler_view.setAdapter(adapter);
```

### TODO
* Create Sample TODO List Apps (WIP)
* Multiselect (data is array of string)
  * radio
* Send File. wow!!! can i do it?
* Chapta

## LICENSE

Copyright 2019 Rico Arisandy Wijaya

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
