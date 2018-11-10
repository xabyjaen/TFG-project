package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    TextView errorTextView;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        String errorMsg = getIntent().getStringExtra("Error");

        errorTextView = (TextView) findViewById(R.id.message_error);
        returnButton = (Button) findViewById(R.id.button_return_to_main);

        if (errorMsg != null){
            errorTextView.setText(errorMsg);
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
