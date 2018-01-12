package com.pbtec.app.barbeariaclub.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pbtec.app.barbeariaclub.MainActivity;
import com.pbtec.app.barbeariaclub.R;

public class Splash_Screen extends AppCompatActivity {
    private final int TIME_MUDAR_ACITITY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent it = new Intent(Splash_Screen.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        }, TIME_MUDAR_ACITITY);
    }

}
