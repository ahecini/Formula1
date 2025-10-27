package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class VoitureRecap extends AppCompatActivity {


    private int brakeValue;
    private boolean brakeIsIllegal;
    private int gearValue;
    private boolean gearIsIllegal;
    private int motorValue;
    private boolean motorIsIllegal;
    private int suspensionValue;
    private boolean suspensionIsIllegal;

    private TextView brakeText;
    private TextView gearText;
    private TextView motorText;
    private TextView suspensionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voiture_recap);

        brakeValue = getIntent().getIntExtra("BrakeValue", 0);
        brakeIsIllegal = getIntent().getBooleanExtra("BrakeIsIllegal", false);
        brakeText = findViewById(R.id.textViewChoseTurn);
        brakeText.setText(String.valueOf(brakeValue));

        gearValue = getIntent().getIntExtra("GearValue", 0);
        gearIsIllegal = getIntent().getBooleanExtra("GearIsIllegal", false);
        gearText = findViewById(R.id.textViewChoseAdapt);
        gearText.setText(String.valueOf(gearValue));

        motorValue = getIntent().getIntExtra("MotorValue", 0);
        motorIsIllegal = getIntent().getBooleanExtra("MotorIsIllegal", false);
        motorText = findViewById(R.id.textViewChoseReact);
        motorText.setText(String.valueOf(motorValue));

        suspensionValue = getIntent().getIntExtra("SuspensionValue", 0);
        suspensionIsIllegal = getIntent().getBooleanExtra("SuspensionIsIllegal", false);
        suspensionText = findViewById(R.id.textViewChoseControl);
        suspensionText.setText(String.valueOf(suspensionValue));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void close (View view){
        finish();
    }

    public void validate (View view){
        Intent intent = new Intent(this, Essais.class);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());

            Moteur moteur = new Moteur(motorValue, motorIsIllegal);
            long moteurId = db.moteurDAO().insert(moteur);

            Frein frein = new Frein(brakeValue, brakeIsIllegal);
            long freinId = db.freinDAO().insert(frein);

            Boite boite = new Boite(gearValue, gearIsIllegal);
            long boiteId = db.boiteDAO().insert(boite);

            Suspension suspension = new Suspension(suspensionValue, suspensionIsIllegal);
            long suspensionId = db.suspensionDAO().insert(suspension);

            Voiture voiture = new Voiture((int) moteurId, (int) freinId, (int) boiteId, (int) suspensionId);
            long voitureId = db.voitureDAO().insert(voiture);

            intent.putExtra("VoitureId", (int)voitureId);

            runOnUiThread(() -> {
                startActivity(intent);
            });
        });
    }


}