package com.example.topicsdisplayapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.topicsdisplayapp.R;
import com.example.topicsdisplayapp.models.Hits;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.MyViewHolder> {

    private List hits;
    private ToggleSwitch toggleSwitch;

    public HitsAdapter(ToggleSwitch toggleSwitch){
        this.toggleSwitch = toggleSwitch;
    }

    public void setHits(List hits){
        this.hits = hits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HitsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HitsAdapter.MyViewHolder holder, int position) {
        Hits hit = (Hits) hits.get(position);
        holder.onBind(hit);
    }

    @Override
    public int getItemCount() {
        return hits != null ? hits.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvtitle, tvCreatedAt;
        Switch aSwitch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
            aSwitch = itemView.findViewById(R.id.sw_status);
        }

        void onBind(Hits hits){
            tvtitle.setText(hits.getTitle());
            tvCreatedAt.setText(hits.getCreated_at());
            aSwitch.setEnabled(hits.getIsSelected());
            aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                toggleSwitch.onSwitchToggle(hits);
            });
        }
    }

    public interface ToggleSwitch{
        void onSwitchToggle(Hits hit);
    }
}
