package com.example.reuniaodecoleta.TelaRequisito;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.R;
import com.example.reuniaodecoleta.TelaProjeto.EditarProjeto;
import com.example.reuniaodecoleta.TelaProjeto.ListagemProjeto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ListagemRequisito extends BaseActivity {
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;
    private ListView lista = null;
    private Cursor listarequisito = null;
    private TextView tv_nomeProjeto = null;
    private String codigoProjeto;
    private WebView myWebView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem_requisito);

        setUpToolbar(R.id.myToolbar);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        // Referências aos componentes do layout
        lista = findViewById(R.id.lv_lista);
        tv_nomeProjeto = findViewById(R.id.tv_nome_projeto);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        myWebView = (WebView) findViewById(R.id.mywebview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); // Carrega a URL dentro do WebView
                return true;
            }
        });



        // Obtém os dados do Intent
        codigoProjeto = this.getIntent().getStringExtra("id");
        String nome = this.getIntent().getStringExtra("nome");

        tv_nomeProjeto.setText(nome);

        // Consulta ao banco de dados
        String[] campos_item = {"id", "descricao", "data_hora_registro", "tipo", "nivel_importancia", "nivel_dificuldade", "horas_estimadas", "qntd_desenvolvedores", "projeto_id"};
        String whereClause = "projeto_id = ?";
        String[] whereArgs = {codigoProjeto};
        String orderBy = "tipo";
        listarequisito = bancoDeDados.query("requisito", campos_item, whereClause, whereArgs, null, null, orderBy);

        if (listarequisito.moveToFirst()) {
            ArrayList<String> list = new ArrayList<>();
            do {
                String descricao = listarequisito.getString(listarequisito.getColumnIndexOrThrow("descricao"));
                String tipo = listarequisito.getString(listarequisito.getColumnIndexOrThrow("tipo"));
                String horasEstimadas = listarequisito.getString(listarequisito.getColumnIndexOrThrow("horas_estimadas"));
                list.add("Descrição do Requisito: " + descricao + "\nTipo: " + tipo + "\nHoras estimadas: " + horasEstimadas);
            } while (listarequisito.moveToNext());

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            lista.setAdapter(arrayAdapter);
        }

        Button bt_editar_projeto = findViewById(R.id.bt_editar_projeto);
        bt_editar_projeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListagemRequisito.this, EditarProjeto.class);
                intent.putExtra("id", codigoProjeto);
                intent.putExtra("nome", nome);
                startActivity(intent);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String codigo;
                String projeto_id;
                listarequisito.moveToPosition(position);
                codigo = listarequisito.getString(listarequisito.getColumnIndexOrThrow("id"));
                projeto_id = listarequisito.getString(listarequisito.getColumnIndexOrThrow("projeto_id"));
                Intent intent = new Intent(ListagemRequisito.this, EditarRequisito.class);
                intent.putExtra("id", codigo);
                intent.putExtra("projeto_id", projeto_id);
                startActivity(intent);
                finish();
            }
        });

        Button btBotaoUrl = (Button) findViewById(R.id.bt_link);
        btBotaoUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://drive.google.com/drive/folders/1wIzRfZmhWQESMghhQ93HGTMeW-Z043S-?usp=sharing";
                myWebView.loadUrl(url);
            }
        });
    }
}