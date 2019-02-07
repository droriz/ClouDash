package com.example.mycloudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddAWSAcount extends Activity {
    EditText key;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_aws_account);

        ImageButton saveAccout=(ImageButton)findViewById(R.id.save);
        key   = (EditText)findViewById(R.id.username_input);
        password   = (EditText)findViewById(R.id.password_input);

        saveAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myKey = key.getText().toString();
                String myPassword = password.getText().toString();
                // TODO after post sucsses do:
                startActivity(new Intent("android.intent.action.MAIN"));
//                finish();
            };
        });

        ImageButton backButton =(ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent("android.intent.action.ADD_ACCOUNT"));
                finish();
            };
        });

    }
    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }
}
