package com.proyectodam.javi.proyectodam;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AfterTransferActivity extends AppCompatActivity {

    Button returnToMainButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_transfer);

        returnToMainButton = (Button) findViewById(R.id.returnToMainButton);
        imageView = (ImageView) findViewById(R.id.loadingView);
        imageView.setBackgroundResource(R.drawable.gif_loading);

        AnimationDrawable frameAnimation = (AnimationDrawable) imageView.getBackground();
        frameAnimation.start();

        final Intent intent = new Intent(this, MainActivity.class);
        returnToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
