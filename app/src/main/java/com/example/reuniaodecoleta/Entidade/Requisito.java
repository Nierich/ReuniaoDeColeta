package com.example.reuniaodecoleta.Entidade;

import java.time.LocalDateTime;
import java.util.Date;

public class Requisito {

    int id;
    String descricao;
    LocalDateTime data_hora_registro;
    String tipo;
    String nivel_importancia;
    String nivel_dificuldade;
    int horas_estimadas;
    int qntd_desenvolvedores;
    int projeto_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData_hora_registro() {
        return data_hora_registro;
    }

    public void setData_hora_registro(LocalDateTime data_hora_registro) {
        this.data_hora_registro = data_hora_registro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivel_importancia() {
        return nivel_importancia;
    }

    public void setNivel_importancia(String nivel_importancia) {
        this.nivel_importancia = nivel_importancia;
    }

    public String getNivel_dificuldade() {
        return nivel_dificuldade;
    }

    public void setNivel_dificuldade(String nivel_dificuldade) {
        this.nivel_dificuldade = nivel_dificuldade;
    }

    public int getHoras_estimadas() {
        return horas_estimadas;
    }

    public void setHoras_estimadas(int horas_estimadas) {
        this.horas_estimadas = horas_estimadas;
    }

    public int getQntd_desenvolvedores() {
        return qntd_desenvolvedores;
    }

    public void setQntd_desenvolvedores(int qntd_desenvolvedores) {
        this.qntd_desenvolvedores = qntd_desenvolvedores;
    }

    public int getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(int projeto_id) {
        this.projeto_id = projeto_id;
    }
}
