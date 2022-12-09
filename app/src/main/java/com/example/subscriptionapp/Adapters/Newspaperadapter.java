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


import com.example.subscriptionapp.Addeditnewspaper;
import com.example.subscriptionapp.Magazinesubscription;
import com.example.subscriptionapp.Models.Magazinemodel;
import com.example.subscriptionapp.Models.Newspapermodel;
import com.example.subscriptionapp.Newspapersubscription;
import com.example.subscriptionapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Newspaperadapter extends RecyclerView.Adapter<Newspaperadapter.MyViewholder> implements Filterable {


    Context context;
    ArrayList<Newspapermodel> newspaperholder;
    ArrayList<Newspapermodel> newspaperholderfull;
    int ifvandor;

    private OnItemLongClickListener mListener;

    @Override
    public Filter getFilter() {
        return newsfilter;
    }

    private Filter newsfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Newspapermodel> filteredNews = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredNews.addAll(newspaperholderfull);
            }else {
                String filterpattern = charSequence.toString().toLowerCase();
                for(Newspapermodel item : newspaperholderfull){

                    if(item.getName().toLowerCase().contains(filterpattern)){
                        filteredNews.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredNews;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            newspaperholder.clear();
            newspaperholder.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemLongClickListener {

        void onLongClick(int position);
    }

    public void setOnLongClickListener(OnItemLongClickListener listener) {
        mListener = listener;
    }

    public Newspaperadapter(Context context, ArrayList<Newspapermodel> newspaperholder, int ifvendor) {
        this.context = context;
        this.newspaperholder = newspaperholder;
        this.ifvandor = ifvendor;
        newspaperholderfull = new ArrayList<>(newspaperholder);
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_newspaper, parent, false);
        return new MyViewholder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Newspapermodel data = newspaperholder.get(position);

        holder.newspapername.setText(data.getName());
        holder.language.setText(data.getLanguage());
        holder.status.setText(data.getStatus());
        holder.price.setText(data.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifvandor == 1){
                    Intent intent = new Intent(context, Addeditnewspaper.class);
                    Gson gson = new Gson();
                    String myJson = gson.toJson(data);
                    intent.putExtra("Newspaper", myJson);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, Newspapersubscription.class);
                    Gson gson = new Gson();
                    String myJson = gson.toJson(data);
                    intent.putExtra("Newspaper", myJson);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return newspaperholder.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView newspapername, language, status, price;
        CardView cardView;

        public MyViewholder(@NonNull View itemView, final OnItemLongClickListener listener) {
            super(itemView);

            newspapername = itemView.findViewById(R.id.newspapername);
            language = itemView.findViewById(R.id.language);
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
