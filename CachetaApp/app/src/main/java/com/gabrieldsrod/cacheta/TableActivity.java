package com.gabrieldsrod.cacheta;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrieldsrod.cacheta.db.AppDatabase;
import com.gabrieldsrod.cacheta.entities.Table;
import com.gabrieldsrod.cacheta.entities.service.TableService;
import com.gabrieldsrod.cacheta.ui.TableAdapter;

import java.util.List;

public class TableActivity extends AppCompatActivity {

    private Context context;
    private TableAdapter adapter;
    private List<Table> tablesList;
    private Button btnAddMesa;
    private RecyclerView recycler;
    private TableService tableService;
    private AppDatabase database;

    private void init() {
        context = TableActivity.this;
        database = AppDatabase.getInstance(context);

        btnAddMesa = findViewById(R.id.btnAddMesa);
        recycler = findViewById(R.id.recyclerTable);

        tableService = new TableService(database.tableDao());
        tablesList = tableService.getALlTables();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        init();

        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TableAdapter(context, tablesList, database, this::refreshTables);
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
}