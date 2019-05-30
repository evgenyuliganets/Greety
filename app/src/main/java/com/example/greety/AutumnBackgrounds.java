package com.example.greety;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class AutumnBackgrounds extends AppCompatActivity {
    ImageButton imageButton12;
    ImageButton imageButton13;
    ImageButton imageButton14;
    ImageButton imageButton15;
    ImageButton imageButton16;
    String ImagePath;

    public static final String OUTPUT_PHOTO_DIRECTORY = "ds_photo_editor_sample";

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("path.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Intent result = new Intent(this, ResultFromEditor.class);
        final Intent result1 = new Intent(this, MainActivity.class);
        if (resultCode == RESULT_OK) {

            if (requestCode == 200) {
                Uri outputUri = data.getData();
                assert outputUri != null;
                result.putExtra("imagePath", outputUri.toString());
                ImagePath = outputUri.toString();
                writeToFile(ImagePath, this);
                result1.putExtra("imagePath", outputUri.toString());
                startActivity(result1);
                startActivity(result);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autumn_backgrounds);
        imageButton12 = findViewById(R.id.imageButton22);
        imageButton13 = findViewById(R.id.imageButton23);
        imageButton14 = findViewById(R.id.imageButton24);
        imageButton15 = findViewById(R.id.imageButton25);
        imageButton16 = findViewById(R.id.imageButton26);

        final Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
        String string = getString(R.string.api_key);
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, OUTPUT_PHOTO_DIRECTORY);
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY, string);
        imageButton12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/autumn1");
                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
        imageButton13.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/autumn2");
                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
        imageButton14.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/autumn3");
                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
        imageButton15.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/autumn4");
                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
        imageButton16.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Uri uri = Uri.parse("android.resource://com.example.greety/drawable/autumn5");
                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
    }
}
