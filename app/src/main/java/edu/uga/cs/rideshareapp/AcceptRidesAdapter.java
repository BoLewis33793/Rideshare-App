package edu.uga.cs.rideshareapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AcceptRidesAdapter extends RecyclerView.Adapter<AcceptRidesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Ride> list;
    private static final String TAG = "AcceptRidesAdapter";
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;
    User user;

    public AcceptRidesAdapter(Context context, ArrayList<Ride> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.accepted_ride_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride = list.get(position);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        holder.departure.setText(ride.getPickup_location());
        holder.destination.setText(ride.getDestination());
        holder.date.setText(ride.getDate());
        holder.time.setText(ride.getTime());

        user = new User();

        if (ride.isRiderConfirm() == true) {
            holder.riderView.setText("Rider has confirmed");

            String userId = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users");

            reference.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                            } else {
                                Log.e("firebase", "Failed to convert dataSnapshot to Ride object");
                            }
                        } else {
                            Log.d("firebase", "Data does not exist at the specified location");
                        }
                    }
                }
            });

            int userPoints = user.getPoints();
            int ridePoints = ride.getPoints();

            int finalPoints = userPoints - ridePoints;

            user.setPoints(finalPoints);

            reference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully changed user points");
                    } else {
                        Log.d(TAG, "Failed to change user points");
                    }
                }
            });

        } else {
            holder.riderView.setText("Rider has not confirmed");
        }

        if (ride.isDriverConfirm() == true) {
            holder.driverView.setText("Driver has confirmed");
            String userId = firebaseUser.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users");

            reference.child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                            } else {
                                Log.e("firebase", "Failed to convert dataSnapshot to Ride object");
                            }
                        } else {
                            Log.d("firebase", "Data does not exist at the specified location");
                        }
                    }
                }
            });

            int userPoints = user.getPoints();
            int ridePoints = ride.getPoints();

            int finalPoints = userPoints + ridePoints;

            user.setPoints(finalPoints);

            reference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully changed user points");
                    } else {
                        Log.d(TAG, "Failed to change user points");
                    }
                }
            });


        } else {
            holder.driverView.setText("Driver has not confirmed");
        }

        holder.riderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((firebaseUser.getUid()).equals(ride.getRider())) {
                    ride.setRiderConfirm(true);

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Accepted-Rides");

                    reference.child(ride.getKey()).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Successfully updated rider confirm state.");
                                Intent intent = new Intent(context, AcceptedRidesList.class);
                                context.startActivity(intent);
                            }
                            else {
                                Log.d(TAG, "Could not update rider confirm state.");
                            }
                        }
                    });

                } else {
                    Toast.makeText(context, "Cannot confirm because you were not the rider.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.driverConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((firebaseUser.getUid()).equals(ride.getAcceptedBy())) {
                    ride.setDriverConfirm(true);

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Accepted-Rides");

                    reference.child(ride.getKey()).setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Successfully updated driver confirm state.");
                                Intent intent = new Intent(context, AcceptedRidesList.class);
                                context.startActivity(intent);
                            }
                            else {
                                Log.d(TAG, "Could not update driver confirm state.");
                            }
                        }
                    });

                } else {
                    Toast.makeText(context, "Cannot confirm because you were not the driver.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, time, riderView, driverView;
        Button riderConfirm, driverConfirm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.textDestination);
            departure = itemView.findViewById(R.id.textDeparture);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textTime);
            riderConfirm = itemView.findViewById(R.id.button10);
            driverConfirm = itemView.findViewById(R.id.button14);
            riderView = itemView.findViewById(R.id.riderTextView);
            driverView = itemView.findViewById(R.id.driverTextView);
        }
    }
}