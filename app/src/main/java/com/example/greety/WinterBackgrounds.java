package com.example.greety;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class WinterBackgrounds<imageUri> extends AppCompatActivity {
    ImageButton imageButton6;
    ImageButton imageButton5;
    ImageButton  imageButton11;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        imageButton11 = findViewById(R.id.imageButton11);
        if (resultCode == RESULT_OK) {

            BitmapFactory.Options bfOptions =new BitmapFactory.Options();
            Uri outputUri = data.getData();

            // handle the result uri as you want, such as display it in an imageView;
            if (outputUri != null) {
                String galleryImatePath = getRealPathFromURI(outputUri);
                try {


                    bfOptions.inDither = false;          //Disable Dithering mode
                    bfOptions.inPurgeable = true;        //Tell to gc that whether it needs free memory, the Bitmap can be cleared
                    bfOptions.inInputShareable = true;   //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
                    bfOptions.inSampleSize = 5;
                    bfOptions.inTempStorage = new byte[32 * 1024];
                    InputStream stream = getContentResolver().openInputStream(outputUri);
                    final Bitmap myImage = BitmapFactory.decodeStream(stream, null, bfOptions);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    assert myImage != null;
                    myImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    imageButton11.setImageBitmap(myImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }



            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter_backgrounds);
        imageButton6 = findViewById(R.id.imageButton6);
        imageButton5 = findViewById(R.id.imageButton5);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Пора покормить кота!", Toast.LENGTH_SHORT);
        toast.show();



        imageButton6.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                Intent dsPhotoEditorIntent = new Intent(WinterBackgrounds.this, DsPhotoEditorActivity.class);

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/wint");
                try {
                    InputStream stream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String string = getString (R.string.api_key);
                dsPhotoEditorIntent.setData(uri);
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "android.resource://com.example.greety/output_photos");
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY,string);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });

    }
}