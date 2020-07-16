package com.example.proyectoventa_autos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MainSQLiteOpenHelper extends SQLiteOpenHelper {

    //constructor
    public MainSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //crear base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cliente(nombre text primary key,usuario text,clave text, ciudad text)");
        db.execSQL("CREATE TABLE auto(placa text primary key,modelo text,marca text, valor text)");
        db.execSQL("CREATE TABLE venta(placa text primary key, usuario text, color text, valor text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE cliente");
        onCreate(db);
        db.execSQL("DROP TABLE auto");
        onCreate(db);
        db.execSQL("DROP TABLE venta");
        onCreate(db);
    }
}
