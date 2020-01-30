package com.example.topicsdisplayapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.MyViewHolder> {

    List<Hits> hits;

    public HitsAdapter(List hits){
        this.hits = hits;
    }

    @NonNull
    @Override
    public HitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_hit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HitsAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(hits.get(position).getTitle());
        holder.createdAt.setText(hits.get(position).getCreated_at());
        holder.status.setEnabled(hits.get(position).getIsSelected());
    }

    @Override
    public int getItemCount() {
        return hits.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, createdAt;
        Switch status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            createdAt = itemView.findViewById(R.id.tv_created_at);
            status = itemView.findViewById(R.id.sw_status);
        }
    }
}
