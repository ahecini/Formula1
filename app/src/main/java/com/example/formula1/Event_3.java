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

import java.util.Random;
import java.util.concurrent.Executors;

public class Event_3 extends AppCompatActivity {

    private TextView actualPosition;
    private int piloteId;
    private boolean reussite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event3);

        actualPosition = findViewById(R.id.actualPosition);
        piloteId = getIntent().getIntExtra("piloteId", -1);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void depassement(View view){
        Random random = new Random();
        int proba = random.nextInt(10);

        if(proba < 7){
            reussite = true;
            consequence();
        } else{
            reussite = false;
            consequence();
        }
    }

    public void securite(View view){
        Intent intent = new Intent(Event_3.this, Podium.class);
        intent.putExtra("piloteId", piloteId);
        startActivity(intent);
    }

    public void consequence(){
        Executors.newSingleThreadExecutor().execute(()->{
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            if(reussite){
                db.piloteDAO().updatePositionById(piloteId, data.pilote.getPosition()-1);
            } else {
                db.piloteDAO().updatePositionById(piloteId, data.pilote.getPosition()+1);
            }

            runOnUiThread(()->{
                Intent intent = new Intent(Event_3.this, Podium.class);
                intent.putExtra("piloteId", piloteId);
                startActivity(intent);
            });
        });
    }

    private void loadData(){
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            int position = data.pilote.getPosition();

            runOnUiThread(() -> {
                actualPosition.setText(String.valueOf(position));
            });
        });
    }

}