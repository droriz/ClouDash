package com.example.mycloudapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class MainGOOGLEActivity extends Activity {
    PieChartView pieRegionChartView;
    List<SliceValue> pieRegionData;
    PieChartView pieServiceChartView;
    List<SliceValue> pieServiceData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_azure);

        pieRegionChartView = findViewById(R.id.regionchart);
        pieRegionData = new ArrayList<>();

        pieServiceChartView = findViewById(R.id.servicechart);
        pieServiceData = new ArrayList<>();


        Intent intent = getIntent();
        CloudProviderData googleProvideData = (CloudProviderData)intent.getSerializableExtra("google_data");
        Random rnd = new Random();
        for(int i = 0; i<googleProvideData.regions.length; i++){
            SliceValue slice = new SliceValue((int)(googleProvideData.regions[i].balance),Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256)) );
            slice.setLabel(googleProvideData.regions[i].name);
            pieRegionData.add(slice);
        }
        PieChartData pieChartData = new PieChartData(pieRegionData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(16);
        pieChartData.setHasCenterCircle(true).setCenterText1("Regions").setCenterText1FontSize(30);
        pieChartData.setValueLabelBackgroundAuto(false);//now you can manually change label's background color
        pieChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        pieRegionChartView.setPieChartData(pieChartData);


        int totalCapacityBalance = 0;
        int totalNetworkBalanceBalance = 0;
        int totalComputeBalance = 0;


        for(int i = 0; i<googleProvideData.regions.length; i++){
            totalCapacityBalance += googleProvideData.regions[i].capacityBalance;
            totalNetworkBalanceBalance += Float.parseFloat(googleProvideData.regions[i].networkBalance);
            totalComputeBalance += Float.parseFloat(googleProvideData.regions[i].computeBalance);
        }

        pieServiceData.add(new SliceValue(totalCapacityBalance,Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))).setLabel("Capacity"));
        pieServiceData.add(new SliceValue(totalNetworkBalanceBalance,Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))).setLabel("Network"));
        pieServiceData.add(new SliceValue(totalComputeBalance,Color.rgb(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256))).setLabel("Compute"));

        PieChartData pieServiceChartData = new PieChartData(pieServiceData);
        pieServiceChartData.setHasLabels(true).setValueLabelTextSize(16);
        pieServiceChartData.setHasCenterCircle(true).setCenterText1("Services").setCenterText1FontSize(30);
        pieServiceChartData.setValueLabelBackgroundAuto(false);//now you can manually change label's background color
        pieServiceChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        pieServiceChartView.setPieChartData(pieServiceChartData);


        ImageButton backButton =(ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            };
        });

    }

    public void makeToast(String str){
        Toast.makeText(this,str, Toast.LENGTH_LONG).show();
    }
}
