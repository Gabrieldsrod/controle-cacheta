package com.gabrieldsrod.cacheta;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.db.AppDatabase;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.PlayerService;
import com.gabrieldsrod.cacheta.entities.service.TableService;
import com.gabrieldsrod.cacheta.service.MatchService;
import com.gabrieldsrod.cacheta.ui.TableAdapter;
import com.gabrieldsrod.cacheta.ui.TableInteractionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableActivity extends AppCompatActivity implements TableInteractionListener {

    private Context context;
    private TableAdapter adapter;
    private List<Table> tablesList;
    private Button btnAddMesa;
    private RecyclerView recycler;
    private TableService tableService;
    private PlayerService playerService;
    private MatchService matchService;

    private void init() {
        context = TableActivity.this;
        AppDatabase database = AppDatabase.getInstance(context);

        btnAddMesa = findViewById(R.id.btnAddMesa);
        recycler = findViewById(R.id.recyclerTable);

        GameService gameService = new GameService(database.gameDao());
        GamePlayerService gamePlayerService = new GamePlayerService(database.gamePlayerDao());
        tableService = new TableService(database.tableDao());
        playerService = new PlayerService(database.playerDao());

        matchService = new MatchService(tableService, gameService, gamePlayerService);

        tablesList = tableService.getALlTables();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        init();

        recycler.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TableAdapter(context, tablesList, this);
        recycler.setAdapter(adapter);

        btnAddMesa.setOnClickListener(v -> addTable());
    }

    private void addTable() {
        Table nova = new Table();
        nova.setStatus("Livre");
        tableService.createTable(nova);
        refreshTables();
    }

    private void refreshTables() {
        tablesList = tableService.getALlTables();
        adapter.updateTables(tablesList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.clearRunnables();
    }

    @Override
    public void onIniciarPartida(Table mesa) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        TextView customTitle = new TextView(context);
        customTitle.setText("Iniciar Partida - Mesa " + mesa.getTableNumber());
        customTitle.setPadding(60, 40, 40, 0);
        customTitle.setTextSize(24);
        customTitle.setTypeface(null, Typeface.BOLD);
        customTitle.setTextColor(ContextCompat.getColor(context, R.color.black)); // ou outra cor

        builder.setCustomTitle(customTitle);

        View playerNamesView = LayoutInflater.from(context).inflate(R.layout.box_iniciar_partida, null);
        builder.setView(playerNamesView);

        EditText nome1 = playerNamesView.findViewById(R.id.inputPlayer1);
        EditText nome2 = playerNamesView.findViewById(R.id.inputPlayer2);
        EditText nome3 = playerNamesView.findViewById(R.id.inputPlayer3);
        EditText nome4 = playerNamesView.findViewById(R.id.inputPlayer4);

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
            matchService.startGame(mesa);
            refreshTables();
        });

        builder.setNegativeButton("Cancelar", null);
        AlertDialog startGameDialog = builder.create();
        Objects.requireNonNull(startGameDialog.getWindow()).setBackgroundDrawableResource(R.drawable.container_rectangle);
        startGameDialog.show();

        Button positiveButton = startGameDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = startGameDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.verde_iniciar));
        negativeButton.setTextColor(ContextCompat.getColor(context, R.color.cinza_claro));
    }

    @Override
    public void onEncerrarPartida(Table mesa) {
        TextView customTitle = new TextView(context);
        customTitle.setText("Encerrar partida?");
        customTitle.setPadding(60, 40, 40, 0);
        customTitle.setTextSize(24);
        customTitle.setTypeface(null, Typeface.BOLD);
        customTitle.setTextColor(ContextCompat.getColor(context, R.color.black));

        AlertDialog endGameDialog = new AlertDialog.Builder(context)
                .setCustomTitle(customTitle)
                .setMessage("Deseja encerrar a partida da Mesa " + mesa.getTableNumber() + "?")
                .setPositiveButton("Encerrar", (dialog, which) -> {
                    try {
                        matchService.endGame(mesa);
                        refreshTables();
                    } catch (Exception e) {
                        Toast.makeText(context, "Erro ao encerrar partida: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();

        endGameDialog.show();

        Button positiveButton = endGameDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = endGameDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.vermelho_encerrar));
        negativeButton.setTextColor(ContextCompat.getColor(context, R.color.cinza_claro));
    }
}
