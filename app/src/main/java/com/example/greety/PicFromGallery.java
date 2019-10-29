package com.example.greety;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

public class PicFromGallery extends AppCompatActivity {
    ImageButton imageButton32;
    String ImagePath;

    public static final String OUTPUT_PHOTO_DIRECTORY = "Greetys";

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("path.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final Intent result = new Intent(this, ResultFromEditor.class);
        final Intent result1 = new Intent(this, MainActivity.class);
        if (resultCode==RESULT_OK){

            if (requestCode == 200) {
                Uri outputUri = data.getData();
                assert outputUri != null;
                result.putExtra("imagePath", outputUri.toString());
                ImagePath=outputUri.toString();
                writeToFile(ImagePath, this);
                result1.putExtra("imagePath",outputUri.toString());
                startActivity(result1);
                startActivity(result);
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_from_gallery);
        imageButton32 = findViewById(R.id.imageButton32);
        final Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
        String string = getString (R.string.api_key);
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, OUTPUT_PHOTO_DIRECTORY);
        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY,string);
        Intent intent = getIntent();

        final Uri uri = intent.getParcelableExtra("imagePath");
        imageButton32.setImageURI(uri);
        imageButton32.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                dsPhotoEditorIntent.setData(uri);
                startActivityForResult(dsPhotoEditorIntent, 200);

            }
        });
    }
}
