package com.example.greety;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

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
            Toast.makeText(mContext, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    private void shareInstagram(Uri uri) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {
            intent.setComponent(new ComponentName("com.instagram.android", "com.instagram.android.activity.UrlHandlerActivity"));
            Intent mIntentShare = new Intent(Intent.ACTION_SEND);
            String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
            if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
                mIntentShare.setType("text*//*");
            } else {
                mIntentShare.setType(mStrMimeType);
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, uri);
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            mIntentShare.setPackage("com.instagram.android");
            startActivity(mIntentShare);
        }
        else  {

            Toast.makeText(mContext, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_from_editor);
        imageView=findViewById(R.id.imageView);
        imageButton=findViewById(R.id.imageButton11);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = getIntent();
        final String image_path= intent.getStringExtra("imagePath");
        final Uri fileUri = Uri.parse(image_path);
        imageView.setImageURI(fileUri);




        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              shareInstagram(fileUri);
        }


        });
    }
}
