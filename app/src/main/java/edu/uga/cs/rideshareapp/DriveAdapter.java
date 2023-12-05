package edu.uga.cs.rideshareapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DriveAdapter extends RecyclerView.Adapter<DriveAdapter.MyViewHolder> {
    Context context;
    ArrayList<Drive> list;
    private static final String TAG = "MyAdapter";

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

        holder.departure.setText(drive.getPickup_location());
        holder.destination.setText(drive.getDestination());
        holder.date.setText(drive.getDate());
        holder.time.setText(drive.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView destination, departure, date, time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            destination = itemView.findViewById(R.id.textDestination);
            departure = itemView.findViewById(R.id.textDeparture);
            date = itemView.findViewById(R.id.textDate);
            time = itemView.findViewById(R.id.textTime);
        }
    }
}
