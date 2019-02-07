package com.example.mycloudapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddAWSAcount extends Activity {
    EditText key;
    EditText password;
    String myKey;
    String myPassword;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_aws_account);
        queue = Volley.newRequestQueue(this);
        ImageButton saveAccout=(ImageButton)findViewById(R.id.save);
        key   = (EditText)findViewById(R.id.username_input);
        password   = (EditText)findViewById(R.id.password_input);

        saveAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myKey = key.getText().toString();
                myPassword = password.getText().toString();
//                String url = "http://httpbin.org/post";
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response) {
//                                // response
//                                Log.d("Response", response);
//                            }
//                        },
//                        new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//
//                            }
//                        }
//                ) {
//                    @Override
//                    protected Map<String, String> getParams()
//                    {
//                        Map<String, String>  params = new HashMap<String, String>();
//                        params.put("key", myKey);
//                        params.put("password",myPassword);
//
//                        return params;
//                    }
//                };
//                queue.add(postRequest);
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
