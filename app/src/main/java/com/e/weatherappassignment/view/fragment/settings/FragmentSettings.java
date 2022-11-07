package com.e.weatherappassignment.view.fragment.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.SharedPref;
import com.e.weatherappassignment.view.fragment.BaseFragment;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentSettings extends BaseFragment {

    @BindView(R.id.toggle_temp)
    ToggleSwitch toggleTemp;
    @BindView(R.id.toggle_time)
    ToggleSwitch toggleTime;
    @BindView(R.id.textViewDateFormat)
    TextView textViewDateFormat;
    @BindView(R.id.textViewWindSpeed)
    TextView textViewWindSpeed;
    @BindView(R.id.textViewPressure)
    TextView textViewPressure;
    @BindView(R.id.textViewPreciption)
    TextView textViewPreciption;
    Unbinder unbinder;
    SharedPref sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        sharedPref = new SharedPref(getContext());
        setToggleData();
        setTextData();
        toggleListener();
        return view;
    }


    @OnClick({R.id.textViewDateFormat, R.id.textViewWindSpeed,
            R.id.textViewPressure,R.id.textViewPreciption})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.textViewDateFormat:
                showDateFormatPopup(view);
                break;
            case R.id.textViewWindSpeed:
                showWindSpeedPopup(view);
                break;
            case R.id.textViewPressure:
                showPressurePopup(view);
                break;
            case R.id.textViewPreciption:
                showPreciptionPopup(view);
                break;
        }
    }

    private void toggleListener() {
        toggleTemp.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0)
                    SharedPref.setDataInPref(Constants.TEMPERATURE, Constants.TEMPERATURE_C);
                else
                    SharedPref.setDataInPref(Constants.TEMPERATURE, Constants.TEMPERATURE_F);
            }
        });

        toggleTime.setOnChangeListener(new ToggleSwitch.OnChangeListener() {
            @Override
            public void onToggleSwitchChanged(int i) {
                if (i == 0)
                    SharedPref.setDataInPref(Constants.TIME_FORMAT, Constants.TIME_FORMAT_12);
                else
                    SharedPref.setDataInPref(Constants.TIME_FORMAT, Constants.TIME_FORMAT_24);

            }
        });
    }

    private void setTextData() {
        if (!SharedPref.getDataFromPref(Constants.DATE_FORMAT).isEmpty())
            textViewDateFormat.setText(SharedPref.getDataFromPref(Constants.DATE_FORMAT));

        if (!SharedPref.getDataFromPref(Constants.WIND_SPEED).isEmpty())
            textViewWindSpeed.setText(SharedPref.getDataFromPref(Constants.WIND_SPEED));

        if (!SharedPref.getDataFromPref(Constants.PRESSURE).isEmpty())
            textViewPressure.setText(SharedPref.getDataFromPref(Constants.PRESSURE));

        if (!SharedPref.getDataFromPref(Constants.PRECIPTION).isEmpty())
            textViewPreciption.setText(SharedPref.getDataFromPref(Constants.PRECIPTION));
    }

    private void setToggleData() {

        if (SharedPref.getDataFromPref(Constants.TEMPERATURE).equalsIgnoreCase(Constants.TEMPERATURE_F))
            toggleTemp.setCheckedPosition(1);
        else
            toggleTemp.setCheckedPosition(0);

        if (SharedPref.getDataFromPref(Constants.TIME_FORMAT).equalsIgnoreCase(Constants.TIME_FORMAT_24))
            toggleTime.setCheckedPosition(1);
        else
            toggleTime.setCheckedPosition(0);
    }

    public void showDateFormatPopup(View view) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_system:
                        SharedPref.setDataInPref(Constants.DATE_FORMAT, Constants.DATE_FORMAT_SYSYTEM);
                        textViewDateFormat.setText(Constants.DATE_FORMAT_SYSYTEM);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_api:
                        SharedPref.setDataInPref(Constants.DATE_FORMAT, Constants.DATE_FORMAT_API);
                        textViewDateFormat.setText(Constants.DATE_FORMAT_API);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_date_popup);
        popupMenu.show();
    }

    public void showWindSpeedPopup(View view) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_km:
                        SharedPref.setDataInPref(Constants.WIND_SPEED, Constants.WIND_KILLOMETRES);
                        textViewWindSpeed.setText(Constants.WIND_KILLOMETRES);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_metre:
                        SharedPref.setDataInPref(Constants.WIND_SPEED, Constants.WIND_METRES);
                        textViewWindSpeed.setText(Constants.WIND_METRES);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_wind_popup);
        popupMenu.show();
    }

    public void showPressurePopup(View view) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_hectopascals:
                        SharedPref.setDataInPref(Constants.PRESSURE, Constants.PRESSURE_HECTOPASCALS);
                        textViewPressure.setText(Constants.PRESSURE_HECTOPASCALS);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_mbar:
                        SharedPref.setDataInPref(Constants.PRESSURE,
                                Constants.PRESSURE_MIILLIBAR);
                        textViewPressure.setText(Constants.PRESSURE_MIILLIBAR);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_pressure_popup);
        popupMenu.show();
    }

    public void showPreciptionPopup(View view) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item_mm:
                        SharedPref.setDataInPref(Constants.PRECIPTION,
                                Constants.PRECIPTION_MM);
                        textViewPreciption.setText(Constants.PRECIPTION_MM);
                        popupMenu.dismiss();
                        break;
                    case R.id.item_inches:
                        SharedPref.setDataInPref(Constants.PRECIPTION,
                                Constants.PRECIPTION_INCHES);
                        textViewPreciption.setText(Constants.PRECIPTION_INCHES);
                        popupMenu.dismiss();
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_preciption_popup);
        popupMenu.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
