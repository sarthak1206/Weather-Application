package com.e.weatherappassignment.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Favourite.class}, version = 1, exportSchema = false)
public abstract class FavouriteDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();
}
