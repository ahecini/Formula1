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

import java.util.concurrent.Executors;

public class EssaisResultats extends AppCompatActivity {

    private TextView textViewTemps;
    private TextView textViewPosition;

    private TextView textViewMotorAttribut;
    private TextView textViewGearAttribut;
    private TextView textViewSuspensionAttribut;
    private TextView textViewBrakeAttribut;

    private TextView textViewActualTires;
    private TextView textViewActualFuel;
    private TextView textViewStratChoice;
    private TextView textViewConsoChoice;

    private int voitureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_essais_resultats);

        voitureId = getIntent().getIntExtra("VoitureId", -1);
        if (voitureId == -1) {
            finish();
            return;
        }

        textViewBrakeAttribut = findViewById(R.id.textViewBrakeAttribut);
        textViewGearAttribut = findViewById(R.id.textViewGearAttribut);
        textViewSuspensionAttribut = findViewById(R.id.textViewSuspensionAttribut);
        textViewMotorAttribut = findViewById(R.id.textViewMotorAttribut);

        textViewActualTires = findViewById(R.id.textViewActualTires);
        textViewActualFuel = findViewById(R.id.textViewActualFuel3);

        textViewStratChoice = findViewById(R.id.textViewStratChoice);
        textViewConsoChoice = findViewById(R.id.textViewConsoChoice);

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

        intent.putExtra("VoitureId", voitureId);

        startActivity(intent);
    }

    private void loadVoitureData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            VoitureAvecPiece voitureAvecPiece = db.voitureDAO().getVoitureAvecPieceById(voitureId);

            if (voitureAvecPiece == null) {
                Log.e("EssaisResultats", "Voiture avec ID " + voitureId + " introuvable");
                return;
            }

            runOnUiThread(() -> {
                textViewBrakeAttribut.setText(String.valueOf(voitureAvecPiece.frein.getValeur()));
                textViewGearAttribut.setText(String.valueOf(voitureAvecPiece.boite.getValeur()));
                textViewSuspensionAttribut.setText(String.valueOf(voitureAvecPiece.suspension.getValeur()));
                textViewMotorAttribut.setText(String.valueOf(voitureAvecPiece.moteur.getValeur()));

                textViewActualTires.setText(voitureAvecPiece.voiture.getPneu());
                textViewActualFuel.setText(voitureAvecPiece.voiture.getCarburant() + " %");
            });
        });
    }
}