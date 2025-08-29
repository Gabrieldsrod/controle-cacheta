package com.gabrieldsrod.cacheta;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.db.AppDatabase;
import com.gabrieldsrod.cacheta.dto.EstablishmentSummary;
import com.gabrieldsrod.cacheta.dto.PlayerSummary;
import com.gabrieldsrod.cacheta.entities.Player;
import com.gabrieldsrod.cacheta.entities.service.GamePlayerService;
import com.gabrieldsrod.cacheta.entities.service.GameService;
import com.gabrieldsrod.cacheta.entities.service.PlayerService;
import com.gabrieldsrod.cacheta.entities.service.TableService;
import com.gabrieldsrod.cacheta.service.ReportService;
import com.gabrieldsrod.cacheta.ui.ReportAdapter;
import com.gabrieldsrod.cacheta.ui.TableReportAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    public Context context;
    private ReportAdapter reportAdapter;
    private TableReportAdapter tableReportAdapter;
    private List<Player> playerList;
    private List<PlayerSummary> playerSummaries;
    private TextView txtDate, txtOccupiedTables, txtTotalRaised;
    private RecyclerView recyclerPlayers, recyclerTables;

    private PlayerService playerService;
    private ReportService reportService;

    private void init() {
        context = ReportActivity.this;
        AppDatabase database = AppDatabase.getInstance(context);

        txtDate = findViewById(R.id.txtDataHoje);
        txtOccupiedTables = findViewById(R.id.txtMesasOcupadas);
        txtTotalRaised = findViewById(R.id.txtTotalArrecadado);
        recyclerPlayers = findViewById(R.id.reciclerPlayers);
        recyclerTables  = findViewById(R.id.recyclerRelatorioMesas);

        // 1) SEMPRE primeiro os LayoutManagers
        recyclerPlayers.setLayoutManager(new LinearLayoutManager(context));
        recyclerPlayers.setHasFixedSize(true); // opcional
        recyclerTables.setLayoutManager(new LinearLayoutManager(context));
        recyclerTables.setHasFixedSize(true);  // opcional

        // 2) Adapters com lista vazia (evita layout sem adapter)
        reportAdapter = new ReportAdapter(context, new ArrayList<>());
        recyclerPlayers.setAdapter(reportAdapter);

        tableReportAdapter = new TableReportAdapter(context, new ArrayList<>());
        recyclerTables.setAdapter(tableReportAdapter);

        GameService gameService = new GameService(database.gameDao());
        GamePlayerService gamePlayerService = new GamePlayerService(database.gamePlayerDao());
        TableService tableService = new TableService(database.tableDao(), gameService);
        playerService = new PlayerService(database.playerDao());
        reportService = new ReportService(playerService, gameService, gamePlayerService, tableService);

        playerList = playerService.getAllPlayers();
        playerSummaries = new ArrayList<>();
        for (Player p : playerList) {
            PlayerSummary summary = reportService.playerReport(p);
            if (summary != null) playerSummaries.add(summary);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();
        updateDailyReport();
    }

    public void updateDailyReport() {
        EstablishmentSummary summary = reportService.EstablishmentReport();

        txtDate.setText("Data: " + summary.getDate());
        txtOccupiedTables.setText("Mesas ocupadas: " + summary.getOccupiedTables());
        txtTotalRaised.setText("Valor total arrecadado: R$ " + String.format(Locale.getDefault(), "%.2f", summary.getTotalRaised()));

        // Atualiza listas dos adapters já anexados
        reportAdapter.submit(playerSummaries);
        tableReportAdapter.submit(summary.getTableSummaries());
    }
}