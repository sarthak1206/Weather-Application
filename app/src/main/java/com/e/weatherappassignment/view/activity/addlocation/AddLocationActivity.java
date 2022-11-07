package com.e.weatherappassignment.view.activity.addlocation;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.weatherappassignment.R;
import com.e.weatherappassignment.database.Favourite;
import com.e.weatherappassignment.database.FavouriteRepository;
import com.e.weatherappassignment.model.NearBySearch;
import com.e.weatherappassignment.model.SearchAddress;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.utils.Event;
import com.e.weatherappassignment.utils.GPSTracker;
import com.e.weatherappassignment.utils.PlaceParser;
import com.e.weatherappassignment.utils.SearchAddressManager;
import com.e.weatherappassignment.utils.Utils;
import com.e.weatherappassignment.view.activity.BaseActivity;
import com.e.weatherappassignment.view.activity.addlocation.adapter.SearchAdapter;
import com.e.weatherappassignment.view.activity.addlocation.presenter.AddLocationActivityPresenter;
import com.e.weatherappassignment.view.activity.addlocation.presenter.AddLocationActivityPresenterHandler;
import com.e.weatherappassignment.view.activity.addlocation.view.AddLocationActivityView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddLocationActivity extends BaseActivity implements AddLocationActivityView, TextWatcher, SearchAdapter.AdapterItem {
    EditText search;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    String status = "YES", comesFrom = "";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    ArrayList<String> arrayList;
    public static String API_KEY = "AIzaSyAsE0edaQKl5wgqcfTibDmdUuHQgFEoldc";
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.iv_clear)
    TextView ivClear;
    @BindView(R.id.activity_search_)
    LinearLayout activitySearch;
    private Bundle bundle;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchAddress> addressArrayList;
    SearchAddress searchAddress;
    ArrayList<String> searchResults = null;
    private SearchAddressManager searchManager;
    ArrayList<String> placeid;
    ArrayList<SearchAddress> address;
    GPSTracker gpsTracker;
    String latitude = "", longitude = "";
    private NearBySearch nearBySearch;
    public static List<NearBySearch.Result> nearByList;
    AddLocationActivityPresenterHandler mPresenter;
    SearchAdapter.AdapterItem adapterItem;
    FavouriteRepository repository;
    Favourite favourite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ButterKnife.bind(this);

        adapterItem=this;
        repository= new FavouriteRepository(this);
        mPresenter = new AddLocationActivityPresenter(this);
        search = findViewById(R.id.searchView1);
        gpsTracker = new GPSTracker(this);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("type")) {
            comesFrom = bundle.getString("type");
        }
        searchManager = new SearchAddressManager();
        search.addTextChangedListener(this);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Utils.getInstanse().hideKeyboard(AddLocationActivity.this, activitySearch);
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //new ExecuteData().execute(String.valueOf(s));
        searchManager.getAddress(this, s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Event event) {
        Log.e("call", "eventbus");

        switch (event.getKey()) {
            case Constants.SEARCHSUCCESS:
                Log.e("on call", "0");
                placeid = SearchAddressManager.placeIdList;
                address = SearchAddressManager.addressList;
                Log.e("addresslist", address.toString());
                SearchAdapter searchAdapter = new SearchAdapter(this, address, placeid,adapterItem);
                recyclerView.setAdapter(searchAdapter);
                searchAdapter.notifyDataSetChanged();
                break;
            case Constants.LATLONG:

                favourite.setLatitude(Constants.serachlat);
                favourite.setLongitude(Constants.serachlng);
                repository.insertTask(favourite);
                Constants.searcstatus = "true";
                com.e.weatherappassignment.utils.Utils.hideKeyboard(AddLocationActivity.this,activitySearch);
                finish();
                break;
        }


    }

    @OnClick({R.id.back, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_clear:
                search.setText("");
                break;
        }
    }

    public void performSearch() {
        latitude = String.valueOf(gpsTracker.getLatitude());
        longitude = String.valueOf(gpsTracker.getLongitude());
        showProgress();
        mPresenter.getData(Constants.API_KEY, latitude, longitude, search.getText().toString());

    }

    @Override
    public void onSuccessfullyGetPlace(String response) {
        hideProgress();
        Log.e("SEARCHNEARBY", response + "");
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("OK")) {
                nearBySearch = new Gson().fromJson(response, NearBySearch.class);
                nearByList = nearBySearch.getResults();
            } else {
                Toast.makeText(this, "No result founded.", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String response) {
        hideProgress();
        Toast.makeText(this, "No result founded.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        Log.e("PlaceId", placeid.get(position));
        PlaceParser placeParser = new PlaceParser();
        placeParser.getAddress(this, placeid.get(position));
        favourite= new Favourite();
        favourite.setTitle(address.get(position).getTitle());
        favourite.setDescription(address.get(position).getFullAddress());
    }
}
