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

    private int voitureId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event1);

        voitureId = getIntent().getIntExtra("VoitureId", -1);
        if(voitureId == -1) {
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
            db.voitureDAO().updatePneuById(voitureId, tireType[3]);

            runOnUiThread(()->{
               Intent intent = new Intent(Event_1.this, EssaisResultats.class);
               intent.putExtra("VoitureId", voitureId);
               startActivity(intent);
            });
        });
    }

    public void keepTire(View view){
        Intent intent = new Intent(Event_1.this, EssaisResultats.class);
        intent.putExtra("VoitureId", voitureId);
        startActivity(intent);
    }

    private void loadVoitureData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
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