package com.e.weatherappassignment.view.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.FrequentFunction;
import com.e.weatherappassignment.utils.SharedPref;
import com.e.weatherappassignment.view.fragment.about.FragmentAbout;
import com.e.weatherappassignment.view.fragment.fivedayweather.FragmentFiveDayWeather;
import com.e.weatherappassignment.view.fragment.hourlyweather.FragmentHourlyWeather;
import com.e.weatherappassignment.view.fragment.moonphase.FragmentMoonPhase;
import com.e.weatherappassignment.view.fragment.mycities.FragmentMyCities;
import com.e.weatherappassignment.view.fragment.settings.FragmentSettings;
import com.e.weatherappassignment.view.fragment.tendayforecast.FragmentTenDayForecast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Handler mHandler;

    // Index to identify which is current menu-item.
    public static int navItemIndex = 0;

    // Tags Using for Fragment.
    public static String CURRENT_TAG = Constants.TAG_WEATHER;

    // For selection of toolbar titles.
    private String[] activityTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrequentFunction.skyBlueStatusBar(this);
        // Creating ToolBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Loading toolbar titles from string resources.
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        // Get main home Fragment as 5-Day Weather Only.
        getHomeFragment();

        // Initializing navigation menu.
        setUpNavigationView();

        // If nothing is clicked.
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = Constants.TAG_WEATHER;
            loadHomeFragment();
        }
    }

    private void showPremiumDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog_premium);
        LinearLayout linearLayoutBuy = bottomSheetDialog.findViewById(R.id.linearLayoutBuy);
        linearLayoutBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref.setBoolean(Constants.IS_PREMIUM, true);
                if (navItemIndex == 0) {
                    CURRENT_TAG = Constants.TAG_WEATHER;
                    loadHomeFragment();
                    bottomSheetDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Success, Upgraded to premium plan.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        bottomSheetDialog.show();
    }


    private Fragment getHomeFragment()
    {
        switch (navItemIndex) {
            case 0:
                // Manage Five Day Weather Fragment.
                FragmentFiveDayWeather fiveDayWeatherFragment = new FragmentFiveDayWeather();
                return fiveDayWeatherFragment;
            case 1:
                // Manage Hourly Weather Fragment.
                FragmentHourlyWeather hourlyWeatherFragment = new FragmentHourlyWeather();
                return hourlyWeatherFragment;
            case 2:
                // Manage 10-Day Forecast Fragment.
                FragmentTenDayForecast tenDayForecastFragment = new FragmentTenDayForecast();
                return tenDayForecastFragment;
            case 3:
                // Manage MoonPhase Fragment.
                FragmentMoonPhase moonPhaseFragment = new FragmentMoonPhase();
                return moonPhaseFragment;
            case 4:
                // Manage My Cities Fragment.
                FragmentMyCities myCitiesFragment = new FragmentMyCities();
                return myCitiesFragment;
            case 5:
                // Manage Settings Fragment.
                FragmentSettings settingsFragment = new FragmentSettings();
                return settingsFragment;
            case 6:
                // Manage About Fragment.
                FragmentAbout aboutFragment = new FragmentAbout();
                return aboutFragment;
            default:
                return new FragmentFiveDayWeather();
        }
    }

    private void loadHomeFragment() {

        // Selecting Navigation Menu Item.
        selectNavMenu();

        // Setting Toolbar Title.
        setToolbarTitle();

        // If user is selecting Same Menu Again, then just closing Navigation Drawer.
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // The fragment is loaded with cross fade effect with runnable
        // as it might be possible that screen got hanged dur to large data.
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                // Updating the main content.
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If not null, then adding it to message queue.
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // Closing drawers.
        drawer.closeDrawers();

        // Refreshing Toolbar menu.
        invalidateOptionsMenu();

    }

    private void setToolbarTitle()
    {
        // Setting ToolBar Title From that string resources.
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu()
    {
        // Selecting Nav Menu Item as checked.
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        // Working on Selecting Item.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Checking that which item is clicked.
                switch (menuItem.getItemId()) {

                    // Replacing the fragment as per the clicked item.
                    case R.id.nav_five_day_weather:
                        navItemIndex = 0;
                        CURRENT_TAG = Constants.TAG_WEATHER;
                        break;

                    case R.id.nav_hourly_weather:
                        if (SharedPref.getBoolean(Constants.IS_PREMIUM))
                            Toast.makeText(MainActivity.this, "You already have premium plan.", Toast.LENGTH_SHORT).show();
                        else
                            showPremiumDialog();
                        break;

                    case R.id.nav_ten_day_forecast:
                        navItemIndex = 2;
                        CURRENT_TAG = Constants.TAG_TEN_DAY_FORECAST;
                        break;

                    case R.id.nav_my_cities:
                        navItemIndex = 4;
                        CURRENT_TAG = Constants.TAG_MY_CITIES;
                        break;

                    case R.id.nav_settings:
                        navItemIndex = 5;
                        CURRENT_TAG = Constants.TAG_SETTINGS;
                        break;

                    case R.id.nav_about:
                        navItemIndex = 6;
                        CURRENT_TAG = Constants.TAG_ABOUT;
                        break;

                    default:
                        navItemIndex = 0;
                }

                // If not in checked state, then make it in checked state.
                menuItem.setChecked(!menuItem.isChecked());
                menuItem.setChecked(true);

                // Loading Home Fragment.
                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout.
        drawer.setDrawerListener(actionBarDrawerToggle);

        // Calling Sync State for showing HamBurger Icon.
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {

        // If drawer is open, then close it first.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // If home fragment is not opened, then get back to home fragment.
        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = Constants.TAG_WEATHER;
            loadHomeFragment();
            return;
        }


        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the Action Bar.

        // Showing menu only, if home fragment is selected.
        if (navItemIndex >= 0 && navItemIndex <=3) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Getting item ID.
        int id = item.getItemId();

        // If share button is clicked, then doing the work of sharing Intent.
        if (id == R.id.action_share) {
            onShareClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onShareClick() {
        String shareBody = "Please Check New Weather App!";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Weather App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }
}
