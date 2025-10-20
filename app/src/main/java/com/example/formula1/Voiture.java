package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Voiture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voiture);

        TextView textViewCar = findViewById(R.id.textViewCar);
        TextView textViewBrake = findViewById(R.id.textViewBrake);
        TextView textViewGear = findViewById(R.id.textViewGear);
        TextView textViewMotor = findViewById(R.id.textViewMotor);
        TextView textViewSuspension = findViewById(R.id.textViewSuspension);

        Spinner spinnerBrake = findViewById(R.id.spinnerBrake);
        Spinner spinnerGear = findViewById(R.id.spinnerGear);
        Spinner spinnerMotor = findViewById(R.id.spinnerMotor);
        Spinner spinnerSuspension = findViewById(R.id.spinnerSuspension);

        Button validate = findViewById(R.id.buttonValidate);
        Button quit = findViewById(R.id.buttonQuit);

        Switch switchLegalBrake = findViewById(R.id.switchLegalBrake);
        Switch switchLegalGear = findViewById(R.id.switchLegalGear);
        Switch switchLegalMotor = findViewById(R.id.switchLegalMotor);
        Switch switchLegalSuspension = findViewById(R.id.switchLegalSuspension);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private Spinner spinnerBrake;
    private Spinner spinnerGear;
    private Spinner spinnerMotor;
    private Spinner spinnerSuspension;

    private Button validate;
    private Button quit;

    private Switch switchLegalBrake;
    private Switch switchLegalGear;
    private Switch switchLegalMotor;
    private Switch switchLegalSuspension;

    public void close(View view){
        finish();
    }

    public void validate(View view) {
        Intent intent = new Intent(this, VoitureRecap.class);

        int Brake = spinnerBrake.getSelectedItemPosition();
        int Gear = spinnerGear.getSelectedItemPosition();
        int Motor = spinnerMotor.getSelectedItemPosition();
        int Suspension = spinnerSuspension.getSelectedItemPosition();

        intent.putExtra("Brake", Brake);
        intent.putExtra("Gear", Gear);
        intent.putExtra("Motor", Motor);
        intent.putExtra("Suspension", Suspension);


        startActivity(intent);
    }

}