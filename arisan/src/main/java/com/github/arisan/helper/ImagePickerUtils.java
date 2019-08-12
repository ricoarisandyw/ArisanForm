package com.github.arisan.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.github.arisan.annotation.Form;
import com.github.arisan.model.FormModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImagePickerUtils {
    public static final int ARISAN_REQUEST_IMAGE = 1;
    private Context context;
    private Bitmap bitmap;
    private File file;
    private Uri uri;
    private String fieldName;
    private Intent data;
    private PreferenceHelper db;

    private int child_position = -1;
    private boolean child = false;
    private String parent_name;

    //FOR EXTRACT
    public ImagePickerUtils(Context context, Intent data) {
        this.context = context;
        this.data = data;
        db = new PreferenceHelper(context);
        fieldName = db.load("field_name");
        child = Boolean.parseBoolean(db.load("child"));
        if(child) {
            parent_name = db.load("parent");
            child_position = Integer.parseInt(db.load("index"));
        }
        //CLEAR CACHE
        db.save("parent",null);
        db.save("child",null);
        db.save("index",null);
        if (db.load("pick_image").equals(String.valueOf(Form.GALLERY))) {
            extractFromGallery();
        } else {
            extractFromCamera();
        }
    }

    //FOR PICK
    public ImagePickerUtils(Context context, FormModel model) {
        this.context = context;
        db = new PreferenceHelper(context);
        db.save("field_name", model.getName());
    }

    public void pickFromGallery() {
        db.save("pick_image", String.valueOf(Form.GALLERY));
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ((Activity)context).startActivityForResult(photoPickerIntent, ARISAN_REQUEST_IMAGE);
    }

    private void extractFromGallery() {
        try {
            Uri imageUri = data.getData();

            String [] proj={MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(imageUri,proj,null,null,null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Uri realPath = Uri.parse(cursor.getString(column_index));
            InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            int real_width = selectedImage.getWidth();
            int next_height = 720;
            float scale = (float) next_height/selectedImage.getHeight();
            Logger.d("Scale : "+scale);
            Logger.d("New Width : "+real_width*scale);
            selectedImage = Bitmap.createScaledBitmap(selectedImage, (int) (real_width*scale),next_height,false);

            File file = new File(realPath.getPath());
            setUri(realPath);
            setBitmap(selectedImage);
            setFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void pickFromCamera() {
        /*StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());*/

        db.save("pick_image", String.valueOf(Form.IMAGE));

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            ((Activity)context).startActivityForResult(takePictureIntent, ARISAN_REQUEST_IMAGE);
        }
    }

    private void extractFromCamera() {
        Bundle extras = data.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");
        if(bitmap!=null) {
            setBitmap(bitmap);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

            /*SAVE IMAGE TO EXTERNAL STORAGE*/
            String path = android.os.Environment.getExternalStorageDirectory()
                    + File.separator
                    + context.getApplicationContext().getPackageName();

            File folder = new File(path);
            if (!folder.exists()) folder.mkdir();

            OutputStream outFile;
            String file_name = System.currentTimeMillis() + ".jpg";
            File file = new File(path, file_name);
            try {
                outFile = new FileOutputStream(file);
                outFile.write(bitmapdata);
                outFile.flush();
                outFile.close();

                setUri(Uri.fromFile(file));
                setFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri saveBitmap(Bitmap bitmap){
        File file;
        if(bitmap!=null) {
            setBitmap(bitmap);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

            /*SAVE IMAGE TO EXTERNAL STORAGE*/
            String path = android.os.Environment.getExternalStorageDirectory()
                    + File.separator
                    + context.getApplicationContext().getPackageName();

            File folder = new File(path);
            if (!folder.exists()) folder.mkdir();

            OutputStream outFile;
            String file_name = System.currentTimeMillis() + ".jpg";
            file = new File(path, file_name);
            try {
                outFile = new FileOutputStream(file);
                outFile.write(bitmapdata);
                outFile.flush();
                outFile.close();
                return Uri.fromFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }else{
            Toast.makeText(context, "No image captured", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public Bitmap resize(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap,180,180,false);
    }

    //============SETGET============//

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getChild_position() {
        return child_position;
    }

    public void setChild_position(int child_position) {
        this.child_position = child_position;
        db.save("index", String.valueOf(child_position));
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
        db.save("child", String.valueOf(child));
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
        db.save("parent",parent_name);
    }
}
