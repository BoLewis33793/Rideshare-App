package edu.uga.cs.rideshareapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditRidesAdapter extends RecyclerView.Adapter<EditRidesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ride> list;
    private static final String TAG = "EditRidesAdapter";
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;

    public EditRidesAdapter(Context context, ArrayList<Ride> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride = list.get(position);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        holder.departure.setText(ride.getPickup_location());
        holder.destination.setText(ride.getDestination());
        holder.date.setText(ride.getDate());
        holder.time.setText(ride.getTime());

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = ride.getKey();

                Intent intent = new Intent(context, EditRide.class);
                intent.putExtra("key", key);
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Rides");

                reference.child(ride.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Successfully removed ride.");
                    }
                });

                Intent intent = new Intent(context, EditRidesList.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, time;
        Button editButton, deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.textDestination);
            departure = itemView.findViewById(R.id.textDeparture);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textTime);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}

