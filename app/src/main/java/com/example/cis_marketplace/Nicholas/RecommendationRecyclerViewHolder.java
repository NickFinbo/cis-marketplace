package com.example.cis_marketplace.Nicholas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.R;

public class RecommendationRecyclerViewHolder extends RecyclerView.ViewHolder
{
    protected TextView nameText;
    protected TextView priceText;

    public RecommendationRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        nameText = itemView.findViewById(R.id.itemName);
        priceText = itemView.findViewById(R.id.itemCategory);
    }
}
