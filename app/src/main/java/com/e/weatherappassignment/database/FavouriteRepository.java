package com.e.weatherappassignment.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavouriteRepository {

    // Making some small database to store the list of cities.
    private String DB_NAME = "db_task";
    private FetchSavedListInterface fetchSavedListInterface;
    List<Favourite> favouritesList = new ArrayList<>();

    private FavouriteDatabase noteDatabase;

    public FavouriteRepository(Context context) {
        noteDatabase = Room.databaseBuilder(context, FavouriteDatabase.class, DB_NAME).build();
    }

    public void insertTask(String title,
                           String description,
                           String latitude,
                           String longitude) {

        Favourite  favourite = new Favourite();
        favourite.setTitle(title);
        favourite.setDescription(description);
        favourite.setCreatedAt(new Date());
        favourite.setModifiedAt(new Date());

        insertTask(favourite);
    }



    public void insertTask(final Favourite note) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                long a = noteDatabase.daoAccess().insertTask(note);
                return a;
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                Log.d("ISDatainserted", aLong + "   ");
            }
        }.execute();
    }

    public void updateTask(final Favourite note) {
        note.setModifiedAt(new Date());

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final String key) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.daoAccess().deleteById(key);
                return null;
            }
        }.execute();
    }


    public void getTasks(FetchSavedListInterface savedListInterface) {
        this.fetchSavedListInterface=savedListInterface;
        new AsyncTask<Void, Void, List<Favourite>>() {
            @Override
            protected List<Favourite> doInBackground(Void... voids) {
                favouritesList = noteDatabase.daoAccess().retrieveAllTasks();
                return favouritesList;
            }

            @Override
            protected void onPostExecute(List<Favourite> list) {
                super.onPostExecute(list);
                fetchSavedListInterface.savedPlaces(list);
            }

        }.execute();
        return ;
    }

    public interface FetchSavedListInterface {
        void savedPlaces(List<Favourite> list);

    }
}
