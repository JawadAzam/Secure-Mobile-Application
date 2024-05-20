package com.auth0.MASproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

public class LoginActivity extends RegActivity {

    EditText et;
    EditText et2;
    MultiWaveHeader waveFooter,waveHeader;
    public static SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //WaveMotion------------
        waveFooter = findViewById(R.id.wave_footer);
        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.CYAN);
        waveFooter.setCloseColor(Color.GREEN);

        waveHeader = findViewById(R.id.wave_header);
        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.GREEN);
        waveHeader.setCloseColor(Color.CYAN);
    }

    //encryption & decryption----------------------------------------------------------------------------------------
    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    //onLogin----------------------------------------------------------------------------------------------------
    public void onLogin(View view){
        et = findViewById(R.id.editTextTextPersonName);
        et2 = findViewById(R.id.editTextTextPersonName2);

        sharedPreferences=getSharedPreferences(RegDetails,MODE_PRIVATE);

        String loginusernameEn = sharedPreferences.getString(encrypt("UserName"),encrypt("null"));
        String loginpasswordEn = sharedPreferences.getString(encrypt("Password"),encrypt("null"));
        String loginpassword = decrypt(loginpasswordEn);
        String loginusername = decrypt(loginusernameEn);

          if(et.getText().length()==0){
            et.setError("Please Enter Your Username");

        }
          else if(et2.getText().length()==0){
            et2.setError("Please Enter Your Password");

        }
          else if(!et2.getText().toString().equals(loginpassword)){
            Toast.makeText(this,"Password is incorrect ",Toast.LENGTH_SHORT).show();

        } else if (!et.getText().toString().equals(loginusername)){
            Toast.makeText(this,"Username is incorrect",Toast.LENGTH_SHORT).show();
            }

          else{
              Intent intent=getIntent();
              setResult(1, intent);
              finish();
          }
    }


    public void onSignUp(View view){
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }

}