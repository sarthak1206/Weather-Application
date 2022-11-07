package com.e.weatherappassignment.view.fragment.mycities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.database.Favourite;

import java.util.List;

public class MyCitiesAdapter extends RecyclerView.Adapter<MyCitiesAdapter.ViewHolder>{

    View view;
    List<Favourite> favouriteList;
    ItemLongPressInterface interfaceLongPress;


    public MyCitiesAdapter(ItemLongPressInterface interfaceLongPress,
                                 List<Favourite> favouriteList) {
        this.favouriteList = favouriteList;
        this.interfaceLongPress = interfaceLongPress;
    }

    public void customNotify( List<Favourite> favouriteList) {
        this.favouriteList = favouriteList;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_my_cities, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvFulladdress.setText(favouriteList.get(position).getTitle());
        holder.tvTitle.setText(favouriteList.get(position).getDescription());
        holder.imageViewPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceLongPress.longPress(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvFulladdress;
        LinearLayout linearLayoutRoot;
        ImageView imageViewPopup;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            linearLayoutRoot = itemView.findViewById(R.id.linearLayoutRoot);
            tvFulladdress = itemView.findViewById(R.id.tv_full_address);
            imageViewPopup = itemView.findViewById(R.id.imageViewPopup);


        }
    }

    public interface ItemLongPressInterface {
        void longPress(View view,int position);
    }
}
