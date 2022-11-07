package com.e.weatherappassignment.view.activity.splash;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.e.weatherappassignment.R;
import com.e.weatherappassignment.services.AppLocationManager;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.FrequentFunction;
import com.e.weatherappassignment.view.activity.BaseActivity;
import com.e.weatherappassignment.view.activity.main.MainActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    // Assigning permission array.
    String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    // Binding the ImageView and CircleProgressBar.
    @BindView(R.id.imageViewSplash)
    ImageView imageViewSplash;
    @BindView(R.id.progressBar)
    CircleProgressBar progressBar;

    private AppLocationManager appLocationManager;
    private LocationSettingsRequest mLocationSettingsRequest;
    private SettingsClient mSettingsClient;

    // Assigning some int of Request Code.
    private static final int REQUEST_CHECK_SETTINGS = 214;
    private static final int REQUEST_ENABLE_GPS = 516;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        FrequentFunction.fullScreen(this);
        // Loading a gif file and running it.
        Glide.with(this)
                .load(R.drawable.weather_gif)
                .into(imageViewSplash);

        // Setting Progress Bar.
        progressBar.setColorSchemeColors(SupportMenu.CATEGORY_MASK, ViewCompat.MEASURED_STATE_MASK);

        // Checking for permission, if its on then checking for GPS or else requesting it.
        if (checkPermission(permission) > 0) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.LOCATION_PERMISSION_CODE);
        } else {
            if (isGPSOn())
                navigateToNextActivity();
            else
                displayLocationSettingsRequest();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.LOCATION_PERMISSION_CODE) {
            if (isGPSOn())
                navigateToNextActivity();
            else
                displayLocationSettingsRequest();
        }
    }

    // Checking whether GPS is on or not.
    private boolean isGPSOn() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // Function for navigation to the next Activity which is Main Activity with some time delay of 2 sec.
    public void navigateToNextActivity()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    // Calling Intent which request for Enable GPS.
    private void openGPSOnSetting() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_ENABLE_GPS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case RESULT_OK:
                    navigateToNextActivity();
                    break;
                case RESULT_CANCELED:
                    Log.e("GPS", "User denied to access location");
                    openGPSOnSetting();
                    break;
            }
        } else if (requestCode == REQUEST_ENABLE_GPS) {
            if (!isGPSOn()) {
                openGPSOnSetting();
            } else {
                navigateToNextActivity();
            }
        }
    }

    // Displaying Location Settings Request.
    private void displayLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY));
        builder.setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();

        mSettingsClient = LocationServices.getSettingsClient(SplashActivity.this);

        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success Perform Task Here
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(SplashActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.e("GPS", "Unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                Log.e("GPS", "Location settings are inadequate, and cannot be fixed here. Fix in FragmentSettings.");
                        }
                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Log.e("GPS", "checkLocationSettings -> onCanceled");
                    }
                });
    }


}
