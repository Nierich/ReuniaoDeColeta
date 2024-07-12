package com.example.reuniaodecoleta;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDeDados;
    private DatabaseManager dataBaseManager;
    private EditText et_user = null;
    private EditText et_senha = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        et_user = (EditText) findViewById(R.id.et_usuario);
        et_senha = (EditText) findViewById(R.id.et_senha);

        Button bt_login = findViewById(R.id.bt_entrar);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = et_user.getText().toString();
                String senha = et_senha.getText().toString();


                String sql = "SELECT nome, senha FROM usuario WHERE nome = ? AND senha = ?";
                Cursor cursor = bancoDeDados.rawQuery(sql, new String[]{user, senha});
                // Verifica se a consulta retornou algum resultado
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex("nome");
                    if (columnIndex != -1) {
                        // Se encontrou, significa que o usuário está autenticado
                        String nomeUsuario = cursor.getString(columnIndex);
                        Toast.makeText(MainActivity.this, "Login bem sucedido para " + nomeUsuario, Toast.LENGTH_SHORT).show();
                        abrirTelaInicio();
                    } else {
                        // Trate aqui caso a coluna não seja encontrada
                        Toast.makeText(MainActivity.this, "Coluna 'nome' não encontrada no resultado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Se não encontrou, exibe uma mensagem de erro
                    Toast.makeText(MainActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
                // Fecha o cursor após o uso
                cursor.close();
            }
        });

    }
    public void abrirTelaInicio(){

        Intent intent = new Intent(this, TelaInicio.class);
        startActivity(intent);
    }
}