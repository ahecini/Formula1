package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class Event_1 extends AppCompatActivity {

    private TextView textViewActualTire;
    private TextView textViewActualFuel2;

    private String[] tireType;

    private int piloteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event1);

        piloteId = getIntent().getIntExtra("PiloteId", -1);
        if(piloteId == -1) {
            finish();
            return;
        }

        textViewActualTire = findViewById(R.id.textViewActualTire);
        textViewActualFuel2 = findViewById(R.id.textViewActualFuel2);
        tireType = getResources().getStringArray(R.array.tireType);

        loadVoitureData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void changeTire(View view){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();
            int temps = data.pilote.getTemps();
            int carburant = data.voitureAvecPiece.voiture.getCarburant();

            db.voitureDAO().updatePneuById(voitureId, tireType[3]);
            db.piloteDAO().updateTempsById(piloteId, temps+3000+60000);
            db.voitureDAO().updateCarburantById(voitureId, 100);


            runOnUiThread(()->{
               Intent intent = new Intent(Event_1.this, Event_2.class);
               intent.putExtra("piloteId", piloteId);
               startActivity(intent);
            });
        });
    }

    public void keepTire(View view){
        Executors.newSingleThreadExecutor().execute(()->{
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();
            int temps = data.pilote.getTemps();
            int carburant = data.voitureAvecPiece.voiture.getCarburant();

            db.piloteDAO().updateTempsById(piloteId, temps+60000);
            db.voitureDAO().updateCarburantById(voitureId, carburant-25);

            runOnUiThread(()->{
                Intent intent = new Intent(Event_1.this, Event_2.class);
                intent.putExtra("piloteId", piloteId);
                startActivity(intent);
            });
        });
    }

    private void loadVoitureData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();

            VoitureAvecPiece voitureAvecPiece = db.voitureDAO().getVoitureAvecPieceById(voitureId);

            if(voitureAvecPiece == null) {
                Log.e("Event_1", "Voiture avec ID " + voitureId + " introuvable");
                return;
            }

            runOnUiThread(() -> {
                textViewActualTire.setText(voitureAvecPiece.voiture.getPneu());
                textViewActualFuel2.setText(voitureAvecPiece.voiture.getCarburant() + " %");
            });
        });
    }
}