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

    @Query("SELECT valeur FROM moteur WHERE id = :id")
    int getMoteurValueById(int id);

    @Query("SELECT illegal FROM moteur WHERE id = :id")
    boolean getMoteurIllegalById(int id);

    @Query("UPDATE moteur SET valeur = :valeur WHERE id = :id")
    void updateMoteurValueById(int id, int valeur);

    @Query("UPDATE moteur SET illegal = :illegal WHERE id = :id")
    void updateMoteurIllegalById(int id, boolean illegal);

    @Query("DELETE FROM moteur WHERE id = :id")
    void deleteMoteurById(int id);
}
