package com.example.taller2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TiendaVirtual.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre_usuario";
    public static final String COL_CORREO = "correo";
    public static final String COL_CONTRASENA = "contrasena";
    public static final String COL_ROL = "rol";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_NOMBRE + " TEXT,"
                + COL_CORREO + " TEXT,"
                + COL_CONTRASENA + " TEXT,"
                + COL_ROL + " INTEGER" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    public boolean registrarUsuario(String nombre, String correo, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, nombre);
        values.put(COL_CORREO, correo);
        values.put(COL_CONTRASENA, contrasena);
        values.put(COL_ROL, 0); // Usuario normal

        long resultado = db.insert(TABLE_USUARIOS, null, values);
        return resultado != -1;
    }

    public boolean verificarUsuario(String correo, String contrasena) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS +
                " WHERE " + COL_CORREO + "=? AND " + COL_CONTRASENA + "=?", new String[]{correo, contrasena});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean obtenerRol(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_ROL + " FROM " + TABLE_USUARIOS +
                " WHERE " + COL_CORREO + "=?", new String[]{correo});
        if (cursor.moveToFirst()) {
            int rol = cursor.getInt(0); // 1 = admin, 0 = usuario
            cursor.close();
            return rol == 1;
        }
        cursor.close();
        return false;
    }

    public boolean existeCorreo(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE " + COL_CORREO + "=?", new String[]{correo});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean actualizarContrasena(String correo, String nuevaContrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CONTRASENA, nuevaContrasena);
        int filas = db.update(TABLE_USUARIOS, values, COL_CORREO + "=?", new String[]{correo});
        return filas > 0;
    }

    public boolean existeUsuario(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USUARIOS + " WHERE " + COL_CORREO + " = ?",
                new String[]{correo}
        );
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public void agregarUsuario(String nombre, String correo, String contrasena, boolean esAdmin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOMBRE, nombre);
        values.put(COL_CORREO, correo);
        values.put(COL_CONTRASENA, contrasena);
        values.put(COL_ROL, esAdmin ? 1 : 0);
        db.insert(TABLE_USUARIOS, null, values);
    }
}