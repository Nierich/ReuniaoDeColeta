package com.example.reuniaodecoleta.TelaRequisito;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.R;

public class CadastroRequisito extends BaseActivity {

    private EditText etDescricao = null;
    private Spinner spn_tipo = null;
    private Spinner spn_importancia = null;
    private Spinner spn_dificuldade = null;
    private EditText et_horas = null;
    private Spinner spn_desenvolvedores = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_requisito);

        setUpToolbar(R.id.myToolbar);

        etDescricao = (EditText) findViewById(R.id.et_descricao_requisito);
        et_horas = (EditText) findViewById(R.id.et_horas_estimadas);
        spn_tipo = (Spinner) findViewById(R.id.spn_tipo_requisito);
        spn_importancia = (Spinner) findViewById(R.id.spn_importancia_requisito);
        spn_dificuldade = (Spinner) findViewById(R.id.spn_dificuldade_requisito);
        spn_desenvolvedores = (Spinner) findViewById(R.id.spn_desenvolvedores_requisito);

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

                // Exibindo os itens selecionados
                String mensagem = "Descrição: " + descricao + "\n" +
                        "Tipo: " + tipo + "\n" +
                        "Importância: " + importancia + "\n" +
                        "Dificuldade: " + dificuldade + "\n" +
                        "Horas estimadas: " + horas + "\n" +
                        "Desenvolvedores: " + desenvolvedores;


                new AlertDialog.Builder(CadastroRequisito.this)
                        .setTitle("Informações do Requisito")
                        .setMessage(mensagem)
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        })
                        .show();

            }
        });
    }
}
