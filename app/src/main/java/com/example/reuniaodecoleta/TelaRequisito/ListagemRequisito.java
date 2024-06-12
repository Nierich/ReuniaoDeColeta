package com.example.reuniaodecoleta.TelaRequisito;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.R;

import java.util.ArrayList;

public class ListagemRequisito extends BaseActivity {
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;
    private ListView lista = null;
    private Cursor listarequisito = null;
    private TextView tv_nomeProjeto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_requisito);

        setUpToolbar(R.id.myToolbar);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 1);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        // Referências aos componentes do layout
        lista = findViewById(R.id.lv_lista);
        tv_nomeProjeto = findViewById(R.id.tv_nome_projeto);

        String codigo = this.getIntent().getStringExtra("id");
        String nome = this.getIntent().getStringExtra("nome");
        tv_nomeProjeto.setText(nome);

        String[] campos_item = {"id", "descricao", "data_hora_registro", "tipo", "nivel_importancia", "nivel_dificuldade", "horas_estimadas", "qntd_desenvolvedores", "projeto_id"};
        String whereClause = "projeto_id = ?";
        String[] whereArgs = {codigo};
        listarequisito = bancoDeDados.query("requisito", campos_item, whereClause, whereArgs, null, null, null);

            listarequisito.moveToFirst();

            ArrayList list = new ArrayList();
            for (int i = 0; i < listarequisito.getCount(); i++) {
                list.add("Descrição do Requisito: " + listarequisito.getString(listarequisito.getColumnIndexOrThrow("descricao")) +
                        " \nTipo: " + listarequisito.getString(listarequisito.getColumnIndexOrThrow("tipo")) +
                        " \nHoras estimadas: " + listarequisito.getString(listarequisito.getColumnIndexOrThrow("horas_estimadas")));
                listarequisito.moveToNext();
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            lista.setAdapter(arrayAdapter);

        bancoDeDados.close();
    }
}
