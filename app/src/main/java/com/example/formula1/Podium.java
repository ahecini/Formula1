package com.example.formula1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class Podium extends AppCompatActivity {

    private RecyclerView recyclerViewPodium;
    private PodiumAdapter podiumAdapter;
    private TextView textViewPenalty;
    private List<Pilote> piloteList = new ArrayList<>();

    private int piloteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_podium);

        textViewPenalty = findViewById(R.id.textViewPenalty);

        recyclerViewPodium = findViewById(R.id.recyclerViewPodium);
        podiumAdapter = new PodiumAdapter(piloteList);

        recyclerViewPodium.setLayoutManager((new LinearLayoutManager(this)));
        recyclerViewPodium.setAdapter(podiumAdapter);

        piloteId = getIntent().getIntExtra("piloteId", -1);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void quit(View view){
        finishAffinity();
    }

    private void loadData(){
        Executors.newSingleThreadExecutor().execute(()->{
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            ArrayList<Boolean> isIllegal;
            isIllegal = new ArrayList<>();

            int nbPieceIllegal = 0;
            int penalite = 0;
            isIllegal.add(data.voitureAvecPiece.moteur.isIllegal());
            isIllegal.add(data.voitureAvecPiece.frein.isIllegal());
            isIllegal.add(data.voitureAvecPiece.suspension.isIllegal());
            isIllegal.add(data.voitureAvecPiece.boite.isIllegal());

            for(int i = 0 ; i < isIllegal.size() ; i++){
                if(isIllegal.get(i)){
                    Random random = new Random();
                    int detect = random.nextInt(4);
                    if(detect == 0) {
                        penalite += 45000;
                        nbPieceIllegal ++;
                    }
                }
            }

            if(penalite > 0){
                db.piloteDAO().updateTempsById(piloteId, data.pilote.getTemps() + penalite);
            }

            List<Pilote> results = db.piloteDAO().getAllPilotes();

            Collections.sort(results, Comparator.comparingLong(Pilote::getTemps));

            final int penaliteFinal = penalite;
            final int nbPieceIllegalFinal = nbPieceIllegal;

            runOnUiThread(()->{

                if(penaliteFinal > 0) {
                    textViewPenalty.setText("Pénalité : +"+penaliteFinal/1000+"s ("+nbPieceIllegalFinal+" pièces illégales)");
                    textViewPenalty.setVisibility(View.VISIBLE);
                }
                piloteList.clear();
                piloteList.addAll(results);
                podiumAdapter.notifyDataSetChanged();
            });
        });
    }
}