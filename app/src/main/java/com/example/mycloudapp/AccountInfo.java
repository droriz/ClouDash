package com.example.mycloudapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;

public class AccountInfo extends Activity {
    Context ctx=this;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info);
        String provider = getIntent().getExtras().getString("provider");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        String myData = pref.getString("ALL_DATA", null);
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        data = gson.fromJson(myData, Data.class);
        makeToast(provider+", "+myData);

    }

    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }
}
