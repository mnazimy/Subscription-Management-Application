package com.example.subscriptionapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriptionapp.Models.Subscriptionmodel;
import com.example.subscriptionapp.Models.Usermodel;
import com.example.subscriptionapp.R;

import java.util.ArrayList;

public class Subscriptionadapter extends RecyclerView.Adapter<Subscriptionadapter.MyViewholder> implements Filterable {

    Context context;
    ArrayList<Subscriptionmodel> subscriptionholder;
    ArrayList<Subscriptionmodel> subscriptionholderfull;

    private OnItemLongClickListener mListener;

    public Subscriptionadapter(Context context, ArrayList<Subscriptionmodel> subscriptionholder) {
        this.context = context;
        this.subscriptionholder = subscriptionholder;
        subscriptionholderfull = new ArrayList<>(subscriptionholder);
    }

    public interface OnItemLongClickListener {

        void onLongClick(int position);
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_subscription, parent, false);
        return new MyViewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Subscriptionmodel data = subscriptionholder.get(position);

        holder.name.setText(data.getName());
        holder.type.setText(data.getType());
        holder.price.setText(data.getPrice());
        holder.date.setText(data.getDate());
        holder.status.setText(data.getStatus());

    }

    @Override
    public int getItemCount() {
        return subscriptionholder.size();
    }

    @Override
    public Filter getFilter() {
        return subscriptionfilter;
    }

    private Filter subscriptionfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Subscriptionmodel> filteredSubscription = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredSubscription.addAll(subscriptionholderfull);
            }else {
                String filterpattern = charSequence.toString().toLowerCase();
                for(Subscriptionmodel item : subscriptionholderfull){

                    if(item.getName().toLowerCase().contains(filterpattern)){
                        filteredSubscription.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredSubscription;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            subscriptionholder.clear();
            subscriptionholder.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView name, type, price, date, status;
        CardView cardView;

        public MyViewholder(@NonNull View itemView, final OnItemLongClickListener listener) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            price = itemView.findViewById(R.id.price);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener!=null) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongClick(position);
                        }
                    }
                    return true;
                }
            });

        }
    }
}
