package com.example.textdetectionapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class MainActivity extends AppCompatActivity {

    Button cameraButton;
    private static final int RESULT_CAMERA_CAPTURE=124;
    private FirebaseVisionImage image;
    private FirebaseVisionTextRecognizer recognizer;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        cameraButton=findViewById(R.id.camerabutton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent takePicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(takePicture.resolveActivity(getPackageManager())!=null)
                {
                    startActivityForResult(takePicture,RESULT_CAMERA_CAPTURE);
                }
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==RESULT_CAMERA_CAPTURE && resultCode==RESULT_OK)
        {
            Bundle extras=data.getExtras();
            Bitmap bitmap=(Bitmap) extras.get("data");

            recognizeText(bitmap);
        }


    }


    private void recognizeText(Bitmap bitmap) {
        try {
            image =FirebaseVisionImage.fromBitmap(bitmap);

            recognizer= FirebaseVision.getInstance()
                    .getOnDeviceTextRecognizer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        recognizer.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        String resultText =firebaseVisionText.getText();
                        if(resultText.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"no text recongnized",Toast.LENGTH_SHORT).show();

                        }else
                        {
                            Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                            intent.putExtra(LCOTextRecognization.RESULT_TEXT,resultText);
                            startActivity(intent);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });




    }
}
