package edu.uga.cs.rideshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar();

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = findViewById(R.id.textView4);
        Button createRide = findViewById(R.id.button4);
        Button createDrive = findViewById(R.id.button5);
        Button allRides = findViewById(R.id.button6);
        Button allDrives = findViewById(R.id.button7);

        Button editRides = findViewById(R.id.button8);
        Button editDrives = findViewById(R.id.button9);

        Button acceptedRides = findViewById(R.id.button11);

        Button profile = findViewById(R.id.button12);

        createRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MakeRide.class);
                startActivity(intent);
            }
        });

        createDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MakeDrive.class);
                startActivity(intent);
            }
        });

        allRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AllRidesList.class);
                startActivity(intent);
            }
        });

        allDrives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AllDrivesList.class);
                startActivity(intent);
            }
        });

        editRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, EditRidesList.class);
                startActivity(intent);
            }
        });

        editDrives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, EditDrivesList.class);
                startActivity(intent);
            }
        });

        acceptedRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, AcceptedRidesList.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ProfileMenu.class);
                startActivity(intent);
            }
        });
    }
}