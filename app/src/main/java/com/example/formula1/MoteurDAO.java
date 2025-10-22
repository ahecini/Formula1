package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MoteurDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Moteur moteur);

    @Query("SELECT * FROM moteur WHERE id = :id")
    Moteur getMoteurById(int id);

    @Query("DELETE FROM moteur WHERE id = :id")
    void deleteMoteurById(int id);
}
