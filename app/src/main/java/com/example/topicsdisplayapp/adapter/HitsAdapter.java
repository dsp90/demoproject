package com.example.topicsdisplayapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.topicsdisplayapp.R;
import com.example.topicsdisplayapp.databinding.ItemHitBinding;
import com.example.topicsdisplayapp.models.Hits;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.MyViewHolder> {

    private List hits;

    public void setHits(List hits){
        this.hits = hits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHitBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_hit, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HitsAdapter.MyViewHolder holder, int position) {
        Hits hit = (Hits) hits.get(position);
        holder.binding.setHits(hit);
    }

    @Override
    public int getItemCount() {
        return hits != null ? hits.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemHitBinding binding;
        MyViewHolder(ItemHitBinding itemHitBinding) {
            super(itemHitBinding.getRoot());
            this.binding = itemHitBinding;
        }
    }
}
