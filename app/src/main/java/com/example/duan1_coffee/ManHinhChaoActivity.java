package com.example.duan1_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        //Man hinh chao
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                //Animation Intent
//                CustomIntent.customType(ManHinhChaoActivity.this,"fadein-to-fadeout");

                //Animation Intent
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                finish();
            }
        }, 2000);

    }
}
