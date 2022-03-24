package com.example.cis_marketplace.Nicholas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.Lucas.Listing;
import com.example.cis_marketplace.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RecommendationRecyclerAdapter extends RecyclerView.Adapter<RecommendationRecyclerViewHolder>{

    ArrayList<Listing> recommendationList;

    public RecommendationRecyclerAdapter(ArrayList<Listing> data){
        recommendationList = data;
    }

    private FirebaseStorage storage;
    private StorageReference storageRef;

    @NonNull
    @Override
    public RecommendationRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item_row_view_vertical,parent,false);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        RecommendationRecyclerViewHolder holder = new RecommendationRecyclerViewHolder(myView);

        return holder;
    }

    // photo then space
    @Override
    public void onBindViewHolder(@NonNull RecommendationRecyclerViewHolder holder, int position) {
        StorageReference photoRef = storageRef.child(recommendationList.get(position).getID());
        final long ONE_MEGABYTE = 1024 * 1024;
        photoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(bmp);
            }
        });
        holder.nameText.setText(recommendationList.get(position).getName());
        holder.priceText.setText("$"+recommendationList.get(position).getPrice());

    }
    private void showImage(){

    }

    @Override
    public int getItemCount() {
        //vehicleList.size()
        return recommendationList.size();
    }
}
