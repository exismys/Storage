package com.example.storage

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT, " +
                AGE_COL + " TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addName(name : String, age : String) {
        val values = ContentValues()
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME");
        db.close()
    }

    fun delete(name: String): Boolean {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "name = ?", arrayOf(name)) != -1
    }

    fun update(original_name: String, new_name: String, new_age: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COl, new_name)
        values.put(AGE_COL, new_age)
        db.update(TABLE_NAME, values, "name = ?", arrayOf(original_name))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "Sample"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "sample_table_1"
        const val ID_COL = "id"
        const val NAME_COl = "name"
        const val AGE_COL = "age"
    }
}