package com.example.formula1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class PodiumAdapter extends RecyclerView.Adapter<PodiumAdapter.PodiumViewHolder> {

    private final List<Pilote> pilotes;

    public PodiumAdapter(List<Pilote> pilotes) {
        this.pilotes = pilotes;
    }

    @NonNull
    @Override
    public PodiumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.podium_item, parent, false);
        return new PodiumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodiumViewHolder holder, int position) {
        Pilote pilote = pilotes.get(position);
        holder.bind(pilote, position + 1);
    }

    @Override
    public int getItemCount() {
        return pilotes.size();
    }

    public static class PodiumViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPosition;
        private final TextView textViewPiloteName;
        private final TextView textViewPiloteTime;

        public PodiumViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPosition = itemView.findViewById(R.id.textViewActualTime);
            textViewPiloteName = itemView.findViewById(R.id.textViewPiloteName);
            textViewPiloteTime = itemView.findViewById(R.id.textViewPiloteTime);
        }

        public void bind(Pilote pilote, int position) {
            textViewPosition.setText(String.valueOf(position));
            textViewPiloteName.setText(pilote.getNom());

            textViewPiloteTime.setText(formatTime(pilote.getTemps()));
        }

        private String formatTime(long millis) {
            long minutes = (millis / 1000) / 60;
            long seconds = (millis / 1000) % 60;
            long milliseconds = millis % 1000;
            return String.format(Locale.getDefault(), "%02d:%02d.%03d", minutes, seconds, milliseconds);
        }
    }
}

