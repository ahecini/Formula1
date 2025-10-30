package com.example.formula1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        holder.bind(pilote);
    }

    @Override
    public int getItemCount() {
        return pilotes.size();
    }

    public static class PodiumViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPosition;
        private final TextView textViewPiloteName;

        public PodiumViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPosition = itemView.findViewById(R.id.textViewActualPosition);
            textViewPiloteName = itemView.findViewById(R.id.textViewPiloteName);
        }

        public void bind(Pilote pilote) {
            textViewPosition.setText(String.valueOf(pilote.getPosition()));
            textViewPiloteName.setText(pilote.getNom());
        }
    }
}

