package com.example.formula1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Essais extends AppCompatActivity {

    private TextView textViewChoseTires;
    private TextView textViewChoseFuel;
    private TextView textViewChoseConso;
    private TextView textViewChoseStrat;

    private SeekBar seekBarTire;
    private SeekBar seekBarFuel;
    private SeekBar seekBarConso;
    private SeekBar seekBarStrat;

    private Button buttonValidateQualif;
    private Button buttonQuitQualif;

    private String[] tireType;
    private String[] fuelStrat;
    private String[] consoStrat;
    private String[] stratType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_essais);

        textViewChoseTires = findViewById(R.id.textViewChoseTires);
        textViewChoseFuel = findViewById(R.id.textViewChoseFuel);
        textViewChoseConso = findViewById(R.id.textViewChoseConso);
        textViewChoseStrat = findViewById(R.id.textViewChoseStrat);

        seekBarTire = findViewById(R.id.seekBarTire);
        seekBarFuel = findViewById(R.id.seekBarFuel);
        seekBarConso = findViewById(R.id.seekBarConso);
        seekBarStrat = findViewById(R.id.seekBarStrat);

        buttonValidateQualif = findViewById(R.id.buttonValidateQualif);
        buttonQuitQualif = findViewById(R.id.buttonQuitQualif);

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
        textViewChoseFuel.setText(fuelStrat[startFuel]);

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

    public void quit(){
        finish();
    }


}