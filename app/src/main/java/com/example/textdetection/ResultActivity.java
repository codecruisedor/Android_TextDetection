package com.example.textdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Button buttonback;
    private TextView textViewResult;
    private ConstraintLayout constraintLayout;
    private String resultText;
    private Boolean switch_state_here;
    private TextView textView_detected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        buttonback = findViewById(R.id.button_back);
        textViewResult = findViewById(R.id.textView_result);
        constraintLayout = findViewById(R.id.result_view);
        textView_detected = findViewById(R.id.textView_detected_static);

        resultText = getIntent().getStringExtra(TextDetector.RESULT_TEXT);
        switch_state_here = getIntent().getBooleanExtra("switch",false);
        if (switch_state_here.equals(true)){

            constraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            textView_detected.setTextColor(Color.parseColor("#000000"));
        }

        textViewResult.setText(resultText);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}