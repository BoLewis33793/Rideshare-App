package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDrive extends AppCompatActivity {

    private static final String TAG = "EditDrive";
    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    EditRidesAdapter adapter;
    Drive drive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drive);

        EditText dateView, timeView, pickup_locationView, destinationView;

        dateView = findViewById(R.id.editTextDate);
        timeView = findViewById(R.id.editTextTime);
        pickup_locationView = findViewById(R.id.editTextText1);
        destinationView = findViewById(R.id.editTextText2);

        Intent intent = getIntent();

        String key = intent.getStringExtra("key");

        databaseReference = FirebaseDatabase.getInstance().getReference("Drives");
        databaseReference.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        drive = dataSnapshot.getValue(Drive.class);
                        if (drive != null) {
                            Log.d("firebase", "Received Ride object: " + drive.toString());

                            // Now you have the Ride object, you can use it as needed
                            // For example, update your UI with the data from the Ride object
                            dateView.setText(drive.getDate());
                            timeView.setText(drive.getTime());
                            pickup_locationView.setText(drive.getPickup_location());
                            destinationView.setText(drive.getDestination());
                        } else {
                            Log.e("firebase", "Failed to convert dataSnapshot to Ride object");
                        }
                    } else {
                        Log.d("firebase", "Data does not exist at the specified location");
                    }
                }
            }
        });

        Button update = findViewById(R.id.button3);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drive.setDate(dateView.getText().toString());
                drive.setTime(timeView.getText().toString());
                drive.setDestination(destinationView.getText().toString());
                drive.setPickup_location(pickup_locationView.getText().toString());

                databaseReference.child(key).setValue(drive).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Successfully updated drive");
                        }
                        else {
                            Log.d(TAG, "Could not update drive");
                        }
                        Intent intent = new Intent(EditDrive.this, EditDrivesList.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }
}