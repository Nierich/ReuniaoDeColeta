package com.example.reuniaodecoleta.TelaProjeto;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.R;

public class EditarProjeto extends BaseActivity {
    private DatabaseManager dataBaseManager = null;
    private SQLiteDatabase bancoDeDados = null;
    private EditText et_nome_projeto = null;
    private TextView dataInicio = null;
    private TextView dataFim = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_projeto);

        setUpToolbar(R.id.myToolbar);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        et_nome_projeto = (EditText) findViewById(R.id.et_nome_projeto);
        dataInicio = findViewById(R.id.tv_data_inicio);
        dataFim = findViewById(R.id.tv_data_fim);

        String codigo = this.getIntent().getStringExtra("id");
        Cursor cursor = carregaDadoById(Integer.parseInt(codigo));

        et_nome_projeto.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        dataInicio.setText(cursor.getString(cursor.getColumnIndexOrThrow("data_inicio")));
        dataFim.setText(cursor.getString(cursor.getColumnIndexOrThrow("data_fim")));

        Button bt_alterar = findViewById(R.id.bt_alterar);
        bt_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bancoDeDados = dataBaseManager.getWritableDatabase();
                ContentValues valores;
                String where;
                String novoNome;
                String novaData_inicio;
                String novaData_fim;

                valores = new ContentValues();
                novoNome = et_nome_projeto.getText().toString();
                novaData_inicio = dataInicio.getText().toString();
                novaData_fim = dataFim.getText().toString();
                valores.put("nome", novoNome);
                valores.put("data_inicio", novaData_inicio);
                valores.put("data_fim", novaData_fim);

                where = "id =" + cursor.getString(cursor.getColumnIndexOrThrow("id"));

                bancoDeDados.update("projeto", valores, where, null);
                abrirTelaListagem();
            }
        });

        Button bt_dataInicio = findViewById(R.id.bt_data_inicio);
        bt_dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditarProjeto.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditarProjeto.this, new DatePickerDialog.OnDateSetListener() {
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

    public Cursor carregaDadoById(int id){
        String[] campos_item = {"id", "nome", "data_inicio", "data_fim"};
        String where =  "id =" + id;
        bancoDeDados = dataBaseManager.getReadableDatabase();
        Cursor cursor = bancoDeDados.query("projeto", campos_item,where, null, null, null, null, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        bancoDeDados.close();
        return cursor;
    }

    public void abrirTelaListagem(){

        Intent lista = new Intent(this, ListagemProjeto.class);
        startActivity(lista);

    }


}

