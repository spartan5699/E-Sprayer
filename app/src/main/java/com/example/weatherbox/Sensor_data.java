package com.example.weatherbox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sensor_data extends AppCompatActivity {

    private Button predictbtn;
    private Button forecastbtn;
    private TextView temp;
    private TextView humid;
    private TextView pressure;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        predictbtn= findViewById(R.id.prediction);
        forecastbtn=findViewById(R.id.forecast);

        temp=(TextView)findViewById(R.id.tempdata);
        humid=(TextView)findViewById(R.id.humidata);
        pressure=(TextView)findViewById(R.id.pressdata);

        reff= FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < 10; i++) {
                    String t = dataSnapshot.child("Temperature").getValue().toString();
                    temp.setText(t);
                    String h = dataSnapshot.child("humidity").getValue().toString();
                    humid.setText(h);
                    String p = dataSnapshot.child("Pressure").getValue().toString();
                    pressure.setText(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        predictbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sensor_data.this,prediction.class));
            }
        });

        forecastbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent linkActivity=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.accuweather.com/en/in/bhopal/204408/weather-forecast/204408"));
                    startActivity(linkActivity);
                }
        });
    }
}

