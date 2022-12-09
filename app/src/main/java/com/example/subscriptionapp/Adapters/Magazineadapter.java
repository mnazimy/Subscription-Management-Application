package com.example.subscriptionapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subscriptionapp.Addeditmagazine;
import com.example.subscriptionapp.Magazinesubscription;
import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

public class Magazineadapter extends RecyclerView.Adapter<Magazineadapter.MyViewholder> implements Filterable {

    ArrayList<Magazinemodel> magazineholder;
    ArrayList<Magazinemodel> magazineholderfull;
    Context context;
    int ifvandor;

    private OnItemLongClickListener mListener;

    @Override
    public Filter getFilter() {
        return magazinefilter;
    }

    private Filter magazinefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Magazinemodel> filteredMagazine = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredMagazine.addAll(magazineholderfull);
            }else {
                String filterpattern = charSequence.toString().toLowerCase();
                for(Magazinemodel item : magazineholderfull){

                    if(item.getName().toLowerCase().contains(filterpattern)){
                        filteredMagazine.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredMagazine;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            magazineholder.clear();
            magazineholder.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemLongClickListener {

        void onLongClick(int position);
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        mListener = listener;
    }

    public Magazineadapter(ArrayList<Magazinemodel> magazineholder, Context context, int ifvendor) {
        this.magazineholder = magazineholder;
        this.context = context;
        this.ifvandor = ifvendor;
        magazineholderfull = new ArrayList<>(magazineholder);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_magazine, parent, false);
        return new MyViewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Magazinemodel data = magazineholder.get(position);

        holder.magazinename.setText(data.getName());
        holder.language.setText(data.getLanguage());
        holder.type.setText(data.getType());
        holder.status.setText(data.getStatus());
        holder.price.setText(data.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifvandor == 1) {
                    Intent intent = new Intent(context, Addeditmagazine.class);
                    Gson gson = new Gson();
                    String myJson = gson.toJson(data);
                    intent.putExtra("Magazine", myJson);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Magazinesubscription.class);
                    Gson gson = new Gson();
                    String myJson = gson.toJson(data);
                    intent.putExtra("Magazine", myJson);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return magazineholder.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView magazinename, language, type, status, price;
        CardView cardView;

        public MyViewholder(@NonNull View itemView,  final Magazineadapter.OnItemLongClickListener listener) {
            super(itemView);

            magazinename = itemView.findViewById(R.id.magazinename);
            language = itemView.findViewById(R.id.language);
            type = itemView.findViewById(R.id.type);
            status = itemView.findViewById(R.id.status);
            price = itemView.findViewById(R.id.price);
            cardView = itemView.findViewById(R.id.cardView);

            if (ifvandor == 1) {
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (listener!=null) {
                            int position = getAdapterPosition();
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
}
