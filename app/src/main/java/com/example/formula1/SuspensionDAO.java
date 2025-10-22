package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SuspensionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Suspension suspension);

    @Query("SELECT * FROM suspension WHERE id = :id")
    Suspension getSuspensionById(int id);

    @Query("DELETE FROM suspension WHERE id = :id")
    void deleteSuspensionById(int id);
}
