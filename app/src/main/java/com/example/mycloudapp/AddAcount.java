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
        ImageButton saveAccout=(ImageButton)findViewById(R.id.save);
        key   = (EditText)findViewById(R.id.username);
        password   = (EditText)findViewById(R.id.password);

        saveAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myKey = key.getText().toString();
                String myPassword = password.getText().toString();
                // TODO after post sucsses do:
               startActivity(new Intent("android.intent.action.MAIN"));
            };
        });


    }
    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }
}
