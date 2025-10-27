package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
public class CarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car);

        TextView textViewCar = findViewById(R.id.textViewCar);
        TextView textViewBrake = findViewById(R.id.textViewBrake);
        TextView textViewGear = findViewById(R.id.textViewGear);
        TextView textViewMotor = findViewById(R.id.textViewMotor);
        TextView textViewSuspension = findViewById(R.id.textViewSuspension);

        spinnerBrake = findViewById(R.id.spinnerTurn);
        spinnerGear = findViewById(R.id.spinnerAdapt);
        spinnerMotor = findViewById(R.id.spinnerReact);
        spinnerSuspension = findViewById(R.id.spinnerControl);

        validate = findViewById(R.id.buttonValidate);
        quit = findViewById(R.id.buttonQuit);

        switchLegalBrake = findViewById(R.id.switchLegalBrake);
        switchLegalGear = findViewById(R.id.switchLegalGear);
        switchLegalMotor = findViewById(R.id.switchLegalMotor);
        switchLegalSuspension = findViewById(R.id.switchLegalSuspension);

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
        Intent intent = new Intent(this, CarRecapActivity.class);

        String brakeTxt = spinnerBrake.getSelectedItem().toString();
        String gearTxt = spinnerGear.getSelectedItem().toString();
        String motorTxt = spinnerMotor.getSelectedItem().toString();
        String suspensionTxt = spinnerSuspension.getSelectedItem().toString();

        int brake = Integer.parseInt(Character.toString(brakeTxt.charAt(brakeTxt.length()-1)));
        int gear = Integer.parseInt(Character.toString(gearTxt.charAt(gearTxt.length()-1)));
        int motor = Integer.parseInt(Character.toString(motorTxt.charAt(motorTxt.length()-1)));
        int suspension = Integer.parseInt(Character.toString(suspensionTxt.charAt(suspensionTxt.length()-1)));

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
        intent.putExtra("Gear", gear);
        intent.putExtra("Motor", motor);
        intent.putExtra("Suspension", suspension);

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
}
