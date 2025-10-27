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
    private int voitureId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event2);

        textViewActualTire2 = findViewById(R.id.textViewActualTire2);
        textViewActualFuel = findViewById(R.id.textViewActualFuel);

        voitureId = getIntent().getIntExtra("VoitureId", -1);
        if(voitureId == -1) {
            finish();
            return;
        }

        loadVoitureData(voitureId);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadVoitureData(int voitureId){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
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
            db.voitureDAO().updateCarburantById(voitureId, 100);

            runOnUiThread(()->{
                Intent intent = new Intent(Event_2.this, EssaisResultats.class);
                intent.putExtra("VoitureId", voitureId);
                startActivity(intent);
            });
        });
    }

    public void stay(View view){
        Intent intent = new Intent(Event_2.this, EssaisResultats.class);
        intent.putExtra("VoitureId", voitureId);
        startActivity(intent);
    }
}