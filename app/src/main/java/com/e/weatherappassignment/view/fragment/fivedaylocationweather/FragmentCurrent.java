package com.e.weatherappassignment.view.fragment.fivedaylocationweather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.weatherappassignment.R;
import com.e.weatherappassignment.model.fivedayweather.CurrentBean;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.FrequentFunction;
import com.e.weatherappassignment.utils.SharedPref;
import com.e.weatherappassignment.view.fragment.BaseFragment;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.adapter.FiveDayWeatherAdapter;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.presenter.CurrentFragmentPresenter;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.presenter.CurrentFragmentPresenterHandler;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.view.CurrentFragmentView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static java.lang.Float.parseFloat;

public class FragmentCurrent extends BaseFragment implements CurrentFragmentView {
    @BindView(R.id.textViewLastUpdate)
    TextView textViewLastUpdate;
    @BindView(R.id.textViewCityAddress)
    TextView textViewCityAddress;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewMaxMin)
    TextView textViewMaxMin;
    @BindView(R.id.textViewWeather)
    TextView textViewWeather;
    @BindView(R.id.textViewCityFeelsLike)
    TextView textViewCityFeelsLike;
    @BindView(R.id.textViewWeatherInfo)
    TextView textViewWeatherInfo;
    @BindView(R.id.textViewPreciption)
    TextView textViewPreciption;
    @BindView(R.id.textViewPressure)
    TextView textViewPressure;
    @BindView(R.id.textViewVisibility)
    TextView textViewVisibility;
    @BindView(R.id.textViewCloudCover)
    TextView textViewCloudCover;
    @BindView(R.id.textViewUvIndex)
    TextView textViewUvIndex;
    @BindView(R.id.textViewHumidity)
    TextView textViewHumidity;
    @BindView(R.id.textViewSunrise)
    TextView textViewSunrise;
    @BindView(R.id.textViewWind)
    TextView textViewWind;
    @BindView(R.id.textViewSunset)
    TextView textViewSunset;
    @BindView(R.id.recyclerViewFiveDays)
    RecyclerView recyclerViewFiveDays;
    Unbinder unbinder;
    @BindView(R.id.imageViewWeather)
    ImageView imageViewWeather;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.adView)
    AdView adView;
    private CurrentFragmentPresenterHandler mPresenter;
    private String latitude, longitude;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentWeather.
     */
    public static FragmentCurrent newInstance() {
        return new FragmentCurrent();
    }

    public FragmentCurrent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new CurrentFragmentPresenter(this);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest =  new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);


        if (SharedPref.getBoolean(Constants.IS_PREMIUM))
            adView.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFiveDays.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerViewFiveDays.addItemDecoration(dividerItemDecoration);
        recyclerViewFiveDays.setHasFixedSize(true);

        Bundle bundle = getArguments();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");

        mPresenter.getCurrentWeather(latitude,
                longitude,
                Constants.APIXXU_API_KEY);

        mPresenter.getForecastWeather(latitude,longitude,Constants.APIXXU_API_KEY);

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void showProgressBar() {
        showLoadingDialog();
    }

    @Override
    public void hideProgressBar() {
        hideLoadingDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccessfullyGetCurrentWeather(CurrentBean response) {

        // Icon of the cloud.
        String imageurl = "https://www.weatherbit.io/static/img/icons/"+ response.data.get(0).weather.icon+".png";

        Glide.with(getActivity())
                .load(imageurl)
                .into(imageViewWeather);

        textViewCityAddress.setText(response.data.get(0).city_name);


        // SunRise and Sunset.
        if (SharedPref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_24)) {
            textViewSunset.setText("Sunset: " + response.data.get(0).sunset+" UTC");
            textViewSunrise.setText("Sunrise: " + response.data.get(0).sunrise+" UTC");
        } else {
            textViewSunset.setText("Sunset: " + FrequentFunction.get24From12(response.data.get(0).sunset)+" UTC");
            textViewSunrise.setText("Sunrise: " + FrequentFunction.get24From12(response.data.get(0).sunrise)+" UTC");
        }


        // Pressure.
        if (SharedPref.getDataFromPref(Constants.PRESSURE).
                equalsIgnoreCase(Constants.PRESSURE_MIILLIBAR))
            textViewPressure.setText("Pressure: " +
                    (response.data.get(0).pres)+"mb");
        else
            textViewPressure.setText("Pressure: " +
                    (response.data.get(0).pres)+"hPa");


        // Precip.
        if (SharedPref.getDataFromPref(Constants.PRECIPTION).
                equalsIgnoreCase(Constants.PRECIPTION_INCHES))
            textViewPreciption.setText("Precip: " +
                    FrequentFunction.mmToInches(response.data.get(0).precip)+"in");
        else
            textViewPreciption.setText("Precip: " +
                    (response.data.get(0).precip)+"mm");


        //Time and stuff.
        if(SharedPref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_24))
        {
            String datetime =FrequentFunction.convertToCurrentTimeZone(response.data.get(0).ob_time);
            String date = datetime.substring(0,10);
            String time = datetime.substring(11,16);
            if(SharedPref.getDataFromPref(Constants.DATE_FORMAT).equalsIgnoreCase(Constants.DATE_FORMAT_API))
            {
                textViewLastUpdate.setText("Last updated: "+date+" "+time);
            }
            else
            {
                textViewLastUpdate.setText("Last updated: "+FrequentFunction.getDateInSystem(date)+" "+time);
            }

        }
        else
        {
            String datetime =FrequentFunction.convertToCurrentTimeZone(response.data.get(0).ob_time);
            String date = datetime.substring(0,10);
            String time = datetime.substring(11,16);
            if(SharedPref.getDataFromPref(Constants.DATE_FORMAT).equalsIgnoreCase(Constants.DATE_FORMAT_API))
            {
                textViewLastUpdate.setText("Last updated: "+date+" "+FrequentFunction.get24From12(time));
            }
            else
            {
                textViewLastUpdate.setText("Last updated: "+FrequentFunction.getDateInSystem(date)+" "+FrequentFunction.get24From12(time));
            }

        }

        if (SharedPref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_C))
        {
            textViewCityFeelsLike.setText("Feels Like: "+response.data.get(0).app_temp+" °C");
            textViewWeather.setText(response.data.get(0).temp+" °C");
        }
        else {
            textViewCityFeelsLike.setText("Feels Like: "+(FrequentFunction.celsiusToFahrenheit
                    (parseFloat(response.data.get(0).app_temp)))+" °F");
            textViewWeather.setText(FrequentFunction.celsiusToFahrenheit(parseFloat(response.data.get(0).temp))+" °F");
        }

        if (SharedPref.getDataFromPref(Constants.WIND_SPEED).equalsIgnoreCase
                (Constants.WIND_METRES))
            textViewWind.setText("Wind: " + response.data.get(0).wind_spd + " " +
                    Constants.WIND_METRES);
        else
            textViewWind.setText("Wind: " + FrequentFunction.mps_to_kmph(parseFloat(response.data.get(0).wind_spd)) + " "
                    + Constants.WIND_KILLOMETRES);


        textViewWeatherInfo.setText(response.data.get(0).weather.description);
        textViewVisibility.setText("Visibility: " + response.data.get(0).vis+" km");
        textViewHumidity.setText("Humidity: " + response.data.get(0).rh + "%");
        textViewCloudCover.setText("Cloud Cover: " + response.data.get(0).clouds + "%");
        textViewUvIndex.setText("UV Index: " + response.data.get(0).uv);
        
    }

    private void setAdapter(List<WeatherBean.ForecastBean> forecastday) {
        FiveDayWeatherAdapter fiveDayWeatherAdapter = new FiveDayWeatherAdapter(forecastday);
        recyclerViewFiveDays.setAdapter(fiveDayWeatherAdapter);
    }


    @Override
    public void onSuccessfullyGetForecastWeather(WeatherBean response) {
        if(SharedPref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_C))
        {
            textViewMaxMin.setText(response.data.get(0).max_temp+"/"+response.data.get(0).min_temp+" °C");
        }
        else
        {
            textViewMaxMin.setText(FrequentFunction.celsiusToFahrenheit(parseFloat(response.data.get(0).max_temp))+"/"+
                    FrequentFunction.celsiusToFahrenheit(parseFloat(response.data.get(0).min_temp))+" °F");
        }

        setAdapter(response.data.subList(0,6));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Do Fragment menu item stuff here
                mPresenter.getCurrentWeather(latitude, longitude,
                        Constants.APIXXU_API_KEY);

                return true;
            case R.id.action_share:
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
