package com.e.weatherappassignment.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.e.weatherappassignment.R;

public class BaseActivity extends AppCompatActivity {

    private Dialog mDialog;
    private int permissionNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    public int checkPermission(String[] permissions) {
        permissionNeeded = 0;
        if (Build.VERSION.SDK_INT >= 23) {
            for (int i = 0; i < permissions.length; i++) {
                int result = ContextCompat.checkSelfPermission(this, permissions[i]);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionNeeded++;
                }
            }
        }
        return permissionNeeded;
    }

    public void showProgress() {
        mDialog = new Dialog(this);

        // Setting it to no TITLE.
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.prograss_bar_dialog);
        ProgressBar mProgressBar = mDialog.findViewById(R.id.progress_bar);

        mProgressBar.setVisibility(View.VISIBLE);

        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}