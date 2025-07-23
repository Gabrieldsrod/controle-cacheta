package com.gabrieldsrod.cacheta.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.R;
import com.gabrieldsrod.cacheta.db.AppDatabase;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.PlayerService;
import com.gabrieldsrod.cacheta.entities.service.TableService;
import com.gabrieldsrod.cacheta.service.MatchService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private static final int PRICE_PER_HOUR = 10;
    private final List<Table> tables;
    private final Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Map<Integer, Runnable> runnableMap = new HashMap<>();

    private final PlayerService playerService;
    private final MatchService matchService;

    private final Runnable refreshCallback;

    public TableAdapter(Context context, List<Table> tables, AppDatabase db, Runnable refreshCallback) {
        this.context = context;
        this.tables = new ArrayList<>(tables);
        this.refreshCallback = refreshCallback;

        GameService gameService = new GameService(db.gameDao());
        TableService tableService = new TableService(db.tableDao());
        GamePlayerService gamePlayerService = new GamePlayerService(db.gamePlayerDao());
        this.playerService = new PlayerService(db.playerDao());

        this.matchService = new MatchService(tableService, gameService, gamePlayerService);
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
            Table mesa = tables.get(position);

            if (mesa.getStatus().equals("Livre")) {
                abrirDialogIniciarPartida(mesa);
            } else {
                abrirDialogEncerrarPartida(mesa);
            }
        });

        if ("Ocupada".equalsIgnoreCase(table.getStatus())) {
            holder.txtStatusMesa.setBackgroundResource(R.drawable.bg_status_ocupado);
            holder.txtCronometro.setVisibility(View.VISIBLE);
            holder.txtValorMesa.setVisibility(View.VISIBLE);

            if (runnableMap.containsKey(tableNumber)) {
                handler.removeCallbacks(runnableMap.get(tableNumber));
            }

            Runnable updateRunnable = new Runnable() {
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
                        double valor = roundedHours * PRICE_PER_HOUR * 4;
                        holder.txtValorMesa.setText("R$ " + String.format("%.2f", valor));
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
                handler.removeCallbacks(runnableMap.get(tableNumber));
                runnableMap.remove(tableNumber);
            }
        }
    }

    public void updateTables(List<Table> newTables) {
        tables.clear();
        tables.addAll(newTables);
        notifyDataSetChanged();
    }

    private void abrirDialogIniciarPartida(Table mesa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Iniciar Partida - Mesa " + mesa.getTableNumber());

        View dialogView = LayoutInflater.from(context).inflate(R.layout.box_iniciar_partida, null);
        builder.setView(dialogView);

        EditText nome1 = dialogView.findViewById(R.id.inputPlayer1);
        EditText nome2 = dialogView.findViewById(R.id.inputPlayer2);
        EditText nome3 = dialogView.findViewById(R.id.inputPlayer3);
        EditText nome4 = dialogView.findViewById(R.id.inputPlayer4);

        builder.setPositiveButton("Iniciar", (dialog, which) -> {
            List<String> nomes = Arrays.asList(
                    nome1.getText().toString().trim(),
                    nome2.getText().toString().trim(),
                    nome3.getText().toString().trim(),
                    nome4.getText().toString().trim()
            );

            if (nomes.stream().anyMatch(String::isEmpty)) {
                Toast.makeText(context, "Preencha todos os nomes!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buscar ou criar os jogadores
            List<Player> jogadores = new ArrayList<>();
            for (String nome : nomes) {
                Player player = playerService.findByName(nome);
                if (player == null) {
                    player = new Player(nome);
                    long id = playerService.createPlayer(player);
                    player.setId((int) id);
                }
                jogadores.add(player);
            }

            mesa.setPlayers(jogadores);

            matchService.startGame(mesa.getTableNumber());
            refreshCallback.run();

            notifyItemChanged(tables.indexOf(mesa));
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void abrirDialogEncerrarPartida(Table mesa) {
        new AlertDialog.Builder(context)
                .setTitle("Encerrar partida?")
                .setMessage("Deseja encerrar a partida da Mesa " + mesa.getTableNumber() + "?")
                .setPositiveButton("Encerrar", (dialog, which) -> {
                    try {
                        matchService.endGame(mesa.getTableNumber());
                        refreshCallback.run();
                        notifyItemChanged(tables.indexOf(mesa));
                    } catch (Exception e) {
                        Toast.makeText(context, "Erro ao encerrar partida: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    @Override
    public int getItemCount() {
        return tables.size();
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

    public void clearRunnables() {
        for (Runnable r : runnableMap.values()) {
            handler.removeCallbacks(r);
        }
        runnableMap.clear();
    }
}


