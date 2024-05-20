package com.auth0.MASproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.scwang.wave.MultiWaveHeader;

public class AddActivity extends AppCompatActivity {


    MultiWaveHeader waveFooter,waveHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //WaveMotion------------
        waveFooter = findViewById(R.id.wave_footer);
        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.MAGENTA);
        waveFooter.setCloseColor(Color.YELLOW);

        waveHeader = findViewById(R.id.wave_header);
        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.MAGENTA);
        waveHeader.setCloseColor(Color.YELLOW);
    }


    public void onAdd(View view)
    {
        EditText et=findViewById(R.id.editText);
        Intent intent=getIntent();
        String newTask=et.getText().toString();
        intent.putExtra("newTask",newTask);
        setResult(1, intent);
        finish();
    }

    public void onCancel(View view) {
        Intent intent=getIntent();
        setResult(2, intent);
        finish();
    }

}