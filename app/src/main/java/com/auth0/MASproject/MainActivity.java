package com.auth0.MASproject;

import androidx.annotation.Nullable;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.scwang.wave.MultiWaveHeader;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class MainActivity extends LoginActivity implements MenuItem.OnMenuItemClickListener {

    ArrayAdapter<String> adapter;
    static ArrayList<String> list = new ArrayList<>();
    ListView lv;
    protected Menu menu;
    private static SharedPreferences sharedPreferences;
    private static final String sharedPreFileName = "UserListItems.txt";

    MultiWaveHeader waveFooter, waveHeader;

    com.google.android.material.floatingactionbutton.FloatingActionButton Addbtn;
    Button Savebtn;


    //onCreate-------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dialog Dialog = new Dialog();
        Dialog.show(getSupportFragmentManager(),"Dialog");


        lv = findViewById(R.id.lv);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                String theTask = list.get(i).toString();
                intent.putExtra("theTask", theTask);
                startActivityForResult(intent, 2);
            }
        });

            Addbtn = findViewById(R.id.Floatingbtn);
            Addbtn.setEnabled(false);

            Savebtn = findViewById(R.id.Savebtn);
            Savebtn.setEnabled(false);


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
        waveHeader.setStartColor(Color.YELLOW);
        waveHeader.setCloseColor(Color.MAGENTA);



    }


    //MenuBtn-------------------------------------------------------------------------------------------------
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 3);
        return true;
    }

    public void refreshMenu() {
        MenuItem Option = menu.findItem(R.id.first_action);
        Option.setTitle(R.string.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;
        refreshMenu();
        return true;
    }


    //encryption & decryption----------------------------------------------------------------------------------------
    public static String encrypt(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    //SaveBtn-------------------------------------------------------------------------------------------------
    public void onSave(View view)  {

        sharedPreferences = getSharedPreferences(sharedPreFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int counter = 0;
        for (int i = 0; i < list.size(); i++) {
            counter ++;
            String data = list.get(i);
            editor.putString(encrypt("List Item" + counter), encrypt(data));
            editor.commit();
        }
        Toast.makeText(this, "List Items Have Been Saved!", Toast.LENGTH_SHORT).show();
    }

    //AddTaskBtn-------------------------------------------------------------------------------------------------
    public void AddTask(View view) {

        //Toast.makeText(this,"Please Login First ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, 1);

    }


    //OnActivityResult-------------------------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 1) {
                String newTask = data.getStringExtra("newTask");
                list.add(newTask);
                adapter.notifyDataSetChanged();
            }

            if (resultCode == 2) {
                adapter.notifyDataSetChanged();
            }
        } //End of Request Code 1

        if (requestCode == 2) {

            String UpdateTask = data.getStringExtra("UpdateTask");
            String theTask = data.getStringExtra("theTask");
            if (resultCode == 1) {
                if (theTask != UpdateTask) {
                    list.remove(theTask);
                    list.add(UpdateTask);
                    adapter.notifyDataSetChanged();
                } else adapter.notifyDataSetChanged();
            } //End of Result Code 1


            if (resultCode == 2) {
                String DeletedTask = data.getStringExtra("DeletedTask");
                list.remove(DeletedTask);
                adapter.notifyDataSetChanged();
            } //End of Result Code 2

            if (resultCode == 3) {
                adapter.notifyDataSetChanged();
            } //End of Result Code 3
        } //End of Request Code 2

        if(requestCode == 3){
            if(resultCode == 1){
                sharedPreferences = getSharedPreferences(sharedPreFileName,MODE_PRIVATE);
                int S = sharedPreferences.getAll().size();
                list.clear();
                int counter = 0;
                for (int i = 0; i < S; i++) {
                    counter ++;
                    String loadedDataEn = sharedPreferences.getString(encrypt("List Item" + counter), encrypt("No Data Loaded"));
                    String loadedData = decrypt(loadedDataEn);
                    list.add(loadedData);
                    adapter.notifyDataSetChanged();
                }
                Addbtn = findViewById(R.id.Floatingbtn);
                Addbtn.setEnabled(true);

                Savebtn = findViewById(R.id.Savebtn);
                Savebtn.setEnabled(true);

                }//End of Result Code 1
            }//End of Request Code 3
    }//End onActivityResult


    //ExitBtn-------------------------------------------------------------------------------------------------
    public void onExit(View view) {
            finish();
        }

    }

