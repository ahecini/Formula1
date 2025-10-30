package com.example.formula1;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

public class Podium extends AppCompatActivity {

    private RecyclerView recyclerViewPodium;
    private PodiumAdapter podiumAdapter;
    private TextView textViewPenalty;
    private String[] nameList; // Tableau pour charger les noms des pilotes depuis les ressources
    private List<Pilote> pilotList = new ArrayList<>(); // La liste utilisée par l'adapter, correctement typée

    private int piloteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_podium);

        textViewPenalty = findViewById(R.id.textViewPenalty);
        recyclerViewPodium = findViewById(R.id.recyclerViewPodium);

        // Initialisation de l'adapter avec la liste qui sera mise à jour
        podiumAdapter = new PodiumAdapter(pilotList);
        recyclerViewPodium.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPodium.setAdapter(podiumAdapter);

        // Chargement de la liste de noms et de l'ID du pilote
        nameList = getResources().getStringArray(R.array.names);
        piloteId = getIntent().getIntExtra("piloteId", -1);

        if (piloteId == -1) {
            Log.e("Podium", "Aucun piloteId n'a été passé à l'activité.");
            finish(); // On quitte si l'ID est invalide
            return;
        }

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

            // --- AMÉLIORATION 1 : Un seul appel à la base de données ---
            PiloteEtVoiture data = db.piloteDAO().getPiloteAvecVoitureById(piloteId);

            // Protection robuste
            if (data == null || data.pilote == null || data.voitureAvecPiece == null) {
                Log.e("Podium", "Données complètes introuvables pour le pilote ID: " + piloteId);
                return;
            }

            Pilote pilotePj = data.pilote;
            int positionPj = pilotePj.getPosition();

            // --- Traitement des pénalités ---
            int nbPieceIllegal = 0;
            int placesDePenalite = 0;

            if (data.voitureAvecPiece.moteur.isIllegal()) nbPieceIllegal++;
            if (data.voitureAvecPiece.frein.isIllegal()) nbPieceIllegal++;
            if (data.voitureAvecPiece.suspension.isIllegal()) nbPieceIllegal++;
            if (data.voitureAvecPiece.boite.isIllegal()) nbPieceIllegal++;

            if (nbPieceIllegal > 0) {
                Random random = new Random();
                for (int i = 0; i < nbPieceIllegal; i++) {
                    if (random.nextInt(4) == 0) { // 1 chance sur 4 par pièce
                        placesDePenalite++;
                    }
                }
            }

            // Appliquer la pénalité si nécessaire
            if (placesDePenalite > 0) {
                int nouvellePosition = pilotePj.getPosition() + placesDePenalite;
                if (nouvellePosition > 20) {
                    nouvellePosition = 20; // Limiter à la dernière place
                }
                db.piloteDAO().updatePositionById(piloteId, nouvellePosition);
                // Mettre à jour l'objet local pour la suite
                pilotePj.setPosition(nouvellePosition);
                positionPj = nouvellePosition;
            }

            // --- CRÉATION DU CLASSEMENT ET DES PILOTES IA ---

            // --- AMÉLIORATION 2 : Utiliser la bonne liste de noms ---
            List<String> nomsConcurrents = new ArrayList<>(Arrays.asList(nameList));
            nomsConcurrents.remove(pilotePj.getNom()); // On enlève le nom du joueur
            Collections.shuffle(nomsConcurrents);     // On mélange les noms restants

            List<Pilote> classement = new ArrayList<>();

            for (int i = 1; i <= 20; i++) { // Boucle de 1 à 20 pour les positions
                if (i == positionPj) {
                    // C'est la place du joueur, on l'ajoute
                    classement.add(pilotePj);
                } else {
                    // C'est la place d'une IA
                    if (!nomsConcurrents.isEmpty()) {
                        String iaName = nomsConcurrents.remove(0); // On prend un nom et on le supprime
                        Pilote ia = new Pilote(iaName, 1, 1, 1, 1); // Création de l'IA
                        ia.setPosition(i); // On lui assigne sa position
                        classement.add(ia);
                    }
                }
            }

            // --- AMÉLIORATION 3 : Utiliser le bon comparateur ---
            Collections.sort(classement, Comparator.comparingInt(Pilote::getPosition));

            // On passe les variables finales au thread UI
            final int finalPlacesDePenalite = placesDePenalite;
            final int finalNbPieceIllegal = nbPieceIllegal;

            runOnUiThread(()->{
                if(finalPlacesDePenalite > 0) {
                    textViewPenalty.setText("Pénalité : +" + finalPlacesDePenalite + " place(s) (" + finalNbPieceIllegal + " pièces illégales détectées)");
                    textViewPenalty.setVisibility(View.VISIBLE);
                }

                // Mise à jour de la liste de l'adapter
                pilotList.clear();
                pilotList.addAll(classement);
                podiumAdapter.notifyDataSetChanged();
            });
        });
    }
}
