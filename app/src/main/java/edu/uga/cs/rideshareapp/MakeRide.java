package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeRide extends AppCompatActivity {

    private static final String TAG = "MakeRideActivity";
    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_ride);

        EditText dateView, timeView, pickup_locationView, destinationView;

        dateView = findViewById(R.id.editTextDate);
        timeView = findViewById(R.id.editTextTime);
        pickup_locationView = findViewById(R.id.editTextText2);
        destinationView = findViewById(R.id.editTextText);

        Button create = findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                String userId = user.getUid();
                String pickup_location,destination,rider,date,time;

                rider = userId;

                pickup_location = pickup_locationView.getText().toString();
                destination = destinationView.getText().toString();
                date = dateView.getText().toString();
                time = timeView.getText().toString();

                int points = 10;

                Ride newRide = new Ride(pickup_location,destination,rider,date,time, points);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Rides");
                reference.child(userId).setValue(newRide).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"Successfully added ride to database.");
                    }
                });
            }
        });
    }
}