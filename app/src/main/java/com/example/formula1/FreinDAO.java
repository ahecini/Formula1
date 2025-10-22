package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FreinDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Frein frein);

    @Query("SELECT * FROM frein WHERE id = :id")
    Frein getFreinById(int id);

    @Query("DELETE FROM frein WHERE id = :id")
    void deleteFreinById(int id);

}
