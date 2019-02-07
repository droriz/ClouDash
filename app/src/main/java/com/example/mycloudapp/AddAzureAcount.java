package com.example.mycloudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddAzureAcount extends Activity {
    EditText clientId;
    EditText tenant;
    EditText subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_azure_account);

        ImageButton saveAccout=(ImageButton)findViewById(R.id.save);
        clientId   = (EditText)findViewById(R.id.clientid_input);
        tenant   = (EditText)findViewById(R.id.tenant_input);
        subscription   = (EditText)findViewById(R.id.subscription_input);

        saveAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myClientId = clientId.getText().toString();
                String myTenant = tenant.getText().toString();
                String mySubscription = subscription.getText().toString();
                // TODO after post sucsses do:
               startActivity(new Intent("android.intent.action.MAIN"));
               finish();
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
