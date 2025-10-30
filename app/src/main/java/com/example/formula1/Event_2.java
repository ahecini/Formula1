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

public class Event_2 extends AppCompatActivity {

    private TextView textViewActualTire2;
    private TextView textViewActualFuel;
    private int piloteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event2);

        textViewActualTire2 = findViewById(R.id.textViewActualTire2);
        textViewActualFuel = findViewById(R.id.textViewActualFuel);

        piloteId = getIntent().getIntExtra("piloteId", -1);
        if(piloteId == -1) {
            finish();
            return;
        }

        loadVoitureData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadVoitureData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();

            VoitureAvecPiece voitureAvecPiece = db.voitureDAO().getVoitureAvecPieceById(voitureId);

            if(voitureAvecPiece == null) {
                Log.e("Event_2", "Voiture avec ID " + voitureId + " introuvable");
                return;
            }

            runOnUiThread(() -> {
                textViewActualTire2.setText(voitureAvecPiece.voiture.getPneu());
                textViewActualFuel.setText(voitureAvecPiece.voiture.getCarburant() + " %");
            });
        });
    }

    public void pitStop(View view){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();

            db.voitureDAO().updateCarburantById(voitureId, 100);
            db.piloteDAO().updatePositionById(piloteId, data.pilote.getPosition()+1);

            runOnUiThread(()->{
                Intent intent = new Intent(Event_2.this, Podium.class);
                intent.putExtra("piloteId", piloteId);
                startActivity(intent);
            });
        });
    }

    public void stay(View view){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);
            int voitureId = data.voitureAvecPiece.voiture.getId();
            int carburant = data.voitureAvecPiece.voiture.getCarburant();

            db.piloteDAO().updatePositionById(piloteId, data.pilote.getPosition()-3);
            db.voitureDAO().updateCarburantById(voitureId, carburant-25);

            runOnUiThread(()->{
                Intent intent = new Intent(Event_2.this, Event_3.class);
                intent.putExtra("piloteId", piloteId);
                startActivity(intent);
            });
        });
    }
}