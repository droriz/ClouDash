package com.example.mycloudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddAcount extends Activity {
    EditText key;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);

        ImageButton backButton =(ImageButton)findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.MAIN"));
            };
        });

        ImageButton amazonButton =(ImageButton)findViewById(R.id.amazon);

        amazonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.ADD_AWS_ACCOUNT"));
                finish();

            };
        });

        ImageButton azureButton =(ImageButton)findViewById(R.id.azure);

        azureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.ADD_AZURE_ACCOUNT"));
                finish();

            };
        });

        ImageButton googleButton =(ImageButton)findViewById(R.id.google);

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.ADD_GOOGLE_ACCOUNT"));
                finish();
            };
        });

    }


    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }



}
