
# ArisanForm   
  
[![](https://jitpack.io/v/ricoarisandyw/arisanform.svg)](https://jitpack.io/#ricoarisandyw/arisanform)  
  
[![Logo](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/drawable/arisan_form_logo.png?raw=true "Logo")](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/drawable/arisan_form_logo.png?raw=true "Logo")  
  
Convert Model into Form like magic  

## Download  
  
use jitpack.io  
  
```maven  
implementation 'com.github.ricoarisandyw:ArisanForm:2.1.0'  
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
| label  | field name | Add properties label |  
|hint|. . . | if not set, label also become hint|  
| position  | -1 | it means random sequence |  
| dateFormat | dd-MM-yyyy | use it just for Date /& Time type |  
| required  | false | |  
|realation|Class|change with child class|  
|custom_class|Class|Class of custom form
  
  
List of @Form types  
  
TEXT, PASSWORD, NUMBER, EMAIL, CHECKBOX, SPINNER, DATE, DATETIME,   
TIME, BOOLEAN, FILE, SEARCH, ONETOMANY, RADIO, SLIDER, IMAGE,    AUTOCOMPLETE, CAMERA, GALLERY, ONELINETEXT, FLOWTEXT  , CUSTOM
  
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


## Version 2.1.0 
### Now you can put Custom Form inside ArisanForm!
In Past, ArisanForm providing creators template. But now you also can put your layout design into ArisanForm. It also worked when you put any UI Library there.

I'll give an example. Let's put https://github.com/Chrisvin/RubberPicker inside ArisanForm :

#### Step 1 : *Prepare your layout, just like create item for RecyclerView*
I give it name ```R.layout.custom_input_range```
```xml
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  xmlns:app="http://schemas.android.com/apk/res-auto"  
  android:orientation="vertical">  
  
    <TextView  
	  android:id="@+id/custom_range_label"  
	  android:text="Label"  
	  android:layout_marginTop="10dp"  
	  android:layout_marginBottom="10dp"  
	  android:layout_width="wrap_content"  
	  android:layout_height="wrap_content"/>  
  
    <com.jem.rubberpicker.RubberSeekBar  
	  app:minValue="20"  
	  app:maxValue="80"  
	  app:elasticBehavior="cubic"  
	  app:dampingRatio="0.3"  
	  app:stiffness="300"  
	  app:stretchRange="24dp"  
	  app:defaultThumbRadius="16dp"  
	  app:normalTrackWidth="4dp"  
	  app:highlightTrackWidth="8dp"  
	  app:normalTrackColor="#AAAAAA"  
	  app:highlightTrackColor="#BA1F33"  
	  app:defaultThumbInsideColor="#FFF"  
	  app:highlightDefaultThumbOnTouchColor="#CD5D67"  
	  android:layout_width="match_parent"  
	  android:layout_height="wrap_content"/>  
  
</LinearLayout>
```
  #### Step 2 : Prepare your class implement with ArisanCustomForm
I give it name ```MyCustomRange.class```
```java
public class MyCustomRange implements ArisanCustomForm {  
    @Override  
    public int getLayout() {  
        return R.layout.custom_input_range;  
    }  
  
    @Override  
    public void onCreated(FormViewHolder holder, FormAdapter adapter) {  
        holder.data.setValue(2000);  
  
        ((TextView) holder.view.getView().findViewById(R.id.custom_range_label)).setText(holder.data.getLabel());  
    }  
}
```
#### Step 3 (Last) : Use Form.Custom in your model 
Implement it like usual
```java
@Form(type = Form.CUSTOM, label = "Budget", position = 9, custom_class = MyCustomRange.class)  
private int budget;
```

#### Result

<img src="./preview/Custom%20Form.jpg" alt="drawing" width="200"/>

#### Full Usage Example (Custom Toogel/ON OFF Button)
##### Layout : [https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/layout/custom_form.xml](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/res/layout/custom_form.xml)
##### Class : [https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/activity/MyCustomToggleForm.java](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/activity/MyCustomToggleForm.java)
##### Model : [https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/model/Nature.java](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/model/Nature.java)
##### Activity : [https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/activity/SecondActivity.java](https://github.com/ricoarisandyw/ArisanForm/blob/master/app/src/main/java/com/github/arisanform/activity/SecondActivity.java)


### TODO
I won't do bellow cause I have created ArisanCustomForm with purpose everyone can contribute their own design form inside ArisanForm.
~~* Send anything file format~~  
~~* Send multiple files~~  
~~* Chapta~~  
  
## LICENSE  
  
Copyright 2020 Rico Arisandy Wijaya  
  
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