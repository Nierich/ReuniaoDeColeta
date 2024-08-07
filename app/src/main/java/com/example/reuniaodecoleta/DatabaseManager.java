package com.example.reuniaodecoleta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    public DatabaseManager(Context context, String databaseName, int version){
        super(context, databaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE projeto " +
                "(id INTEGER NOT NULL PRIMARY KEY, " +
                "nome TEXT, " +
                "data_inicio DATE, " +
                "data_fim DATE);");
        db.execSQL("CREATE TABLE requisito" +
                " (id INTEGER NOT NULL PRIMARY KEY, " +
                "descricao TEXT, " +
                "data_hora_registro DATETIME, " +
                "tipo TEXT, " +
                "nivel_importancia TEXT, " +
                "nivel_dificuldade TEXT, " +
                "horas_estimadas INTEGER, " +
                "qntd_desenvolvedores INTEGER, " +
                "latitude TEXT, " +
                "longitude TEXT, " +
                "foto1 BLOB, " +
                "foto2 BLOB, " +
                "projeto_id INTEGER, " +
                "FOREIGN KEY(projeto_id) REFERENCES projeto(id));");
        db.execSQL("CREATE TABLE usuario" +
                " (id INTEGER NOT NULL PRIMARY KEY, " +
                "nome TEXT, " +
                "senha TEXT);");
        db.execSQL("INSERT INTO usuario (nome, senha) VALUES ('admin', 'admin');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE projeto");
        db.execSQL("DROP TABLE requisito");
        db.execSQL("DROP TABLE IF EXISTS usuario");
        onCreate(db);
    }

}
