package com.example.reuniaodecoleta.Entidade;

import java.util.Date;

public class Projeto {

    int id;
    String nome;
    String data_inicio;
    String data_fim;

    // Construtor vazio
    public Projeto() {
    }

    // Novo construtor
    public Projeto(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_fim() {
        return data_fim;
    }

    public void setData_fim(String data_fim) {
        this.data_fim = data_fim;
    }
    @Override
    public String toString() {
        return nome; // Define como o Spinner irá exibir as categorias (pelo nome)
    }

}
