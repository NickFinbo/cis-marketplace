package com.example.cis_marketplace.Fleming;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<Listing> listings;

    int amount;
    private ItemClickListener mClickListener;
    private FirebaseStorage sto;
    private StorageReference ref;


    public Adapter(ArrayList<Listing> listing, int amount) {
        listings = listing;
        this.amount = amount;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_item_row_view, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(listings.get(position).getName());
        holder.itemCategory.setText(listings.get(position).getType());
        holder.itemCondition.setText(listings.get(position).getState());
        //  holder.itemYearLevel.setText(listings.get(position).getYearLevel());
        holder.itemSubject.setText(listings.get(position).getSubject());
        holder.itemPrice.setText(Double.toString(listings.get(position).getPrice()));
        StorageReference photo = ref.child(listings.get(position).getID());
        final long ONE_MEGABYTE = 1024 * 1024 * 5;
        photo.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.itemImage.setImageBitmap(b);
                holder.itemImage.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });


    }


    @Override
    public int getItemCount() {
        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView itemName;
        protected TextView itemPrice;
        protected TextView itemCondition;
        protected TextView itemYearLevel;
        protected TextView itemCategory;
        protected TextView itemSubject;
        protected ImageView itemImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemNameEditText);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemCondition = itemView.findViewById(R.id.itemCondition);
            itemYearLevel = itemView.findViewById(R.id.itemYearLevel);
            itemCategory = itemView.findViewById(R.id.itemCategoryEditText);
            itemSubject = itemView.findViewById(R.id.itemSubject);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);
            sto = FirebaseStorage.getInstance();
            ref = sto.getReference();

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }
//    String getItem(int id) {
//        return Listing.get(id);
//    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
