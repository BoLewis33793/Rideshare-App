package edu.uga.cs.rideshareapp;

import android.content.Context;
import android.content.Intent;
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

public class DriveAdapter extends RecyclerView.Adapter<DriveAdapter.MyViewHolder> {
    Context context;
    ArrayList<Drive> list;
    private static final String TAG = "MyAdapter";
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;

    public DriveAdapter(Context context, ArrayList<Drive> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DriveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drive_card, parent, false);
        return new DriveAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriveAdapter.MyViewHolder holder, int position) {
        Drive drive = list.get(position);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        holder.departure.setText(drive.getPickup_location());
        holder.destination.setText(drive.getDestination());
        holder.date.setText(drive.getDate());
        holder.time.setText(drive.getTime());

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((drive.getDriver()).equals(user.getUid())) {
                    Log.w(TAG, "User can not accept their own drive");
                    Toast.makeText(context, "Cannot accept your own drive!", Toast.LENGTH_SHORT).show();

                } else {
                    drive.setAccepted(true);
                    drive.setAcceptedBy(user.getUid());
                    Log.d(TAG, "Rider: " + drive.getAcceptedBy());
                    Log.d(TAG, "Accept State: " + drive.isAccepted());

                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Drives");
                    reference.child(drive.getKey()).setValue(drive).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d(TAG, "Successfully updated except state of drive.");
                            Intent intent = new Intent(context, AllDrivesList.class);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
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
