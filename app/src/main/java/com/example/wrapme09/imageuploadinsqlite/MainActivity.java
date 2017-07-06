package com.example.wrapme09.imageuploadinsqlite;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button button,button2;
    private static final int SELECT_PICTURE = 100;
    byte[] Imagebytes;
    ImageView ImageView;
    private CartDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        ImageView=(ImageView)findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });
    }



    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    //AFTER SELETING THE IMAGE WHAT WIL HAPEN
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                Uri imageUri = data.getData();
                // imageView_FromFile.setImageURI(filePath);
                try {
                    Imagebytes = Utils.getImageBytes(imageUri,this);

                    //ImageView.setImageBitmap(Utils.getImageBitmap(Imagebytes));
                    db.addCartRow("ggg",Imagebytes);
                    ImageView.setImageBitmap(Utils.getImageBitmap(db.retreiveImageFromDB()));
                    //imageView_FromFile.setImageBitmap(Utils.getImageBitmap(Imagebytes));


                } catch (IOException ioe) {

                }
                   /* // Saving to Database...
                    if (saveImageInDB(filePath)) {
                        //showMessage("Image Saved in Database...");
                        imageView_FromFile.setImageURI(filePath);
                        //OR
                       *//* Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        imageView_FromFile.setImageBitmap(bitmap);*//*
                    }*/


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                           /* if (loadImageFromDB()) {
                                //showMessage("Image Loaded from Database...");
                            }*/
                    }
                }, 3000);
            }

        }
    }
}
