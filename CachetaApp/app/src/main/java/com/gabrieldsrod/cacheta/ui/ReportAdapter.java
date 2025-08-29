package com.gabrieldsrod.cacheta.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.R;
import com.gabrieldsrod.cacheta.dto.MatchSummary;
import com.gabrieldsrod.cacheta.dto.PlayerSummary;
import com.gabrieldsrod.cacheta.dto.TableSummary;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.PlayerReportViewHolder> {
    private final List<PlayerSummary> playerSummaries;
    private final Context context;

    public ReportAdapter(Context context, List<PlayerSummary> playerSummaries) {
        this.context = context;
        this.playerSummaries = playerSummaries;
    }

    @NonNull
    @Override
    public PlayerReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player_report_item, parent, false);
        return new PlayerReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerReportViewHolder holder, int position) {
        PlayerSummary summary = playerSummaries.get(position);

        holder.txtName.setText("Jogador: " + summary.getPlayerName());
        holder.txtDate.setText("Data: " + summary.getDate());
        holder.txtTotal.setText("Total a pagar : R$ " + String.format("%.2f", summary.getTotalToPay()));

        StringBuilder partidas = new StringBuilder();
        for (MatchSummary match : summary.getMatches()) {
            partidas.append("Mesa ")
                    .append(match.getTableId())
                    .append("| ")
                    .append(match.getTime())
                    .append(" (")
                    .append(match.getDurationTime())
                    .append(") => R$ ")
                    .append(String.format("%.2f", match.getValueToPay()))
                    .append("\n");
        }
        holder.txtMatches.setText((partidas.toString().trim()));
    }

    @Override
    public int getItemCount() {
        return playerSummaries.size();
    }

    public static class PlayerReportViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDate, txtTotal, txtMatches;
        public PlayerReportViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtNomeJogador);
            txtDate = itemView.findViewById(R.id.txtData);
            txtTotal = itemView.findViewById(R.id.txtTotalPagar);
            txtMatches = itemView.findViewById(R.id.txtPartidas);
        }
    }

    public void submit(List<PlayerSummary> list) {
        this.playerSummaries.clear();
        this.playerSummaries.addAll(list);
        notifyDataSetChanged();
    }
}

