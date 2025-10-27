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

public class RecapPiloteActivity extends AppCompatActivity {

    private String nom;
    private int virage;
    private int adaptabilite;
    private int controle;
    private int reactivite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recap_pilote);

        TextView textviewChoseTurn = findViewById(R.id.textViewChoseTurn);
        TextView textviewChoseAdapt = findViewById(R.id.textViewChoseAdapt);
        TextView textviewChoseReact = findViewById(R.id.textViewChoseReact);
        TextView textviewChoseControl = findViewById(R.id.textViewChoseControl);

        nom = getIntent().getStringExtra("nom");
        virage = getIntent().getIntExtra("virage", 0);
        adaptabilite = getIntent().getIntExtra("adaptabilite", 0);
        reactivite = getIntent().getIntExtra("réactivité", 0);
        controle = getIntent().getIntExtra("controle", 0);

        textviewChoseTurn.setText(String.valueOf(virage));
        textviewChoseAdapt.setText(String.valueOf(adaptabilite));
        textviewChoseReact.setText(String.valueOf(reactivite));
        textviewChoseControl.setText(String.valueOf(controle));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void close(View view){
        finish();
    }

    public void start(View view) {
        insertPilote();
    }

    private void insertPilote(){
        Pilote pilote = new Pilote(nom, virage, adaptabilite, controle, reactivite);
        Intent intent = new Intent(this, CarActivity.class);

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            long piloteId = db.piloteDAO().insert(pilote);
            intent.putExtra("piloteId", (int)piloteId);
        });

        runOnUiThread(() -> {
            startActivity(intent);
        });
    }
}
