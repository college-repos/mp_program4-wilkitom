package com.example.thomaswilkinson.program4;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    //select all from table
    @Query("SELECT * FROM cont")
    List<Cont> selectAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Cont> conts);

    @Insert
    long insert(Cont contData);

    @Query("select * from cont where name = :nameValue")
    List<Cont> loadCont(String nameValue);

    @Query("DELETE FROM cont WHERE id = :id")
    int deleteById(long id);

    @Update
    int update(Cont contData);


}
