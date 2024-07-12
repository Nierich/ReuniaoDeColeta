package com.example.reuniaodecoleta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.reuniaodecoleta.TelaProjeto.ListagemProjeto;
import com.example.reuniaodecoleta.TelaProjeto.CadastroProjeto;
import com.example.reuniaodecoleta.TelaRequisito.ListagemRequisito;
import com.example.reuniaodecoleta.TelaRequisito.CadastroRequisito;
import com.example.reuniaodecoleta.CadastroUsuario;
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpToolbar(int toolbarId) {
        Toolbar toolbar = findViewById(toolbarId);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cadastroProjeto) {
            startActivity(new Intent(this, CadastroProjeto.class));
            Toast.makeText(this, "Menu de cadastro selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.listagemProjeto) {
            startActivity(new Intent(this, ListagemProjeto.class));
            Toast.makeText(this, "Menu de listagem selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.cadastroRequisito) {
            startActivity(new Intent(this, CadastroRequisito.class));
            Toast.makeText(this, "Menu de Cadastro selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.cadastroUsuario) {
            startActivity(new Intent(this, CadastroUsuario.class));
            Toast.makeText(this, "Menu de Cadastro selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.voltar) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}