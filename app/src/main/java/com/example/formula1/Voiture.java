package com.example.formula1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "voiture",
foreignKeys = {
        @ForeignKey(entity = Moteur.class,
                parentColumns = "id",
                childColumns = "moteur_id"),
        @ForeignKey(entity = Frein.class,
                parentColumns = "id",
                childColumns = "frein_id"),
        @ForeignKey(entity = Boite.class,
                parentColumns = "id",
                childColumns = "boite_id"),
        @ForeignKey(entity = Suspension.class,
                parentColumns = "id",
                childColumns = "suspension_id")
    }
)
public class Voiture {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "moteur_id", index = true)
    private int moteurId;

    @ColumnInfo(name = "frein_id", index = true)
    private int freinId;

    @ColumnInfo(name = "boite_id", index = true)
    private int boiteId;

    @ColumnInfo(name = "suspension_id", index = true)
    private int suspensionId;

    @ColumnInfo(name = "carburant")
    private int carburant;

    @ColumnInfo(name = "pneu")
    private String pneu;

    public Voiture(int moteurId, int freinId, int boiteId, int suspensionId){
        this.moteurId = moteurId;
        this.freinId = freinId;
        this.boiteId = boiteId;
        this.suspensionId = suspensionId;
        this.carburant = 100;
        this.pneu = "Medium";
    }

    public int getId() {
        return id;
    }

    public int getMoteurId() {
        return moteurId;
    }

    public void setMoteurId(int moteurId) {
        this.moteurId = moteurId;
    }

    public int getFreinId() {
        return freinId;
    }

    public void setFreinId(int freinId) {
        this.freinId = freinId;
    }

    public int getBoiteId() {
        return boiteId;
    }

    public void setBoiteId(int boiteId) {
        this.boiteId = boiteId;
    }

    public int getSuspensionId() {
        return suspensionId;
    }

    public void setSuspensionId(int suspensionId) {
        this.suspensionId = suspensionId;
    }

    public int getCarburant() {
        return carburant;
    }

    public void setCarburant(int carburant) {
        this.carburant = carburant;
    }

    public String getPneu() {
        return pneu;
    }

    public void setPneu(String pneu) {
        this.pneu = pneu;
    }
}
