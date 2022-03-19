package com.example.cis_marketplace.Lucas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarketAdapter extends RecyclerView.Adapter<ListingsHolder>{
    ArrayList<String> categoryList;
    ArrayList<String> yearList;
    ArrayList<String> priceList;
    ArrayList<String> itemList;
    ArrayList<String> conditionList;
    private MarketAdapter.listingListener listener;

    public MarketAdapter(ArrayList nameData, ArrayList categoryData, ArrayList yearData, ArrayList conditionData, ArrayList priceData, listingListener listener1) {
        itemList = nameData;
        categoryList = categoryData;
        yearList = yearData;
        conditionList = conditionData;
        priceList = priceData;
        listener = listener1;

    }



    @NonNull
    @Override
    public ListingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item_row_view, parent, false);
        ListingsHolder holder = new ListingsHolder(myView, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingsHolder holder, int position) {
        holder.name.setText(itemList.get(position));
        holder.category.setText(categoryList.get(position));
        holder.year.setText(yearList.get(position));
        holder.condition.setText(conditionList.get(position));
        holder.price.setText(priceList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface listingListener {
        void listingOnClick(int p);

    }


}
