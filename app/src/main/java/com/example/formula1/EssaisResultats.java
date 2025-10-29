package com.example.formula1;

import android.annotation.SuppressLint;
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

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;

public class EssaisResultats extends AppCompatActivity {

    private TextView textViewTemps;
    private TextView actualTime;

    private TextView textViewMotorAttribut;
    private TextView textViewGearAttribut;
    private TextView textViewSuspensionAttribut;
    private TextView textViewBrakeAttribut;

    private TextView textViewActualTires;
    private TextView textViewActualFuel;
    private TextView textViewStratChoice;
    private TextView textViewConsoChoice;

    private String[] name;

    private int piloteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_essais_resultats);

        piloteId = getIntent().getIntExtra("piloteId", -1);
        if (piloteId == -1) {
            finish();
            return;
        }

        actualTime = findViewById(R.id.textViewActualTime);

        textViewBrakeAttribut = findViewById(R.id.textViewBrakeAttribut);
        textViewGearAttribut = findViewById(R.id.textViewGearAttribut);
        textViewSuspensionAttribut = findViewById(R.id.textViewSuspensionAttribut);
        textViewMotorAttribut = findViewById(R.id.textViewMotorAttribut);

        textViewActualTires = findViewById(R.id.textViewActualTires);
        textViewActualFuel = findViewById(R.id.textViewActualFuel3);

        textViewStratChoice = findViewById(R.id.textViewStratChoice);
        textViewConsoChoice = findViewById(R.id.textViewConsoChoice);

        name = getResources().getStringArray(R.array.names);

        loadVoitureData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void quit (View view){
        finish();
    }

    public void validate (View view){
        Intent intent = new Intent(this, Event_1.class);

        generatePilot();

        intent.putExtra("PiloteId", piloteId);

        startActivity(intent);
    }

    private void loadVoitureData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();
            VoitureAvecPiece voitureAvecPiece = db.voitureDAO().getVoitureAvecPieceById(voitureId);

            if (voitureAvecPiece == null) {
                Log.e("EssaisResultats", "Voiture avec ID " + voitureId + " introuvable");
                return;
            }

            runOnUiThread(() -> {
                actualTime.setText(formatTime(data.pilote.getTemps()));

                textViewBrakeAttribut.setText(String.valueOf(voitureAvecPiece.frein.getValeur()));
                textViewGearAttribut.setText(String.valueOf(voitureAvecPiece.boite.getValeur()));
                textViewSuspensionAttribut.setText(String.valueOf(voitureAvecPiece.suspension.getValeur()));
                textViewMotorAttribut.setText(String.valueOf(voitureAvecPiece.moteur.getValeur()));

                textViewActualTires.setText(voitureAvecPiece.voiture.getPneu());
                textViewActualFuel.setText(voitureAvecPiece.voiture.getCarburant() + " %");
            });
        });
    }

    private void generatePilot(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            for (int i = 0; i < 20 ; i++) {
                Pilote pilote = new Pilote(name[i], variation(), variation(), variation(), variation());

                long piloteId = db.piloteDAO().insert(pilote);

                db.piloteDAO().updateTempsById((int)piloteId, randomTemps());
            }
        });
    }

    private int variation (){
        int value;
        Random random = new Random();

        value = random.nextInt(5);

        if(value == 0){
            value = 1;
        }

        return value;
    }

    private int randomTemps(){
        int value;
        Random random = new Random();

        value = random.nextInt(180000);

        return value;
    }

    private String formatTime(int millis) {
        int minutes = (millis / 1000) / 60;
        int seconds = (millis / 1000) % 60;
        int milliseconds = millis % 1000;
        return String.format(Locale.getDefault(), "%02d:%02d.%03d", minutes, seconds, milliseconds);
    }
}