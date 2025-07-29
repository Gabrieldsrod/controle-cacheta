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
import com.gabrieldsrod.cacheta.service.ReportService;
import com.gabrieldsrod.cacheta.ui.ReportAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    public Context context;
    private ReportAdapter reportAdapter;
    private List<Player> playerList;
    private List<PlayerSummary> playerSummaries;
    private TextView txtDate, txtOccupiedTables, txtTotalRaised;
    private RecyclerView recycler;

    private PlayerService playerService;
    private ReportService reportService;

    private void init() {
        context = ReportActivity.this;
        AppDatabase database = AppDatabase.getInstance(context);

        txtDate = findViewById(R.id.txtDataHoje);
        txtOccupiedTables = findViewById(R.id.txtMesasOcupadas);
        txtTotalRaised = findViewById(R.id.txtTotalArrecadado);
        recycler = findViewById(R.id.reciclerPlayers);

        GameService gameService = new GameService(database.gameDao());
        GamePlayerService gamePlayerService = new GamePlayerService(database.gamePlayerDao());
        playerService = new PlayerService(database.playerDao());

        reportService = new ReportService(gameService, gamePlayerService);

        playerList = playerService.getAllPlayers();

        playerSummaries = new ArrayList<>();
        for (Player p : playerList) {
            PlayerSummary summary = reportService.playerReport(p);
            if (summary != null) {
                playerSummaries.add(summary);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();
        updateDailyReport();

        recycler.setLayoutManager(new LinearLayoutManager(context));
        reportAdapter = new ReportAdapter(context, playerSummaries);
        recycler.setAdapter(reportAdapter);
    }

    public void updateDailyReport() {
        EstablishmentSummary summary = reportService.EstablishmentReport();

        txtDate.setText("Data: " + summary.getDate());
        txtOccupiedTables.setText("Mesas ocupadas: " + summary.getOccupiedTables());
        txtTotalRaised.setText("Valor total arrecadado: R$ " + String.format("%.2f", summary.getTotalRaised()));
    }
}