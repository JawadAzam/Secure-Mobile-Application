package com.auth0.MASproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.scwang.wave.MultiWaveHeader;

public class UpdateActivity extends AppCompatActivity {

    EditText ed;
    MultiWaveHeader waveFooter,waveHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ed = findViewById(R.id.editText);
        Intent intent = getIntent();
        String theTask = intent.getStringExtra("theTask");
        ed.setText(theTask);

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


    public void onUpdate(View view) {
        EditText ed=findViewById(R.id.editText);
        Intent intent=getIntent();
        String newTask=ed.getText().toString();
        intent.putExtra("UpdateTask",newTask);
        setResult(1, intent);
        finish();
    }


    public void onCancel(View view) {
        Intent intent=getIntent();
        setResult(3, intent);
        finish();
    }


    public void onDelete(View view) {
        EditText ed=findViewById(R.id.editText);
        Intent intent=getIntent();
        String newTask=ed.getText().toString();
        intent.putExtra("DeletedTask",newTask);
        setResult(2, intent);
        finish();

    }



}