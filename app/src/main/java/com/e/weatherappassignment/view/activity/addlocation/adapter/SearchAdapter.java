package com.e.weatherappassignment.view.activity.addlocation.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.model.SearchAddress;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    View view;
    Activity activity;
    ArrayList<SearchAddress> address;
    ArrayList<String> placeid;
    AdapterItem adapterItem;

    public SearchAdapter(Activity activity, ArrayList<SearchAddress> address, ArrayList<String> placeid, AdapterItem adapterItem) {
        this.activity=activity;
        this.address=address;
        this.placeid=placeid;
        this.adapterItem=adapterItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvFulladdress.setText(address.get(position).getTitle());
        holder.tvTitle.setText(address.get(position).getFullAddress());
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterItem.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return address.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvFulladdress;
        LinearLayout mainView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            mainView = itemView.findViewById(R.id.main_view);
            tvFulladdress = itemView.findViewById(R.id.tv_full_address);
        }
    }

    public interface AdapterItem{
        void   onItemClick(int position);
    }
}

