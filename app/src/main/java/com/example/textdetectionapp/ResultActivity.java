package com.example.textdetectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Button backButton;
    TextView resultTextView;
    String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        backButton=findViewById(R.id.backbutton);
        resultTextView=findViewById(R.id.resulttext);
        resultText=getIntent().getStringExtra(LCOTextRecognization.RESULT_TEXT);

        resultTextView.setText(resultText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
