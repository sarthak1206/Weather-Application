package com.e.weatherappassignment.view.fragment.tendaylocationweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.weatherappassignment.R;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.SharedPref;
import com.e.weatherappassignment.view.fragment.tendayforecast.adapter.TenDayForecastViewPagerAdapter;

import org.w3c.dom.Text;

import java.util.List;
import java.util.logging.SocketHandler;

import static com.e.weatherappassignment.utils.FrequentFunction.celsiusToFahrenheit;
import static com.e.weatherappassignment.utils.FrequentFunction.get12HrsByDate;
import static com.e.weatherappassignment.utils.FrequentFunction.get24HrsByDate;
import static com.e.weatherappassignment.utils.FrequentFunction.getDateInSystem;
import static com.e.weatherappassignment.utils.FrequentFunction.getWeek;
import static com.e.weatherappassignment.utils.FrequentFunction.mmToInches;
import static com.e.weatherappassignment.utils.FrequentFunction.mps_to_kmph;
import static java.lang.Float.parseFloat;

public class TenDayWeatherAdapter extends RecyclerView.Adapter<TenDayWeatherAdapter.ViewHolder>{

    private List<WeatherBean.ForecastBean> mItemsList;
    private Context context;

    public TenDayWeatherAdapter(List<WeatherBean.ForecastBean> itemsList) {
        this.mItemsList=itemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        context=parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_listrow, parent, false);
        // Set the view to the ViewHolder


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Date According to the settings.
        if(SharedPref.getDataFromPref(Constants.DATE_FORMAT).equalsIgnoreCase(Constants.DATE_FORMAT_API))
        {
            holder.textDate.setText(mItemsList.get(position+1).valid_date);
        }
        else
        {
            holder.textDate.setText(getDateInSystem(mItemsList.get(position+1).valid_date));
        }

        // Day According to that screen given in assignment.
        holder.textDay.setText(getWeek(mItemsList.get(position+1).valid_date));

        // Pressure.
        if(SharedPref.getDataFromPref(Constants.PRESSURE).equalsIgnoreCase(Constants.PRESSURE_HECTOPASCALS))
        {
            holder.textPressure.setText("Pressure: "+mItemsList.get(position+1).pres+" hPa");
        }
        else
        {
            holder.textPressure.setText("Pressure: "+mItemsList.get(position+1).pres+" mb");
        }

        // Precipitation
        if(SharedPref.getDataFromPref(Constants.PRECIPTION).equalsIgnoreCase(Constants.PRECIPTION_MM))
        {
            holder.textPrecip.setText("Precip.: "+mItemsList.get(position+1).precip+" mm");
        }
        else
        {
            holder.textPrecip.setText("Precip.: "+mmToInches(mItemsList.get(position+1).precip)+" in");
        }

        // WindSpeed with direction.
        if(SharedPref.getDataFromPref(Constants.WIND_SPEED).equalsIgnoreCase(Constants.WIND_KILLOMETRES))
        {
            holder.textWind.setText("Wind: "+mps_to_kmph(parseFloat(mItemsList.get(position+1).wind_spd)
            )+" km/h "+mItemsList.get(position+1).wind_cdir);
        }
        else {
            holder.textWind.setText("Wind: "+mItemsList.get(position+1).wind_spd+" m/s "+mItemsList.get(position+1).wind_cdir);
        }

        // Humidity.
        holder.textHumidity.setText("Humidity: "+mItemsList.get(position+1).rh+" %");

        // UV Index.
        holder.textUVIndex.setText("UV Index: "+mItemsList.get(position+1).uv);

        // Sunrise.
        if(SharedPref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_12))
        {
            holder.textSunrise.setText("SunRise: "+get12HrsByDate(mItemsList.get(position+1).sunrise_ts));
        }
        else {
            holder.textSunrise.setText("SunRise: "+get24HrsByDate(mItemsList.get(position + 1).sunrise_ts));
        }

        // Sunset.
        if(SharedPref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_12))
        {
            holder.textSunSet.setText("Sunset: "+get12HrsByDate(mItemsList.get(position+1).sunset_ts));
        }
        else {
            holder.textSunSet.setText("Sunset: "+get24HrsByDate(mItemsList.get(position + 1).sunset_ts));
        }

        //Min Max Temperature.
        if(SharedPref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_C))
        {
            holder.textMinMaxTemp.setText((int)parseFloat(mItemsList.get(position+1).max_temp)+
                    "/"+(int)parseFloat(mItemsList.get(position+1).min_temp)+"°C");
        }
        else
        {
            holder.textMinMaxTemp.setText(celsiusToFahrenheit(parseFloat(mItemsList.get(position+1).max_temp))+
                    "/"+celsiusToFahrenheit(parseFloat(mItemsList.get(position+1).min_temp))+"°F");
        }

        // Description.
        holder.textDesc.setText(mItemsList.get(position+1).weather.description);

        // Icon of the cloud.
        String imageurl = "https://www.weatherbit.io/static/img/icons/"+mItemsList.get(position+1).weather.icon+".png";

        Glide.with((context))
                .load(imageurl)
                .into(holder.imageViewWeather);

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    // Create the ViewHolder class to keep references to your views
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textDate, textPressure, textPrecip,textHumidity, textUVIndex,textSunrise,textSunSet,textWind;
        public TextView textDesc, textMinMaxTemp, textDay;
        public ImageView imageViewWeather;

        /**
         * Constructor
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            textDate = (TextView) v.findViewById(R.id.date);
            textPressure = (TextView) v.findViewById(R.id.pressureTextView);
            textPrecip = (TextView) v.findViewById(R.id.preciptTextView);
            textHumidity = (TextView) v.findViewById(R.id.humidityTextView);
            textSunrise = (TextView) v.findViewById(R.id.SunriseTextView);
            textSunSet = (TextView) v.findViewById(R.id.SunsetTextView);
            textUVIndex = (TextView) v.findViewById(R.id.UVIndexTextView);
            textWind = (TextView) v.findViewById(R.id.windTextView);
            textMinMaxTemp = (TextView) v.findViewById(R.id.minMaxTempTextView);
            textDesc = (TextView) v.findViewById(R.id.descTextView);
            textDay = (TextView) v.findViewById(R.id.day);
            imageViewWeather = (ImageView) v.findViewById(R.id.weather_desc);

        }


    }

}
