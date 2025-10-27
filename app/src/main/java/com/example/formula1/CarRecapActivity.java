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

public class CarRecapActivity extends AppCompatActivity {

    private int brake;
    private int gear;
    private int motor;
    private int suspension;

    private TextView brakeText;
    private TextView gearText;
    private TextView motorText;
    private TextView suspensionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_recap);

        brake = getIntent().getIntExtra("Brake", 0);
        brakeText = findViewById(R.id.textViewChoseTurn);
        brakeText.setText(String.valueOf(brake));

        gear = getIntent().getIntExtra("Gear", 0);
        gearText = findViewById(R.id.textViewChoseAdapt);
        gearText.setText(String.valueOf(gear));

        motor = getIntent().getIntExtra("Motor", 0);
        motorText = findViewById(R.id.textViewChoseReact);
        motorText.setText(String.valueOf(motor));

        suspension = getIntent().getIntExtra("Suspension", 0);
        suspensionText = findViewById(R.id.textViewChoseControl);
        suspensionText.setText(String.valueOf(suspension));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void close (View view){
        finish();
    }

    public void validate(View view) {
        Intent intent = new Intent(this, EntrainementActivity.class);
        startActivity(intent);
    }
}
