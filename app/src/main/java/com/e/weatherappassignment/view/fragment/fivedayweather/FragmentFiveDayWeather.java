package com.e.weatherappassignment.view.fragment.fivedayweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.database.Favourite;
import com.e.weatherappassignment.database.FavouriteRepository;
import com.e.weatherappassignment.view.fragment.BaseFragment;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.FragmentCurrent;
import com.e.weatherappassignment.view.fragment.fivedayweather.adapter.FiveDayViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.e.weatherappassignment.MyApp.cityName;
import static com.e.weatherappassignment.MyApp.latitude;
import static com.e.weatherappassignment.MyApp.longitude;

public class FragmentFiveDayWeather extends BaseFragment implements FavouriteRepository.FetchSavedListInterface{

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private FiveDayViewPagerAdapter viewPagerAdapter;
    FavouriteRepository repository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_day_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        repository = new FavouriteRepository(getActivity());
        repository.getTasks(this);

        setupViewPager(viewpager);
        tabLayout.setupWithViewPager(viewpager);


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new FiveDayViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new FragmentCurrent(), cityName,String.valueOf(latitude),
                String.valueOf(longitude));

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void savedPlaces(List<Favourite> list) {
        if (list!=null&&list.size()>0)
            updateViewPager(list);

    }

    private void updateViewPager(List<Favourite> list) {
        for (int i=0;i<list.size();i++) {
            viewPagerAdapter.addFragment(new FragmentCurrent(), list.get(i).getDescription(),
                    list.get(i).getLatitude(),
                    list.get(i).getLongitude());
        }
        viewPagerAdapter.notifyDataSetChanged();

    }
}

