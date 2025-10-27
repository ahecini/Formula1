package com.example.formula1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "pilote",
        foreignKeys =
        {
            @ForeignKey(entity = Voiture.class,
                    parentColumns = "id",
                    childColumns = "voitureId",
                    onDelete = ForeignKey.SET_NULL
            )
        })
public class Pilote {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "voitureId", index = true)
    private Integer voitureId;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "virage")
    private int virage;

    @ColumnInfo(name = "adaptabilite")
    private int adaptabilite;

    @ColumnInfo(name = "controle")
    private int controle;

    @ColumnInfo(name = "reactivite")
    private int reactivite;

    @ColumnInfo(name = "temps")
    private int temps;

    public Pilote(Integer voitureId,
                  String nom,
                  int virage,
                  int adaptabilite,
                  int controle,
                  int reactivite
                  ){
        this.voitureId = voitureId;
        this.nom = nom;
        this.virage = virage;
        this.adaptabilite = adaptabilite;
        this.controle = controle;
        this.reactivite = reactivite;
        this.temps = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(Integer voitureId) {
        this.voitureId = voitureId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getVirage() {
        return virage;
    }

    public void setVirage(int virage) {
        this.virage = virage;
    }

    public int getAdaptabilite() {
        return adaptabilite;
    }

    public void setAdaptabilite(int adaptabilite) {
        this.adaptabilite = adaptabilite;
    }

    public int getControle() {
        return controle;
    }

    public void setControle(int controle) {
        this.controle = controle;
    }

    public int getReactivite() {
        return reactivite;
    }

    public void setReactivite(int reactivite) {
        this.reactivite = reactivite;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }
}
