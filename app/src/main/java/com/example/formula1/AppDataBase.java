package com.example.formula1;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executors;

@Database(
        entities = {Voiture.class, Moteur.class, Frein.class, Boite.class, Suspension.class, Pilote.class},
        version = 2,
        exportSchema = false
)
public abstract class AppDataBase extends RoomDatabase {
    public abstract VoitureDAO voitureDAO();
    public abstract PiloteDAO piloteDAO();
    public abstract MoteurDAO moteurDAO();
    public abstract FreinDAO freinDAO();
    public abstract BoiteDAO boiteDAO();
    public abstract SuspensionDAO suspensionDAO();

    public static AppDataBase INSTANCE;

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDataBase.class,
                    "formula1_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public void clearDatabase() {
        Executors.newSingleThreadExecutor().execute(() -> {
            this.clearAllTables();
        });
    }
}
