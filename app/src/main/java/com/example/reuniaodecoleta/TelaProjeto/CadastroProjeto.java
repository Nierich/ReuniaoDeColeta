package com.example.reuniaodecoleta.TelaProjeto;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.Entidade.Projeto;
import com.example.reuniaodecoleta.R;

public class CadastroProjeto extends BaseActivity {
    private EditText etNome = null;
    private TextView dataInicio = null;
    private TextView dataFim = null;
    private SQLiteDatabase bancoDeDados;
    private DatabaseManager dataBaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_projeto);

        setUpToolbar(R.id.myToolbar);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();



        etNome = findViewById(R.id.et_nome_projeto);
        dataInicio = findViewById(R.id.tv_data_inicio);
        dataFim = findViewById(R.id.tv_data_fim);

        Button criar = findViewById(R.id.bt_alterar);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeProjeto = etNome.getText().toString();
                String data_inicio = dataInicio.getText().toString();
                String data_fim = dataFim.getText().toString();

                if (nomeProjeto.isEmpty() || data_inicio.isEmpty() || data_fim.isEmpty()) {
                    Toast.makeText(CadastroProjeto.this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    Projeto projeto = new Projeto();
                    projeto.setNome(nomeProjeto);
                    projeto.setData_inicio(data_inicio);
                    projeto.setData_fim(data_fim);

                    salvarProjetoNoBanco(projeto);
                }
            }
        });

        Button bt_dataInicio = findViewById(R.id.bt_data_inicio);
        bt_dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroProjeto.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String formattedDate = String.format("%02d/%02d/%04d", day, month + 1, year);
                        dataInicio.setText(formattedDate);
                    }
                }, 2024, 5, 5);
                datePickerDialog.show();
            }
        });

        Button bt_dataFim = findViewById(R.id.bt_data_fim);
        bt_dataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroProjeto.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String formattedDate = String.format("%02d/%02d/%04d", day, month + 1, year);
                        dataFim.setText(formattedDate);
                    }
                }, 2024, 5, 5);
                datePickerDialog.show();
            }
        });
    }

    private void salvarProjetoNoBanco(Projeto projeto) {
        ContentValues valores = new ContentValues();
        valores.put("nome", projeto.getNome());
        valores.put("data_inicio", projeto.getData_inicio());
        valores.put("data_fim", projeto.getData_fim());

        long resultado = bancoDeDados.insert("projeto", null, valores);
        bancoDeDados.close();

        if (resultado != -1) {
            Toast.makeText(CadastroProjeto.this, "Projeto criado com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CadastroProjeto.this, "Erro ao criar projeto!", Toast.LENGTH_LONG).show();
        }
    }
}