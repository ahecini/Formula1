package com.example.formula1;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PiloteEtVoiture {

    @Embedded
    public Pilote pilote;

    @Relation(
        entity = Voiture.class,
        parentColumn = "voitureId",
        entityColumn = "id"
    )
    public VoitureAvecPiece voitureAvecPiece;


}
