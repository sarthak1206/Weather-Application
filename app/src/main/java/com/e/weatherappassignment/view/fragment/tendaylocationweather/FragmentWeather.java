package com.e.weatherappassignment.view.fragment.tendaylocationweather;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.e.weatherappassignment.R;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.view.fragment.BaseFragment;
import com.e.weatherappassignment.view.fragment.tendaylocationweather.adapter.TenDayWeatherAdapter;
import com.e.weatherappassignment.view.fragment.tendaylocationweather.presenter.WeatherFragmentPresenter;
import com.e.weatherappassignment.view.fragment.tendaylocationweather.presenter.WeatherFragmentPresenterHandler;
import com.e.weatherappassignment.view.fragment.tendaylocationweather.view.WeatherFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentWeather extends BaseFragment implements WeatherFragmentView {

    @BindView(R.id.recyclerViewTenDays)
    RecyclerView recyclerViewTenDays;
    Unbinder unbinder;

    private WeatherFragmentPresenterHandler mPresenter;
    private String latitude, longitude;


    public static FragmentWeather newInstance() {
        return new FragmentWeather();
    }

    public FragmentWeather() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = new WeatherFragmentPresenter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTenDays.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                linearLayoutManager.getOrientation());
        recyclerViewTenDays.addItemDecoration(dividerItemDecoration);
        recyclerViewTenDays.setHasFixedSize(true);

        Bundle bundle = getArguments();
        latitude = bundle.getString("latitude");
        longitude = bundle.getString("longitude");

        mPresenter.getForecastWeather(latitude,
                longitude,
                Constants.APIXXU_API_KEY);

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                // Do Fragment menu item stuff here
                mPresenter.getForecastWeather(latitude, longitude,
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
    public void onSuccessfullyGetForecastWeather(WeatherBean response) {
        setAdapter(response.data);
    }

    private void setAdapter(List<WeatherBean.ForecastBean> forecastday) {
        TenDayWeatherAdapter tenDayWeatherAdapter = new TenDayWeatherAdapter(forecastday);
        recyclerViewTenDays.setAdapter(tenDayWeatherAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
