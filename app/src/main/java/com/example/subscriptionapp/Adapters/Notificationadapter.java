package com.example.subscriptionapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriptionapp.Models.Notificationmodel;
import com.example.subscriptionapp.Models.eventmodel;
import com.example.subscriptionapp.R;

import java.util.ArrayList;

public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.MyViewholder> {

    ArrayList<eventmodel> notificationholder = new ArrayList<>();
    Context context;

    public Notificationadapter(ArrayList<eventmodel> notificationholder, Context context) {
        this.notificationholder = notificationholder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_notification, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        eventmodel event = notificationholder.get(position);

        holder.username.setText(event.getName());
        holder.date.setText(event.getDate());
        holder.message.setText(event.getEvent());
        holder.address.setText(event.getAddress());
        holder.number.setText(event.getMobnumber());

    }

    @Override
    public int getItemCount() {
        return notificationholder.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView username, date, message, address, number;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.message);
            address = itemView.findViewById(R.id.address);
            number = itemView.findViewById(R.id.number);
        }
    }

}
