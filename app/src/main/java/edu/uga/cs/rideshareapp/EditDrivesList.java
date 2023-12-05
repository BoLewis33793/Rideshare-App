package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditDrivesList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Drive> list;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    EditDrivesAdapter adapter;
    private static final String TAG = "EditDrivesList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drives_list);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        recyclerView = findViewById(R.id.recyclerview4);
        databaseReference = FirebaseDatabase.getInstance().getReference("Drives");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EditDrivesAdapter(this, list);
        recyclerView.setAdapter(adapter);
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(view -> showPopupMenu(view));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Drive drive = dataSnapshot.getValue(Drive.class);

                    if ((drive.getDriver()).equals(user.getUid())) {
                        list.add(drive);
                    } else {
                        // Don't add to list
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_all_rides) {
                // Intent to start Activity that shows all rides
                startActivity(new Intent(this, AllRidesList.class));
                return true;
            } else if (itemId == R.id.action_view_profile) {
                // Intent to start Activity that shows profile
                startActivity(new Intent(this, ProfileMenu.class));
                return true;
            }
            return false;
        });
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
}
