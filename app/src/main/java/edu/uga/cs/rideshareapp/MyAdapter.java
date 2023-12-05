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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ride> list;
    private static final String TAG = "MyAdapter";
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;

    public MyAdapter(Context context, ArrayList<Ride> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ride_card, parent, false);
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

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ride.getRider()).equals(user.getUid())) {
                    Log.w(TAG, "User can not accept their own ride.");
                    Toast.makeText(context, "Cannot accept your own ride!", Toast.LENGTH_SHORT).show();

                } else {
                    ride.setAccepted(true);
                    ride.setAcceptedBy(user.getUid());
                    Log.d(TAG, "Driver: " + ride.getAcceptedBy());
                    Log.d(TAG, "Accept State: " + ride.isAccepted());

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Rides");
                    reference.child(ride.getKey()).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Successfully updated except state of ride.");
                            Intent intent = new Intent(context, AllRidesList.class);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, time;
        Button acceptButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.textDestination);
            departure = itemView.findViewById(R.id.textDeparture);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textTime);
            acceptButton = itemView.findViewById(R.id.acceptButton);
        }
    }
}
