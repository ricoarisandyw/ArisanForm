# ArisanForm 

[![](https://jitpack.io/v/ricoarisandyw/arisanform.svg)](https://jitpack.io/#ricoarisandyw/arisanform)

[![Logo](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/drawable/arisan_form_logo.png?raw=true "Logo")](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/drawable/arisan_form_logo.png?raw=true "Logo")

Convert Model into Form like magic

## Version 2.0.4
version 2.0.4+ using custom view for fix v1 bugs.
version 1.0.0+ convert RecyclerView (unresolved  RecyclerView bugs)

## Download

use jitpack.io

```maven
implementation 'com.github.ricoarisandyw:ArisanForm:2.0.4'
```

## How to use?

### 1. Create Model with annotation @Form above field.
```java
public class Nature {
    @Form(label = "Image Name",position = 1) //Default type is Edit Text
    private String image_name;
    @Form(type = Form.SPINNER,position = 2)
    private String category;
    @Form(type = Form.IMAGE,position = 3)
    private String image;
    @Form(type = Form.CHECKBOX,position = 4)
    private List<String> label;
    @Form(position = 5)
    private String description;
    @Form(type = Form.SLIDER,position = 6)
    private int score;
    @Form(label = "Pick at",type = Form.DATE,position = 7,format = "dd-MMM-yyyy")
    private String pick_at;
}
```

#### @Form attributes

| Form Type  | Default | Note |
| --------- | --------- | ------ |
| type   | Form.TEXT | editText |
| label  | field name | |
|hint|. . . | if not set, label also become hint|
| position  | -1 | it means random sequence |
| dateFormat | dd-MM-yyyy | use it just for Date category |
| required  | false | |
|realation|String.class|change with child class|


List of @Form types

TEXT, PASSWORD, NUMBER, EMAIL, CHECKBOX, SPINNER, DATE, DATETIME, 
TIME, BOOLEAN, FILE, SEARCH, ONETOMANY, RADIO, SLIDER, IMAGE,    AUTOCOMPLETE, CAMERA, GALLERY, ONELINETEXT, FLOWTEXT

### 2. Prepare the data
```java
//PREPARRING ARRAY DATA
public static String[] DATA_CATEGORY = {"Mountain","Beach","Forest","Museum"};
public static String[] DATA_LABEL = {"Visitable","Souvenir Shop","Guide","Events"};
```

### 3. Prepare Layout

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

	<com.github.arisan.ArisanForm
		android:id="@+id/arisan_form"
		android:layout_width="match_parent"
		android:layout_height="match_parent"/>

</LinearLayout>
```

### 4. Build your model

```java
vForm = findViewById(R.id.arisan_form);

//Assign your model
vForm.setModels(new Nature());

//Fill array data for checkbox, radio or spinner
vForm.fillData("category",Nature.DATA_CATEGORY);
vForm.fillData("label",Nature.DATA_LABEL);
  
//Build Adapter
vForm.setOnSubmitListener(result -> {/*Do something with json result*/});
vForm.buildForm();
```

### PREVIEW
this preview add styles font Montserrat, custom button, and color style that you can find in advance tutorial or in this github preview project.

<img src="./preview/First%20Look%201.png" alt="drawing" width="200"/><img src="./preview/Fist%20Look%202.png" alt="drawing" width="200"/><img src="./preview/Date%20Picker.png" alt="drawing" width="200"/><img src="./preview/Image%20Picker.png" alt="drawing" width="200"/><img src="./preview/Result%20Json.png" alt="drawing" width="200"/>

### TODO
* Send anything file format
* Send multiple files
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

#### thanks for all who believe in this library for their project. It funded me to make it greater form template for easier developing.
