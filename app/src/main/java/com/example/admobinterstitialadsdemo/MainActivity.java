package com.example.admobinterstitialadsdemo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private InterstitialAd interstitialAd;
    DatabaseReference adsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adsDatabase = FirebaseDatabase.getInstance().getReference().child("AdmobAds");
        adsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            String AppID = dataSnapshot.child("app_id").getValue().toString();
            String InterstitialID = dataSnapshot.child("interstitial_id").getValue().toString();

                MobileAds.initialize(MainActivity.this, AppID);
                AdRequest adRequest = new AdRequest.Builder().build();
                interstitialAd = new InterstitialAd(MainActivity.this);
                interstitialAd.setAdUnitId(InterstitialID);
                interstitialAd.loadAd(adRequest);
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdLoaded() {

                        DisplayInterstitial();
                    }
                });



            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void DisplayInterstitial(){

        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }


}
