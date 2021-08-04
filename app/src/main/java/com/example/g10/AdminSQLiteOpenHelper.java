package com.example.g10;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    /**
     * Constructor para la base de datos
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory,version);
    }

    /**
     * Método que crea la base de datos
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuariosRegistrados(matricula_usuario TEXT NOT NULL, nom_usuario TEXT NOT NULL,ap_usuario TEXT NOT NULL,sueldo TEXT NOT NULL)");
    }

    /**
     * Método para las actualizaciones de la base de datos
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuariosRegistrados");
        this.onCreate(db);
    }

}
