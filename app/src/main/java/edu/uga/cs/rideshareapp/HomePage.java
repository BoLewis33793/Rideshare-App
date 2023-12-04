package edu.uga.cs.rideshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        String displayName = user.getDisplayName();

        TextView textView2 = findViewById(R.id.textView5);
        TextView textView = findViewById(R.id.textView4);

        textView.setText(userEmail);
        textView2.setText(displayName);
    }
}