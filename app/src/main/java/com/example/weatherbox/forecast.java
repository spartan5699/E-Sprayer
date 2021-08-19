package com.example.weatherbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class forecast extends AppCompatActivity {

    public void open(View view){
        Intent linkActivity=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.accuweather.com/en/in/delhi/202396/weather-forecast/202396"));
        startActivity(linkActivity);
    }
}
