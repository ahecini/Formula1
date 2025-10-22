package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BoiteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Boite boite);

    @Query("SELECT * FROM boite WHERE id = :id")
    Boite getBoiteById(int id);

    @Query("DELETE FROM boite WHERE id = :id")
    void deleteBoiteById(int id);
}
