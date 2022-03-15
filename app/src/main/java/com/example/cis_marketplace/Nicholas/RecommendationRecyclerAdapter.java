package com.example.cis_marketplace.Nicholas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.R;

import java.util.ArrayList;

public class RecommendationRecyclerAdapter extends RecyclerView.Adapter<RecommendationRecyclerViewHolder>{

    ArrayList<Listing> recommendationList = new ArrayList<>();

    public RecommendationRecyclerAdapter(ArrayList<Listing> data){
        recommendationList = data;
    }

    @NonNull
    @Override
    public RecommendationRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item_row_view_vertical,parent,false);

        RecommendationRecyclerViewHolder holder = new RecommendationRecyclerViewHolder(myView);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecommendationRecyclerViewHolder holder, int position) {

        holder.nameText.setText(recommendationList.get(position).getName());
        String priceString = recommendationList.get(position).getPrice()+"";
        holder.priceText.setText(priceString);

    }

    @Override
    public int getItemCount() {
        //vehicleList.size()
        return recommendationList.size();
    }
}
