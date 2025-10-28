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

    @Query("SELECT valeur FROM frein WHERE id = :id")
    int getFreinValueById(int id);

    @Query("SELECT illegal FROM frein WHERE id = :id")
    boolean getFreinIllegalById(int id);

    @Query("UPDATE frein SET valeur = :valeur WHERE id = :id")
    void updateFreinValueById(int id, int valeur);

    @Query("UPDATE frein SET illegal = :illegal WHERE id = :id")
    void updateFreinIllegalById(int id, boolean illegal);

    @Query("DELETE FROM frein WHERE id = :id")
    void deleteFreinById(int id);

    @Query("DELETE FROM frein")
    void deleteAllFrein();

}
