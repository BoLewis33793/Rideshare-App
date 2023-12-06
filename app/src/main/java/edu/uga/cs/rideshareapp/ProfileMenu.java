package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileMenu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView email, firstName, lastName, points;

        Button logoutButton = findViewById(R.id.button13);

        email = findViewById(R.id.emailTextView);
        firstName = findViewById(R.id.firstNameTextView);
        lastName = findViewById(R.id.lastNameTextView);
        points = findViewById(R.id.pointsTextView);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        String userEmail = firebaseUser.getEmail();
        String userId = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            Log.d("firebase", "Received Ride object: " + user.toString());

                            // Now you have the Ride object, you can use it as needed
                            // For example, update your UI with the data from the Ride object
                            email.setText(userEmail);
                            firstName.setText(user.getFirst_name());
                            lastName.setText(user.getLast_name());
                            points.setText(String.valueOf(user.getPoints()));
                        } else {
                            Log.e("firebase", "Failed to convert dataSnapshot to Ride object");
                        }
                    } else {
                        Log.d("firebase", "Data does not exist at the specified location");
                    }
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ProfileMenu.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}