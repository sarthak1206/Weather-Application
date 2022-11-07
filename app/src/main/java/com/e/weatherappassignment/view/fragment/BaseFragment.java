package com.e.weatherappassignment.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.PopupWindow;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.e.weatherappassignment.R;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

public class BaseFragment extends Fragment {

    private Dialog mDialog;
    private Dialog progressDialog;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
    }

    public void showLoadingDialog() {
        progressDialog = new Dialog(getActivity());
        progressDialog.requestWindowFeature(1);
        progressDialog.setContentView(R.layout.custom_dialog);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((CircleProgressBar) progressDialog.findViewById(R.id.progressBar)).setColorSchemeColors(SupportMenu.CATEGORY_MASK, ViewCompat.MEASURED_STATE_MASK);
        progressDialog.show();
    }

    public void hideLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
