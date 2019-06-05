package com.turkishvein.tekturk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AracActivity extends AppCompatActivity {

    EditText commentText;
    ImageView imageView;
    Bitmap chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac);

        commentText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
    }

        public void upload(View view){

            String comment= commentText.getText().toString();

            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            chosenImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            ParseFile parseFile = new ParseFile("image.png",bytes);

            ParseObject object = new ParseObject("Cars");
            object.put("Image",parseFile);
            object.put("Tanim",comment);
            object.put("Username", ParseUser.getCurrentUser().getUsername());

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e!=null)
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(getApplicationContext(),"Resim yüklendi",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });

    }
        public void chooseImage(View view){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},2);
            else {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 2){
            if(grantResults.length>0 && (grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && (resultCode == RESULT_OK) && (data != null)){

            Uri uri =data.getData();

            try {
                chosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageView.setImageBitmap(chosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
