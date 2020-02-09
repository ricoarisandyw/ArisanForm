package com.github.arisan.helper;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {

  String[] patternList;

  public DateDeserializer(String... pattern) {
    this.patternList = pattern;
  }

  @Override
  public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
    Date result;
    for(String pattern:patternList){
      result = deserializeDate(json,pattern);
      if(result!=null){
        return result;
      }
    }
    Log.d("ArisanForm","Deserializer data not match with any of pattern");
    return null;
  }

  private Date deserializeDate(JsonElement json, String pattern){
    String dateString = json.getAsJsonPrimitive().getAsString();
    if(android.text.TextUtils.isEmpty(dateString)) {
      return null;
    }
    try {
      return new SimpleDateFormat(pattern).parse(dateString);
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }
}