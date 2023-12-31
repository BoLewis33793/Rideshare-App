package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    String email, password, first_name, last_name, type;
    Boolean switchState;
    String id;
    Button signUpButton;
    Boolean complete = false;
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    FirebaseDatabase db;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);

        signUpButton = findViewById(R.id.submitButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                first_name = editTextFirstName.getText().toString();
                last_name = editTextLastName.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    addUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    // Check the type of exception
                                    Exception exception = task.getException();
                                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                        // Handle invalid email format
                                        Toast.makeText(SignUp.this, "Invalid email format.",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                                        // Handle duplicate email
                                        Toast.makeText(SignUp.this, "Email is already in use. Try another one.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Show a generic authentication failed toast
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
    public void addUser() {
        id = mAuth.getCurrentUser().getUid();
        String userType = "customer";

        User newUser = new User(id, userType, first_name, last_name);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Users");
        reference.child(id).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "Successfully added user to database.");
            }
        });
        Intent intent = new Intent(SignUp.this, HomePage.class);
        startActivity(intent);
    }
}