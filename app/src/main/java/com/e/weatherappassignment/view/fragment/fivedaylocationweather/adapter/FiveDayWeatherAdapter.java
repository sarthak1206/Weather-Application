package com.e.weatherappassignment.view.fragment.fivedaylocationweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.weatherappassignment.R;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.utils.FrequentFunction;

import java.util.List;

public class FiveDayWeatherAdapter extends RecyclerView.Adapter<FiveDayWeatherAdapter.ViewHolder> {

    private List<WeatherBean.ForecastBean> mItemsList;
    private Context context;

    public FiveDayWeatherAdapter(List<WeatherBean.ForecastBean> itemsList) {
        this.mItemsList=itemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_listrow, parent, false);
        // Set the view to the ViewHolder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewMaxMin.setText(mItemsList.get(position+1).max_temp +"/"+
                mItemsList.get(position+1).min_temp+ "°C");

        holder.textViewWeather.setText(mItemsList.get(position+1).weather.description);

        holder.textViewWeekDay.setText(FrequentFunction.getWeek(mItemsList.get(position+1).valid_date));

        String imageurl = "https://www.weatherbit.io/static/img/icons/"+ mItemsList.get(position+1).weather.icon+".png";

        Glide.with((context))
                .load(imageurl)
                .into(holder.imageViewWeather);

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    // Create the ViewHolder class to keep references to your views
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWeekDay,textViewWeather,textViewMaxMin;
        public ImageView imageViewWeather;

        /**
         * Constructor
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            textViewWeekDay = (TextView) v.findViewById(R.id.textViewWeekDay);
            textViewWeather = (TextView) v.findViewById(R.id.textViewWeather);
            textViewMaxMin = (TextView) v.findViewById(R.id.textViewMaxMinWeather);
            imageViewWeather =  v.findViewById(R.id.imageViewWeather);
        }


    }
}