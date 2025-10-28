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

public class RecapPiloteVoitureActivity extends AppCompatActivity {

    private int piloteId;

    private TextView turnValue;
    private TextView adaptValue;
    private TextView reactValue;
    private TextView controlValue;

    private TextView brakeValue;
    private TextView gearValue;
    private TextView motorValue;
    private TextView suspensionValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recap_pilote_voiture);

        piloteId = getIntent().getIntExtra("piloteId", -1);

        turnValue = findViewById(R.id.textViewChoseTurn);
        adaptValue = findViewById(R.id.textViewChoseAdapt);
        reactValue = findViewById(R.id.textViewChoseReact);
        controlValue = findViewById(R.id.textViewChoseControl);

        brakeValue = findViewById(R.id.textView35);
        gearValue = findViewById(R.id.textView40);
        motorValue = findViewById(R.id.textView41);
        suspensionValue = findViewById(R.id.textView42);

        loadData();

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
        Intent intent = new Intent(this, Essais.class);
        intent.putExtra("piloteId", piloteId);
        startActivity(intent);
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            runOnUiThread(() -> {
                turnValue.setText(String.valueOf(data.pilote.getVirage()));
                adaptValue.setText(String.valueOf(data.pilote.getAdaptabilite()));
                reactValue.setText(String.valueOf(data.pilote.getReactivite()));
                controlValue.setText(String.valueOf(data.pilote.getControle()));

                brakeValue.setText(String.valueOf(data.voitureAvecPiece.frein.getValeur()));
                gearValue.setText(String.valueOf(data.voitureAvecPiece.boite.getValeur()));
                motorValue.setText(String.valueOf(data.voitureAvecPiece.moteur.getValeur()));
                suspensionValue.setText(String.valueOf(data.voitureAvecPiece.suspension.getValeur()));
            });
        });
    }
}
