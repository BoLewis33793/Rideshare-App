package edu.uga.cs.rideshareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllRidesList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Ride> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    private static final String TAG = "AllRidesListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_rides_list);

        recyclerView = findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference("Rides");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(view -> showPopupMenu(view));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Ride ride = dataSnapshot.getValue(Ride.class);

                    if (ride.isAccepted()) {
                        // Don't add to list
                    } else {
                        list.add(ride);
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