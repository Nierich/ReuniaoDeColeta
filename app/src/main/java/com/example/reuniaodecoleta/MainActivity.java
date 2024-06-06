package com.example.reuniaodecoleta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.myToolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater exibirMenu = getMenuInflater();
        exibirMenu.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cadastroProjeto) {
            Intent intent = new Intent(MainActivity.this, Projeto.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Menu de cadastro selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.listagemProjeto) {
            Intent intent = new Intent(MainActivity.this, ListagemProjeto.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Menu de listagem selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.cadastroRequisito) {
            Intent intent = new Intent(MainActivity.this, Requisito.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Menu de Cadastro selecionado", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.listagemRequisito) {
            Intent intent = new Intent(MainActivity.this, ListagemRequisito.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Menu de listagem selecionado", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}