package com.auth0.MASproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {


    public static SharedPreferences sharedPreferences;
    public static final String RegDetails = "RegDetails.txt";

    private static EditText username;
    private static EditText email;
    private static EditText password;
    private static EditText passwordconf;

    MultiWaveHeader waveFooter,waveHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordconf = findViewById(R.id.passwordconf);

        //WaveMotion------------
        waveFooter = findViewById(R.id.wave_footer);
        waveFooter.setVelocity(1);
        waveFooter.setProgress(1);
        waveFooter.isRunning();
        waveFooter.setGradientAngle(45);
        waveFooter.setWaveHeight(40);
        waveFooter.setStartColor(Color.BLACK);
        waveFooter.setCloseColor(Color.RED);

        waveHeader = findViewById(R.id.wave_header);
        waveHeader.setVelocity(1);
        waveHeader.setProgress(1);
        waveHeader.isRunning();
        waveHeader.setGradientAngle(45);
        waveHeader.setWaveHeight(40);
        waveHeader.setStartColor(Color.RED);
        waveHeader.setCloseColor(Color.BLACK);

    }

    public boolean validatePassword(String password, String passwordcon) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digitcase = Pattern.compile("[0-9]");


        if (password.isEmpty()) {
            Toast.makeText(this, "Password can not be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!lowercase.matcher(password).find()) {
            Toast.makeText(this, "Password Must Contain Lowercase!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!uppercase.matcher(password).find()) {
            Toast.makeText(this, "Password Must Contain Uppercase!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!digitcase.matcher(password).find()) {
            Toast.makeText(this, "Password Must Contain Digits!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() <= 8) {
            Toast.makeText(this, "Password Length Must be 8 characters or more!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(passwordcon)) {
            Toast.makeText(this, "Passwords must match!", Toast.LENGTH_SHORT).show();
            return false;
        } else
        return true;
    }

    public boolean validateEmail(String email) {
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            Toast.makeText(this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //encryption----------------------------------------------------------------------------------------
    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }


    public void onRegister(View view) {
        sharedPreferences = getSharedPreferences(RegDetails, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String data = username.getText().toString();
        String data2 = email.getText().toString();
        String data3 = password.getText().toString();
        String data4 = passwordconf.getText().toString();

        if (validatePassword(data3, data4) == true && validateEmail(data2) == true) {
            Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show();
            editor.putString(encrypt("UserName"), encrypt(data));
            editor.putString(encrypt("Email"), encrypt(data2));
            editor.putString(encrypt("Password"), encrypt(data3));
            editor.putString(encrypt("Password Config"), encrypt(data4));
            editor.commit();
            finish();
        }
    }
}
