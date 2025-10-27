package com.example.formula1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DraftActivity extends AppCompatActivity {

    private EditText editTextName;
    private Spinner spinnerTurn;
    private Spinner spinnerAdapt;
    private Spinner spinnerControl;
    private Spinner spinnerReact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draft);

        editTextName = findViewById(R.id.editTextName);
        spinnerTurn = findViewById(R.id.spinnerTurn);
        spinnerAdapt = findViewById(R.id.spinnerAdapt);
        spinnerControl = findViewById(R.id.spinnerControl);
        spinnerReact = findViewById(R.id.spinnerReact);

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

        if (editTextName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Veuillez saisir un nom pour le pilote.", Toast.LENGTH_SHORT).show();
            return;
        }

        String nom = editTextName.getText().toString();
        int virage = Integer.parseInt(spinnerTurn.getSelectedItem().toString());
        int adaptabilite = Integer.parseInt(spinnerAdapt.getSelectedItem().toString());
        int controle = Integer.parseInt(spinnerControl.getSelectedItem().toString());
        int reactivite = Integer.parseInt(spinnerReact.getSelectedItem().toString());

        if(!verifAttributDiff(virage, adaptabilite, controle, reactivite)){
            Toast.makeText(this, "Les valeurs doivent être différentes.", Toast.LENGTH_SHORT).show();
            return;
        }

        virage = variation(virage);
        adaptabilite = variation(adaptabilite);
        controle = variation(controle);
        reactivite = variation(reactivite);

        Intent intent = new Intent(this, RecapPiloteActivity.class);

        intent.putExtra("nom", nom);
        intent.putExtra("virage", virage);
        intent.putExtra("adaptabilite", adaptabilite);
        intent.putExtra("controle", controle);
        intent.putExtra("réactivité", reactivite);

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

    private boolean verifAttributDiff(int virage, int adaptabilite, int control, int reactivite){
        Set<Integer> attributs = new HashSet<>();

        attributs.add(virage);
        attributs.add(adaptabilite);
        attributs.add(control);
        attributs.add(reactivite);

        return attributs.size() == 4;
    }
}
