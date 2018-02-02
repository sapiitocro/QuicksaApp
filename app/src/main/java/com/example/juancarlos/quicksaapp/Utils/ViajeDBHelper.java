package com.example.juancarlos.quicksaapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.juancarlos.quicksaapp.Model.Viaje;

import java.util.LinkedList;
import java.util.List;


public class ViajeDBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_DATABASE = "viaje.db";
    private static final int VERSION_DATABASE = 3;
    private static final String TABLA_NOMBRE = "Viaje";
    private static final String COLUMNA_ID = "_id";
    private static final String COLUMNA_OPERADOR = "operador";
    private static final String COLUMNA_MATERIAL = "material";
    private static final String COLUMNA_ORIGEN = "origen";
    private static final String COLUMNA_DESTINO = "destino";

    public ViajeDBHelper(Context context) {
        super(context, NOMBRE_DATABASE, null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLA_NOMBRE + " (" +
                COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_OPERADOR + " TEXT NOT NULL, " +
                COLUMNA_MATERIAL + " TEXT NOT NULL, " +
                COLUMNA_ORIGEN + " TEXT NOT NULL, " +
                COLUMNA_DESTINO + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_NOMBRE);
        this.onCreate(db);
    }

    public void saveNewViaje(Viaje viaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_OPERADOR, viaje.getOperador());
        values.put(COLUMNA_MATERIAL, viaje.getMaterial());
        values.put(COLUMNA_ORIGEN, viaje.getOrigen());
        values.put(COLUMNA_DESTINO, viaje.getDestino());

        db.insert(TABLA_NOMBRE, null, values);
        db.close();
    }

    void deleteViajeRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLA_NOMBRE + "WHERE _id='" + id + "'");
        Toast.makeText(context, "Borrado Correctamente.", Toast.LENGTH_SHORT).show();
    }

    public List<Viaje> peopleList() {
        String query;

        query = "SELECT  * FROM " + TABLA_NOMBRE;
        List<Viaje> personLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        Viaje viaje;

        if (cursor.moveToFirst()) {
            do {
                viaje = new Viaje();

                viaje.setId(cursor.getLong(cursor.getColumnIndex(COLUMNA_ID)));
                viaje.setOperador(cursor.getString(cursor.getColumnIndex(COLUMNA_OPERADOR)));
                viaje.setMaterial(cursor.getString(cursor.getColumnIndex(COLUMNA_MATERIAL)));
                viaje.setOrigen(cursor.getString(cursor.getColumnIndex(COLUMNA_ORIGEN)));
                viaje.setDestino(cursor.getString(cursor.getColumnIndex(COLUMNA_DESTINO)));
                personLinkedList.add(viaje);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
    }

}
