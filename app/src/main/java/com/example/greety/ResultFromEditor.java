package com.example.greety;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class ResultFromEditor extends AppCompatActivity {
    ImageView imageView;
    ImageButton imageButton;
    Context mContext;

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //Error
        }

        return false;
    }


    private void shareInstagram(Uri uri) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {
            Intent mIntentShare = new Intent(Intent.ACTION_SEND);
            String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
            if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
                // if there is no extension or there is no definite mimetype, still try to open the file
                mIntentShare.setType("text*//*");
            } else {
                mIntentShare.setType(mStrMimeType);
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, uri);
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            mIntentShare.setPackage("com.instagram.android");
            startActivity(mIntentShare);
        } else {

            Toast.makeText(mContext, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_from_editor);
        imageView=findViewById(R.id.imageView1);
        imageButton=findViewById(R.id.imageButton11);

        Intent intent = getIntent();
        final String image_path= intent.getStringExtra("imagePath");
        final Uri fileUri = Uri.parse(image_path);
        imageView.setImageURI(fileUri);
        final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/*");



        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              shareInstagram(fileUri);
        }


        });
    }
}
