package com.example.reuniaodecoleta;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Projeto extends AppCompatActivity {
    private EditText etNome = null;
    private TextView dataInicio = null;
    private TextView dataFim = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_projeto);

        etNome = (EditText) findViewById(R.id.et_nome_projeto);
        dataInicio = (TextView) findViewById(R.id.tv_data_inicio);
        dataFim = (TextView) findViewById(R.id.tv_data_fim);

        Button criar = (Button) findViewById(R.id.bt_criar);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeProjeto = etNome.getText().toString();
                String data_inicio = dataInicio.getText().toString();
                String data_fim = dataFim.getText().toString();

                if (nomeProjeto.isEmpty() && data_inicio.isEmpty() && data_fim.isEmpty()) {
                    Toast.makeText(Projeto.this, "Por favor, preencha os campos!!", Toast.LENGTH_LONG).show();
                } else if (nomeProjeto.isEmpty()) {
                    Toast.makeText(Projeto.this, "Por favor, coloque um nome no seu projeto!!", Toast.LENGTH_LONG).show();
                } else if (data_inicio.isEmpty()) {
                    Toast.makeText(Projeto.this, "Por favor, coloque uma data de inicio no seu projeto!!", Toast.LENGTH_LONG).show();
                } else if (data_fim.isEmpty()) {
                    Toast.makeText(Projeto.this, "Por favor, coloque uma data de fim no seu projeto!!", Toast.LENGTH_LONG).show();
                } else {

                    String mensagem = "Nome do projeto: " + nomeProjeto + "\n" +
                            "Data de inicio do projeto: " + data_inicio + "\n" +
                            "Data do fim do projeto: " + data_fim;

                    new AlertDialog.Builder(Projeto.this)
                            .setTitle("Informações do Projeto")
                            .setMessage(mensagem)
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            })
                            .show();
                }
            }
        });

        Button bt_dataInicio = (Button) findViewById(R.id.bt_data_inicio);
        bt_dataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Projeto.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String formattedDate = String.format("%02d/%02d/%04d", day, month + 1, year);
                        dataInicio.setText(formattedDate);
                    }
                }, 2024, 5, 5);
                datePickerDialog.show();
            }
        });

        Button bt_dataFim = (Button) findViewById(R.id.bt_data_fim);
        bt_dataFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Projeto.this, new DatePickerDialog.OnDateSetListener() {
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
}