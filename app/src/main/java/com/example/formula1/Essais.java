package com.example.formula1;

import android.view.View;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.content.Intent;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class Essais extends AppCompatActivity {

    private TextView textViewChoseTires;
    private TextView textViewChoseFuel;
    private TextView textViewChoseConso;
    private TextView textViewChoseStrat;

    private SeekBar seekBarTire;
    private SeekBar seekBarFuel;
    private SeekBar seekBarConso;
    private SeekBar seekBarStrat;

    private String[] tireType;
    private String[] fuelStrat;
    private String[] consoStrat;
    private String[] stratType;

    private int piloteId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_essais);

        piloteId = getIntent().getIntExtra("piloteId", -1);
        if (piloteId == -1) {
            finish();
            return;
        }

        textViewChoseTires = findViewById(R.id.textViewChoseTires);
        textViewChoseFuel = findViewById(R.id.textViewChoseFuel);
        textViewChoseConso = findViewById(R.id.textViewChoseConso);
        textViewChoseStrat = findViewById(R.id.textViewChoseStrat);

        seekBarTire = findViewById(R.id.seekBarTire);
        seekBarFuel = findViewById(R.id.seekBarFuel);
        seekBarConso = findViewById(R.id.seekBarConso);
        seekBarStrat = findViewById(R.id.seekBarStrat);

        tireType = getResources().getStringArray(R.array.tireType);
        fuelStrat = getResources().getStringArray(R.array.fuelStrat);
        consoStrat = getResources().getStringArray(R.array.consoStrat);
        stratType = getResources().getStringArray(R.array.stratType);

        seekBarTire.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){
                    textViewChoseTires.setText(tireType[progress]);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarFuel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    textViewChoseFuel.setText(fuelStrat[progress]);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarConso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    textViewChoseConso.setText(consoStrat[progress]);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarStrat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    textViewChoseStrat.setText(stratType[progress]);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int startTire = seekBarTire.getProgress();
        textViewChoseTires.setText(tireType[startTire]);

        int startFuel = seekBarFuel.getProgress();
        textViewChoseFuel.setText(fuelStrat[startFuel] + " %");

        int startConso = seekBarConso.getProgress();
        textViewChoseConso.setText(consoStrat[startConso]);

        int startStrat = seekBarStrat.getProgress();
        textViewChoseStrat.setText(stratType[startStrat]);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void quit(View view){
        finish();
    }

    public void validate(View view){
        int carburant = Integer.parseInt(fuelStrat[seekBarFuel.getProgress()]);
        String pneu = tireType[seekBarTire.getProgress()];

        majVoiture(carburant, pneu);
    }

    public void majVoiture(int carburant, String pneu){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            data.voitureAvecPiece.voiture.setPneu(pneu);
            data.voitureAvecPiece.voiture.setCarburant(carburant);

            runOnUiThread(() -> {
                Intent intent = new Intent(Essais.this, EssaisResultats.class);
                intent.putExtra("piloteId", piloteId);
                startActivity(intent);
            });
        });
    }
}