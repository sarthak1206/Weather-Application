package com.e.weatherappassignment.view.activity.addlocation.presenter;

import com.e.weatherappassignment.view.activity.addlocation.view.AddLocationActivityView;
import com.e.weatherappassignment.web.WebServices;
import com.e.weatherappassignment.web.handler.NearByPlacesHandler;

public class AddLocationActivityPresenter implements AddLocationActivityPresenterHandler {
    AddLocationActivityView view;

    public AddLocationActivityPresenter(AddLocationActivityView view) {
        this.view = view;
    }

    @Override
    public void getData(String apiKey, String latitude, String longitude, String text) {
        WebServices.getInstance().getNearByPlaces(new NearByPlacesHandler() {
            @Override
            public void onSuccess(String response) {
                view.onSuccessfullyGetPlace(response);
            }


            @Override
            public void onError(String message) {
                view.onFailure(message);
            }
        }, apiKey, latitude, longitude, text);
    }

}