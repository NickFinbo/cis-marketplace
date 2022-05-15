package com.example.cis_marketplace.Nicholas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cis_marketplace.R;

import java.util.ArrayList;

public class SubjectRecyclerAdapter extends RecyclerView.Adapter<SubjectRecyclerViewHolder> {

    ArrayList<String> classList;

    public SubjectRecyclerAdapter(ArrayList<String> data) {
        classList = data;
    }

    @NonNull
    @Override
    public SubjectRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_row_view, parent, false);

        SubjectRecyclerViewHolder holder = new SubjectRecyclerViewHolder(myView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRecyclerViewHolder holder, int position) {

        holder.classText.setText(classList.get(position));

    }

    @Override
    public int getItemCount() {
        //vehicleList.size()
        return classList.size();
    }
}
