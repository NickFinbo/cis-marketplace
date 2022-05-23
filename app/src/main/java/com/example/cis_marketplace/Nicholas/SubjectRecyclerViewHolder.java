package com.example.cis_marketplace.Nicholas;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.R;

public class SubjectRecyclerViewHolder extends RecyclerView.ViewHolder {
    protected TextView classText;

    public SubjectRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        classText = itemView.findViewById(R.id.itemNameEditText);
    }
}
