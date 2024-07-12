package com.example.reuniaodecoleta;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reuniaodecoleta.Entidade.Projeto;
import com.example.reuniaodecoleta.Entidade.Requisito;
import com.example.reuniaodecoleta.Entidade.Usuario;
import com.example.reuniaodecoleta.TelaRequisito.CadastroRequisito;

import java.io.ByteArrayOutputStream;

public class CadastroUsuario extends BaseActivity{

    private EditText et_user = null;
    private EditText et_senha = null;
    private SQLiteDatabase bancoDeDados;
    private DatabaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

        setUpToolbar(R.id.myToolbar);

        et_user = (EditText) findViewById(R.id.et_usuario);
        et_senha = (EditText) findViewById(R.id.et_senha);

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        Button bt_criar = findViewById(R.id.bt_criar);
        bt_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = et_user.getText().toString();
                String senha = et_senha.getText().toString();

                if (user.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(CadastroUsuario.this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    Usuario usuario = new Usuario();
                    usuario.setNome(user);
                    usuario.setSenha(senha);
                    salvarUserNoBanco(usuario);
                }
            }
        });
    }

    private void salvarUserNoBanco(Usuario usuario) {
        ContentValues valores = criarContentValuesParaRequisito(usuario);

        long resultado = bancoDeDados.insert("usuario", null, valores);

        if (resultado != -1) {
            Toast.makeText(CadastroUsuario.this, "Usuario criado com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CadastroUsuario.this, "Erro ao criar requisito!", Toast.LENGTH_LONG).show();
        }
    }

    private ContentValues criarContentValuesParaRequisito(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("senha", usuario.getSenha());

        return valores;
    }
}
