package com.example.textdetection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton cameraButton;
    public TextView textView;
    private final static int REQUEST_CAMERA_CAPTURE = 129;
    private TextRecognizer textRecognizer;
    InputImage inputImage;
    ConstraintLayout layout;
    public Switch theme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.button_openCamera);
        textView = findViewById(R.id.textView);
        layout = findViewById(R.id.main_activity_view);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (takePictureIntent.resolveActivity(getPackageManager()) !=null){
                    startActivityForResult(takePictureIntent,REQUEST_CAMERA_CAPTURE);
                }
            }
        });
        theme = findViewById(R.id.theme_switch);
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theme.isChecked()){
                    textView.setTextColor(Color.parseColor("#000000"));
                    layout.setBackgroundColor(Color.parseColor("#ffffff"));
                    theme.setText("Light");
                    theme.setTextColor(Color.parseColor("#000000"));
                }else{
                    textView.setTextColor(Color.parseColor("#ffffff"));
                    layout.setBackgroundColor(Color.parseColor("#535C68"));
                    theme.setText("Dark");
                    theme.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            recognizeText(bitmap);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void recognizeText(Bitmap bitmap) {

        inputImage = InputImage.fromBitmap(bitmap,0);
        textRecognizer = TextRecognition.getClient();

        textRecognizer.process(inputImage).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                String resultTextFromImage = text.getText();
                if (resultTextFromImage.isEmpty()){
                    Toast.makeText(MainActivity.this,"NO TEXT DETECTED..",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                    intent.putExtra(TextDetector.RESULT_TEXT,resultTextFromImage);
                    Boolean checked = theme.isChecked();
                    intent.putExtra("switch",checked);
                    startActivity(intent);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Failed...",Toast.LENGTH_SHORT).show();
            }
        });

    }
}