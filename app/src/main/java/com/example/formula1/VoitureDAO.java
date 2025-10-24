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

    @Query("UPDATE voiture set carburant = :carburant WHERE id = :id")
    void updateCarburantById(int id, int carburant);

    @Query("UPDATE voiture set pneu = :pneu WHERE id = :id")
    void updatePneuById(int id, String pneu);

    @Query("DELETE FROM voiture WHERE id = :id")
    void deleteVoitureById(int id);
}
