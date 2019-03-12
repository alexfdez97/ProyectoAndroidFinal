package com.example.alejandrofm.proyectoandroidfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {

    /**
     * Sentencia de creacion de la tabla
     */
    private final String sqlCreateTable = "CREATE TABLE records (id INTEGER PRIMARY KEY AUTOINCREMENT, points INTEGER, date DATETIME DEFAULT CURRENT_TIMESTAMP)";

    /**
     * Inicializa BaseDatos
     * @param context el contexto de la aplicacion
     * @param name el nombre de la base de datos
     * @param factory el factory
     * @param version la version
     */
    public BaseDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        db.execSQL(sqlCreateTable);
    }
}
