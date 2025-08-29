package com.gabrieldsrod.cacheta.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.R;
import com.gabrieldsrod.cacheta.dto.PlayerSummary;
import com.gabrieldsrod.cacheta.dto.TableSummary;
import com.gabrieldsrod.cacheta.entities.Player;

import java.util.List;
import java.util.Locale;

public class TableReportAdapter extends RecyclerView.Adapter<TableReportAdapter.TableReportViewHolder> {

    private final Context context;
    private final List<TableSummary> tableSummaries;

    public TableReportAdapter(Context context, List<TableSummary> tableSummaries) {
        this.context = context;
        this.tableSummaries = tableSummaries;
    }

    @NonNull
    @Override
    public TableReportAdapter.TableReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_report_item, parent, false);
        return new TableReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableReportViewHolder holder, int position) {
        TableSummary tableSummary = tableSummaries.get(position);

        holder.txtTableTitle.setText("Mesa " + tableSummary.getTableNumber());
        holder.txtRaisedByTable.setText("Arrecadado: R$" + String.format("%.2f", tableSummary.getTotalRaised()));

        Player top = tableSummary.getTopPlayer();
        if (top == null) {
            holder.txtTopPlayer.setText("Jogador com mais tempo: —");
        } else {
            int minutes = tableSummary.getTopPlayerMinutesPlayed();
            holder.txtTopPlayer.setText("Jogador com mais tempo: "
                    + top.getName() + " - " + formatMinutes(minutes));
        }
    }

    private String formatMinutes(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;

        if (h > 0) {
            return String.format(Locale.getDefault(), "%dh%02dmin", h, m);
        } else {
            return String.format(Locale.getDefault(), "%dmin", m);
        }
    }

    @Override
    public int getItemCount() {
        return tableSummaries.size();
    }

    public static class TableReportViewHolder extends RecyclerView.ViewHolder {
        TextView txtTableTitle, txtRaisedByTable, txtTopPlayer;

        public TableReportViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTableTitle = itemView.findViewById(R.id.txtTitleMesa);
            txtRaisedByTable = itemView.findViewById(R.id.txtMesaArrecadado);
            txtTopPlayer = itemView.findViewById(R.id.txtTopPlayer);
        }
    }

    public void submit(List<TableSummary> list) {
        this.tableSummaries.clear();
        this.tableSummaries.addAll(list);
        notifyDataSetChanged();
    }
}
