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

    @Query("SELECT valeur FROM boite WHERE id = :id")
    int getBoiteValueById(int id);

    @Query("SELECT illegal FROM boite WHERE id = :id")
    boolean getBoiteIllegalById(int id);

    @Query("UPDATE boite SET valeur = :valeur WHERE id = :id")
    void updateBoiteValueById(int id, int valeur);

    @Query("UPDATE boite SET illegal = :illegal WHERE id = :id")
    void updateBoiteIllegalById(int id, boolean illegal);

    @Query("DELETE FROM boite WHERE id = :id")
    void deleteBoiteById(int id);

    @Query("DELETE FROM boite")
    void deleteAllBoite();
}
