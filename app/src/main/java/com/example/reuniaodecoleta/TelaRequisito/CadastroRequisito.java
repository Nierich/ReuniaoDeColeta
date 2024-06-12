package com.example.reuniaodecoleta.TelaRequisito;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.Entidade.Projeto;
import com.example.reuniaodecoleta.Entidade.Requisito;
import com.example.reuniaodecoleta.R;
import com.example.reuniaodecoleta.TelaProjeto.CadastroProjeto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastroRequisito extends BaseActivity {

    private EditText etDescricao = null;
    private Spinner spn_tipo = null;
    private Spinner spn_importancia = null;
    private Spinner spn_dificuldade = null;
    private EditText et_horas = null;
    private Spinner spn_desenvolvedores = null;
    private Spinner spn_projeto = null;
    private SQLiteDatabase bancoDeDados;
    private DatabaseManager dataBaseManager;
    private List<Projeto> projetoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_requisito);

        setUpToolbar(R.id.myToolbar);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 1);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        etDescricao = (EditText) findViewById(R.id.et_descricao_requisito);
        et_horas = (EditText) findViewById(R.id.et_horas_estimadas);
        spn_tipo = (Spinner) findViewById(R.id.spn_tipo_requisito);
        spn_importancia = (Spinner) findViewById(R.id.spn_importancia_requisito);
        spn_dificuldade = (Spinner) findViewById(R.id.spn_dificuldade_requisito);
        spn_desenvolvedores = (Spinner) findViewById(R.id.spn_desenvolvedores_requisito);
        spn_projeto = (Spinner) findViewById(R.id.spn_projeto);

        configurarSpinner();

        Button bt_criar_requisito = findViewById(R.id.bt_criar_requisito);
        bt_criar_requisito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = etDescricao.getText().toString();
                String tipo = spn_tipo.getSelectedItem().toString();
                String importancia = spn_importancia.getSelectedItem().toString();
                String dificuldade = spn_dificuldade.getSelectedItem().toString();
                String horas = et_horas.getText().toString();
                String desenvolvedores = spn_desenvolvedores.getSelectedItem().toString();

                if (descricao.isEmpty() || horas.isEmpty()) {
                    Toast.makeText(CadastroRequisito.this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateAndTime = sdf.format(new Date());


                    Requisito requisito = new Requisito();
                    requisito.setData_hora_registro(currentDateAndTime);
                    requisito.setDescricao(descricao);
                    requisito.setTipo(tipo);
                    requisito.setNivel_importancia(importancia);
                    requisito.setNivel_dificuldade(dificuldade);
                    requisito.setHoras_estimadas(horas);
                    requisito.setQntd_desenvolvedores(desenvolvedores);


                    salvarRequisitoNoBanco(requisito);
                }
            }
        });
    }
    private void salvarRequisitoNoBanco(Requisito requisito) {
        ContentValues valores = criarContentValuesParaRequisito(requisito);

        long resultado = bancoDeDados.insert("requisito", null, valores);


        if (resultado != -1) {
            Toast.makeText(CadastroRequisito.this, "Requisito criado com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CadastroRequisito.this, "Erro ao criar requisito!", Toast.LENGTH_LONG).show();
        }
    }

    private ContentValues criarContentValuesParaRequisito(Requisito requisito) {
        ContentValues valores = new ContentValues();
        valores.put("data_hora_registro", requisito.getData_hora_registro());
        valores.put("descricao", requisito.getDescricao());
        valores.put("tipo", requisito.getTipo());
        valores.put("nivel_importancia", requisito.getNivel_importancia());
        valores.put("nivel_dificuldade", requisito.getNivel_dificuldade());
        valores.put("horas_estimadas", requisito.getHoras_estimadas());
        valores.put("qntd_desenvolvedores", requisito.getQntd_desenvolvedores());
        // Insere o projeto selecionado pelo usuário
        Projeto projetoSelecionado = (Projeto) spn_projeto.getSelectedItem();
        valores.put("projeto_id", projetoSelecionado.getId());
        return valores;
    }

    public Cursor fazerConsulta() {
        SQLiteDatabase db = dataBaseManager.getReadableDatabase();
        return db.rawQuery("SELECT * FROM projeto ORDER BY id", null);
    }

    // Método para configurar o Spinner e carregar as categorias
    private void configurarSpinner() {
        // Consulta as categorias do banco de dados
        Cursor cursor = fazerConsulta();

        // Cria uma lista de categorias
        projetoList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nomeIndex = cursor.getColumnIndex("nome");

            if (idIndex != -1 && nomeIndex != -1) {
                do {
                    int id = cursor.getInt(idIndex);
                    String nome = cursor.getString(nomeIndex);
                    Projeto projeto = new Projeto(id, nome);
                    projetoList.add(projeto);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        // Cria um ArrayAdapter para o Spinner
        ArrayAdapter<Projeto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projetoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_projeto.setAdapter(adapter);
    }
}
