﻿package com.zzj.customdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zzj.customvoice.CustomRadio;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD
//增

//bendizengjia
//第三行注释11111
=======
//电脑修改
//电脑增加的内容
>>>>>>> a7d93d957f1af927821a858c404241d79be19580
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        CustomRadio radio = (CustomRadio) findViewById(R.id.customRadio);
        if (radio != null) {
            radio.setOnVolumeChangeListener(new CustomRadio.OnVolumeChangeListener() {
                @Override
                public void volumeChange(int level) {
                    Toast.makeText(MainActivity.this,"当前音量"+level,Toast.LENGTH_SHORT).show();
//第四行注释
                }
            });
        }
    }
}
//第二行注释
