package com.gabrieldsrod.cacheta.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.R;

import com.gabrieldsrod.cacheta.entities.Table;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private static final int PRICE_PER_HOUR = 10;

    private final List<Table> tables;
    private final Context context;
    private final TableInteractionListener listener;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Map<Integer, Runnable> runnableMap = new HashMap<>();

    public TableAdapter(Context context, List<Table> tables, TableInteractionListener listener) {
        this.context = context;
        this.tables = new ArrayList<>(tables);
        this.listener = listener;
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        TextView txtNumeroMesa, txtStatusMesa, txtCronometro, txtValorMesa;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNumeroMesa = itemView.findViewById(R.id.txtMesaNumero);
            txtStatusMesa = itemView.findViewById(R.id.txtStatusMesa);
            txtCronometro = itemView.findViewById(R.id.txtCronometro);
            txtValorMesa = itemView.findViewById(R.id.txtValorMesa);
        }
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mesa_item, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        Table table = tables.get(position);
        int tableNumber = table.getTableNumber();

        holder.txtNumeroMesa.setText("Mesa " + tableNumber);
        holder.txtStatusMesa.setText(table.getStatus());

        holder.itemView.setOnClickListener(v -> {
            if ("Livre".equalsIgnoreCase(table.getStatus())) {
                listener.onIniciarPartida(table);
            } else {
                listener.onEncerrarPartida(table);
            }
        });

        if ("Ocupada".equalsIgnoreCase(table.getStatus())) {
            holder.txtStatusMesa.setBackgroundResource(R.drawable.bg_status_ocupado);
            holder.txtCronometro.setVisibility(View.VISIBLE);
            holder.txtValorMesa.setVisibility(View.VISIBLE);

            if (runnableMap.containsKey(tableNumber)) {
                handler.removeCallbacks(Objects.requireNonNull(runnableMap.get(tableNumber)));
            }

            Runnable updateRunnable = new Runnable() {
                long lastNotifiedHour = 0;

                @Override
                public void run() {
                    if (table.getStartTime() != null) {
                        Duration duration = Duration.between(table.getStartTime(), LocalDateTime.now());
                        long totalSeconds = duration.getSeconds();
                        long hours = totalSeconds / 3600;
                        long minutes = (totalSeconds % 3600) / 60;
                        long seconds = totalSeconds % 60;

                        holder.txtCronometro.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

                        int roundedHours = Math.max(1, (int) Math.ceil(totalSeconds / 3600.0));
                        if (roundedHours != lastNotifiedHour) {
                            double valor = roundedHours * PRICE_PER_HOUR * 4;
                            holder.txtValorMesa.setText("R$ " + String.format("%.2f", valor));

                            Toast.makeText(context, "🔔 Mesa " + table.getTableNumber() + " completou " + roundedHours + " hora(s)!", Toast.LENGTH_SHORT).show();

                            lastNotifiedHour = roundedHours;
                        }
                    }

                    handler.postDelayed(this, 1000);
                }
            };

            runnableMap.put(tableNumber, updateRunnable);
            handler.post(updateRunnable);
        } else {
            holder.txtStatusMesa.setBackgroundResource(R.drawable.bg_status_livre);
            holder.txtCronometro.setVisibility(View.GONE);
            holder.txtValorMesa.setVisibility(View.GONE);

            if (runnableMap.containsKey(tableNumber)) {
                handler.removeCallbacks(Objects.requireNonNull(runnableMap.get(tableNumber)));
                runnableMap.remove(tableNumber);
            }
        }
    }

    public void updateTables(List<Table> newTables) {
        tables.clear();
        tables.addAll(newTables);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public void clearRunnables() {
        for (Runnable r : runnableMap.values()) {
            handler.removeCallbacks(r);
        }
        runnableMap.clear();
    }
}