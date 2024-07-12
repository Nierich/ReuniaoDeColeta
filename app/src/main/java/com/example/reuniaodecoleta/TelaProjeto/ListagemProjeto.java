package com.example.reuniaodecoleta.TelaProjeto;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.R;
import com.example.reuniaodecoleta.TelaRequisito.ListagemRequisito;

import java.util.ArrayList;

public class ListagemProjeto extends BaseActivity {
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;
    private ListView lista = null;
    private Cursor listaprojeto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_projeto);

        setUpToolbar(R.id.myToolbar);

        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        lista = (ListView) findViewById(R.id.lv_lista);

        String[] campos_item = {"id", "nome", "data_inicio", "data_fim"};
        listaprojeto = bancoDeDados.query("projeto", campos_item, null, null, null, null, null);

        listaprojeto.moveToFirst();

        ArrayList list = new ArrayList();
        for (int i = 0; i < listaprojeto.getCount(); i++) {
            list.add("Nome do Projeto: " + listaprojeto.getString(listaprojeto.getColumnIndexOrThrow("nome")) +
                    "\nData de Inicio: " + listaprojeto.getString(listaprojeto.getColumnIndexOrThrow("data_inicio")) +
                    "\nData de Fim: " + listaprojeto.getString(listaprojeto.getColumnIndexOrThrow("data_fim")));
            listaprojeto.moveToNext();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lista.setAdapter(arrayAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                String nomeProjeto;
                listaprojeto.moveToPosition(position);
                codigo = listaprojeto.getString(listaprojeto.getColumnIndexOrThrow("id"));
                nomeProjeto = listaprojeto.getString(listaprojeto.getColumnIndexOrThrow("nome"));
                Intent intent = new Intent(ListagemProjeto.this, ListagemRequisito.class);
                intent.putExtra("id", codigo);
                intent.putExtra("nome", nomeProjeto);
                startActivity(intent);
                finish();
            }
        });
        bancoDeDados.close();
    }

}
