package com.example.cis_marketplace.Lucas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.R;
import com.google.common.collect.MapMaker;

public class ListingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView name;
    protected TextView category;
    protected TextView year;
    protected TextView condition;
    protected TextView price;
    MarketAdapter.listingListener listener;

    public ListingsHolder(@NonNull View itemView, MarketAdapter.listingListener listener1) {
        super(itemView);

        name = itemView.findViewById(R.id.itemName);
        category = itemView.findViewById(R.id.itemCategory);
        year = itemView.findViewById(R.id.itemYearLevel);
        condition = itemView.findViewById(R.id.itemCondition);
        price = itemView.findViewById(R.id.itemPrice);

        listener = listener1;

        itemView.setOnClickListener(this);
    }

    public void onClick(View view) {
        listener.listingOnClick(getAdapterPosition());
    }
}
