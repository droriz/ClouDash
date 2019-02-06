package com.example.mycloudapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    Button awsButton;
    Button azureButton;
    Button googleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awsButton = (Button) findViewById(R.id.aws_acounnt);
        azureButton = (Button) findViewById(R.id.azure_account);
        googleButton = (Button) findViewById(R.id.google_account);
        awsButton.setVisibility(View.GONE);
        azureButton.setVisibility(View.GONE);
        googleButton.setVisibility(View.GONE);
        ImageButton addAccout=(ImageButton)findViewById(R.id.add_account);
        String url = "http://ec2-54-229-237-186.eu-west-1.compute.amazonaws.com:8080/data";
        mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson(); // Or use new GsonBuilder().create();
                        Data data = gson.fromJson(response.toString(), Data.class);
                        if(data.isContainProvider("AWS")) {
                            awsButton.setVisibility(View.VISIBLE);
                            awsButton.setText(data.getBalanceInformation("AWS"));
                            

                        }
                        if(data.isContainProvider("AZURE")) {
                            azureButton.setVisibility(View.VISIBLE);
                            azureButton.setText(data.getBalanceInformation("AZURE"));
                        }
                        if(data.isContainProvider("GOOGLE")) {
                            googleButton.setVisibility(View.VISIBLE);
                            googleButton.setText(data.getBalanceInformation("GOOGLE"));
                        }
                        makeToast(data.totalUsage);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(req);
        addAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent("android.intent.action.ADD_ACCOUNT"));
            };
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }

}
