package com.example.subscriptionapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriptionapp.Models.Newspapermodel;
import com.example.subscriptionapp.Models.Subscribermodel;
import com.example.subscriptionapp.Models.Usermodel;
import com.example.subscriptionapp.R;
import com.example.subscriptionapp.Viewsubscriptions;

import java.util.ArrayList;

public class Subscriberadapter extends RecyclerView.Adapter<Subscriberadapter.MyViewholder> implements Filterable {

    Context context;
    ArrayList<Usermodel> subscriberholder;
    ArrayList<Usermodel> subscriberholderfull;

    public Subscriberadapter(Context context, ArrayList<Usermodel> subscriberholder) {
        this.context = context;
        this.subscriberholder = subscriberholder;
        subscriberholderfull = new ArrayList<>(subscriberholder);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_subscriber, parent, false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Usermodel data = subscriberholder.get(position);

        holder.name.setText(data.getName());
        holder.pincode.setText(data.getPincode());
        holder.area.setText(data.getTown()+" "+data.getCity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Viewsubscriptions.class);
                intent.putExtra("Userid", data.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subscriberholder.size();
    }

    @Override
    public Filter getFilter() {
        return subscriberfilter;
    }

    private Filter subscriberfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Usermodel> filteredSubscribers = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredSubscribers.addAll(subscriberholderfull);
            }else {
                String filterpattern = charSequence.toString().toLowerCase();
                for(Usermodel item : subscriberholderfull){

                    if(item.getName().toLowerCase().contains(filterpattern)){
                        filteredSubscribers.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredSubscribers;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            subscriberholder.clear();
            subscriberholder.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView name, pincode, area;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            pincode = itemView.findViewById(R.id.pincode);
            area = itemView.findViewById(R.id.area);
        }
    }
}
