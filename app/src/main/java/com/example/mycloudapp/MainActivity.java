package com.example.mycloudapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    Context ctx=this;
    String getData;
    PieChartView pieChartView;
    List<SliceValue> pieData;
    TextView awsBalance;
    TextView azureBalance;
    TextView googleBalance;
    TextView totalBalance;
    TextView dailyBalance;

    LinearLayout summary;
    RelativeLayout awsSummary;
    RelativeLayout azureSummary;
    RelativeLayout googleSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awsBalance = (TextView) this.findViewById(R.id.awsbalance);
        azureBalance = (TextView) this.findViewById(R.id.azurebalance);
        googleBalance = (TextView) this.findViewById(R.id.googlebalance);
        totalBalance = (TextView) this.findViewById(R.id.totalbalancenum);
        dailyBalance = (TextView) this.findViewById(R.id.dailybalancenum);

        summary = (LinearLayout)  this.findViewById(R.id.summary);
        awsSummary = (RelativeLayout)  this.findViewById(R.id.awssummary);
        azureSummary = (RelativeLayout)  this.findViewById(R.id.azuresummary);
        googleSummary = (RelativeLayout)  this.findViewById(R.id.googlesummary);

        pieChartView = findViewById(R.id.chart);
        pieData = new ArrayList<>();

        ImageButton addAccout=(ImageButton)findViewById(R.id.add_account);

//       fetchData();
        String temp ="{\"totalUsage\":2000,\"cloudUsageList\":[{\"accountType\":\"aws\",\"accountsBalance\":1000,\"regions\":[{\"name\":\"Irland\",\"balance\":600.0,\"capacitySize\":10.0,\"capacityBalance\":200.0,\"networkBalance\":100.0,\"computeBalance\":300.0},{\"name\":\"Israel\",\"balance\":400.0,\"capacitySize\":5.0,\"capacityBalance\":100.0,\"networkBalance\":100.0,\"computeBalance\":50.0}]}," +
                "{\"accountType\":\"azure\",\"accountsBalance\":600,\"regions\":[{\"name\":\"Irland\",\"balance\":300.0,\"capacitySize\":10.0,\"capacityBalance\":200.0,\"networkBalance\":100.0,\"computeBalance\":200.0},{\"name\":\"Israel\",\"balance\":150.0,\"capacitySize\":5.0,\"capacityBalance\":100.0,\"networkBalance\":100.0,\"computeBalance\":50.0}]}," +
                "{\"accountType\":\"google\",\"accountsBalance\":400,\"regions\":[{\"name\":\"Irland\",\"balance\":100.0,\"capacitySize\":10.0,\"capacityBalance\":140.0,\"networkBalance\":100.0,\"computeBalance\":200.0},{\"name\":\"Israel\",\"balance\":150.0,\"capacitySize\":5.0,\"capacityBalance\":200.0,\"networkBalance\":100.0,\"computeBalance\":50.0}]}]}";
        buildMainDash(temp);

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
    public void goToInfo(String provider) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("ALL_DATA", getData);
        edit.apply();
        Intent newIntent = new Intent("android.intent.action.ACCOUNT_INFO");
        newIntent.putExtra("provider",provider);
        startActivity(newIntent);
    }

    public void fetchData() {
        String url = "http://ec2-54-76-167-137.eu-west-1.compute.amazonaws.com:8080/data";
        mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        buildMainDash(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(req);
    }

    public void buildMainDash(String incomeData){
        if (incomeData.equals("")){
            return;
        }

        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        Data data = gson.fromJson(incomeData, Data.class);
        getData = incomeData;

        pieChartView.setVisibility(View.VISIBLE);
        summary.setVisibility(View.VISIBLE);;

        if(data.isContainProvider("AWS")) {
            final CloudProviderData awsProvideData = data.getProviderDataByType("AWS");
            pieData.add(new SliceValue(Integer.parseInt(awsProvideData.accountsBalance), Color.rgb(116, 119, 216)));
            awsBalance.setText(awsProvideData.accountsBalance);
            awsSummary.setVisibility(View.VISIBLE);

            Button awsMainDash=(Button)findViewById(R.id.aws);
            awsMainDash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.AWS_MAIN_DASH");
                        intent.putExtra("aws_data",awsProvideData);
                        startActivity(intent);
                        //finish();
                    };
                });

        }

        if(data.isContainProvider("AZURE")) {
            final CloudProviderData azureProvideData = data.getProviderDataByType("AZURE");
            pieData.add(new SliceValue(Integer.parseInt(azureProvideData.accountsBalance), Color.rgb(27, 200, 137)));
            azureBalance.setText(azureProvideData.accountsBalance);
            azureSummary.setVisibility(View.VISIBLE);

            Button azureMainDash=(Button)findViewById(R.id.azure);
            azureMainDash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.AZURE_MAIN_DASH");
                    intent.putExtra("azure_data",azureProvideData);
                    startActivity(intent);
                    //finish();
                };
            });
        }

        if(data.isContainProvider("GOOGLE")) {
            final CloudProviderData googleProvideData = data.getProviderDataByType("GOOGLE");
            pieData.add(new SliceValue(Integer.parseInt(googleProvideData.accountsBalance), Color.rgb( 239, 84, 84)));
            googleBalance.setText(googleProvideData.accountsBalance);
            googleSummary.setVisibility(View.VISIBLE);

            Button googleMainDash=(Button)findViewById(R.id.google);
            googleMainDash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.intent.action.GOOGLE_MAIN_DASH");
                    intent.putExtra("google_data",googleProvideData);
                    startActivity(intent);
                    //finish();
                };
            });
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true).setCenterText1("ClouDash").setCenterText1FontSize(30);
        pieChartView.setPieChartData(pieChartData);

        totalBalance.setText(data.totalUsage);
        dailyBalance.setText("500");
    }
}
