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

    @Query("SELECT valeur FROM suspension WHERE id = :id")
    int getSuspensionValueById(int id);

    @Query("SELECT illegal FROM suspension WHERE id = :id")
    boolean getSuspensionIllegalById(int id);

    @Query("UPDATE suspension SET valeur = :valeur WHERE id = :id")
    void updateSuspensionValueById(int id, int valeur);

    @Query("UPDATE suspension SET illegal = :illegal WHERE id = :id")
    void updateSuspensionIllegalById(int id, boolean illegal);

    @Query("DELETE FROM suspension WHERE id = :id")
    void deleteSuspensionById(int id);

    @Query("DELETE FROM suspension")
    void deleteAllSuspension();
}
