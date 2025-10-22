package com.example.formula1;

import androidx.room.Embedded;
import androidx.room.Relation;

public class VoitureAvecPiece {
    @Embedded
    public Voiture voiture;

    @Relation(parentColumn = "moteur_id", entityColumn = "id")
    public Moteur moteur;

    @Relation(parentColumn = "frein_id", entityColumn = "id")
    public Frein frein;

    @Relation(parentColumn = "boite_id", entityColumn = "id")
    public Boite boite;

    @Relation(parentColumn = "suspension_id", entityColumn = "id")
    public Suspension suspension;

}
