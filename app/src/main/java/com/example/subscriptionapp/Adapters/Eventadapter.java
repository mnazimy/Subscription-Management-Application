package com.example.subscriptionapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriptionapp.Calendareventedit;
import com.example.subscriptionapp.CalenderEventEdit;
import com.example.subscriptionapp.Models.eventmodel;
import com.example.subscriptionapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Eventadapter extends RecyclerView.Adapter<Eventadapter.MyViewholder> {

    Context context;
    ArrayList<eventmodel> eventHolder;

    public Eventadapter(Context context, ArrayList<eventmodel> eventHolder) {
        this.context = context;
        this.eventHolder = eventHolder;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_event, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        eventmodel data = eventHolder.get(position);

        holder.event.setText(data.getEvent());
        Log.i("aaaaaaaaaaaaa",data.getEvent());
        holder.date.setText(data.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, Calendareventedit.class);
                Gson gson = new Gson();
                String myJson = gson.toJson(data);
                intent.putExtra("Eventdata", myJson);
//                Log.i("idddd",data.getEvent());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return eventHolder.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView event, date;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
        }
    }
}
