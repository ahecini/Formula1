package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CarActivity extends AppCompatActivity {

    private Spinner spinnerBrake;
    private Spinner spinnerGear;
    private Spinner spinnerMotor;
    private Spinner spinnerSuspension;

    private Switch switchLegalBrake;
    private Switch switchLegalGear;
    private Switch switchLegalMotor;
    private Switch switchLegalSuspension;

    private int piloteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car);

        spinnerBrake = findViewById(R.id.spinnerTurn);
        spinnerGear = findViewById(R.id.spinnerAdapt);
        spinnerMotor = findViewById(R.id.spinnerReact);
        spinnerSuspension = findViewById(R.id.spinnerControl);

        switchLegalBrake = findViewById(R.id.switchLegalBrake);
        switchLegalGear = findViewById(R.id.switchLegalGear);
        switchLegalMotor = findViewById(R.id.switchLegalMotor);
        switchLegalSuspension = findViewById(R.id.switchLegalSuspension);

        piloteId = getIntent().getIntExtra("piloteId", -1);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void close(View view){
        finish();
    }

    public void validate(View view) {
        Intent intent = new Intent(this, CarRecapActivity.class);

        int brake = Integer.parseInt(spinnerBrake.getSelectedItem().toString());
        int gear = Integer.parseInt(spinnerGear.getSelectedItem().toString());
        int motor = Integer.parseInt(spinnerMotor.getSelectedItem().toString());
        int suspension = Integer.parseInt(spinnerSuspension.getSelectedItem().toString());

        if(!verifAttributDiff(brake, gear, motor, suspension)){
            Toast.makeText(this, "Les valeurs doivent être différentes.", Toast.LENGTH_SHORT).show();
            return;
        }

        brake = variation(brake);
        gear = variation(gear);
        motor = variation(motor);
        suspension = variation(suspension);

        if (switchLegalBrake.isChecked()){
            brake += 1;
        }
        if (switchLegalGear.isChecked()){
            gear += 1;
        }
        if (switchLegalMotor.isChecked()){
            motor += 1;
        }
        if (switchLegalSuspension.isChecked()){
            suspension += 1;
        }

        intent.putExtra("Brake", brake);
        intent.putExtra("BrakeIsIllegal", switchLegalBrake.isChecked());

        intent.putExtra("Gear", gear);
        intent.putExtra("GearIsIllegal", switchLegalGear.isChecked());

        intent.putExtra("Motor", motor);
        intent.putExtra("MotorIsIllegal", switchLegalMotor.isChecked());

        intent.putExtra("Suspension", suspension);
        intent.putExtra("SuspensionIsIllegal", switchLegalSuspension.isChecked());

        intent.putExtra("PiloteId", piloteId);

        startActivity(intent);
    }

    private int variation (int value){
        Random random = new Random();

        value = value + random.nextInt(3) - 1;

        if(value == 0){
            value = 1;
        }

        return value;
    }

    private boolean verifAttributDiff(int brake, int gear, int motor, int suspension){
        Set<Integer> attributs = new HashSet<>();

        attributs.add(brake);
        attributs.add(gear);
        attributs.add(motor);
        attributs.add(suspension);

        return attributs.size() == 4;
    }
}
