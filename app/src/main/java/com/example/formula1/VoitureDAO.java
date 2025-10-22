package com.example.formula1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface VoitureDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Voiture voiture);

    @Transaction
    @Query("SELECT * FROM voiture WHERE id = :id")
    VoitureAvecPiece getVoitureAvecPieceById(int id);


    @Query("DELETE FROM voiture WHERE id = :id")
    void deleteVoitureById(int id);
}
