package edu.uga.cs.rideshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String userEmail = user.getEmail();

        TextView textView = findViewById(R.id.textView4);
        Button createRide = findViewById(R.id.button4);
        Button createDrive = findViewById(R.id.button5);
        Button allRides = findViewById(R.id.button6);
        Button allDrives = findViewById(R.id.button7);

        textView.setText(userEmail);

        createRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MakeRide.class);
                startActivity(intent);
                finish();
            }
        });

        createDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MakeDrive.class);
                startActivity(intent);
                finish();
            }
        });

        allRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AllRidesList.class);
                startActivity(intent);
                finish();
            }
        });

        allDrives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AllDrivesList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}