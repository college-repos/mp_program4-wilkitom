package com.example.thomaswilkinson.program4;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


    @Database(entities = {Cont.class,}, version = 1, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract DaoAccess ContDao();
}

