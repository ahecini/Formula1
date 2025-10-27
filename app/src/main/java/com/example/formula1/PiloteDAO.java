package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PiloteDAO {

    @Insert
    long insert (Pilote pilote);

    @Query("SELECT * FROM pilote WHERE id = :id")
    Pilote getPiloteById(int id);

    @Query("UPDATE Pilote SET temps = :temps WHERE id = :id")
    void updateTempsById(int id, int temps);

    @Query("UPDATE Pilote SET voitureId = :voitureId WHERE id = :id")
    void updateVoitureIdById(int id, int voitureId);

    @Query("SELECT * FROM pilote ORDER BY nom ASC")
    List<Pilote> getAllPilotes();
}
