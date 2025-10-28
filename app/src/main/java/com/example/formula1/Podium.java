package com.example.formula1;

import android.os.Bundle;

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
import java.util.concurrent.Executors;

public class Podium extends AppCompatActivity {

    private RecyclerView recyclerViewPodium;
    private PodiumAdapter podiumAdapter;
    private List<Pilote> piloteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_podium);

        recyclerViewPodium = findViewById(R.id.recyclerViewPodium);
        podiumAdapter = new PodiumAdapter(piloteList);

        recyclerViewPodium.setLayoutManager((new LinearLayoutManager(this)));
        recyclerViewPodium.setAdapter(podiumAdapter);

        loadData();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void loadData(){
        Executors.newSingleThreadExecutor().execute(()->{
            AppDataBase db = AppDataBase.getInstance(getApplicationContext());
            List<Pilote> results = db.piloteDAO().getAllPilotes();

            Collections.sort(results, Comparator.comparingLong(Pilote::getTemps));

            runOnUiThread(()->{
                piloteList.clear();
                piloteList.addAll(results);
                podiumAdapter.notifyDataSetChanged();
            });
        });
    }
}