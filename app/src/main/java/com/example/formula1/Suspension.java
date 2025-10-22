package com.example.formula1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "suspension")
public class Suspension {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "valeur")
    private int valeur;

    @ColumnInfo(name = "illegal")
    private boolean illegal;

    public Suspension(int valeur, boolean illegal) {
        this.valeur = valeur;
        this.illegal = illegal;
    }

    public int getId() {
        return id;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean isIllegal() {
        return illegal;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public void setIllegal(boolean illegal) {
        this.illegal = illegal;
    }


}
