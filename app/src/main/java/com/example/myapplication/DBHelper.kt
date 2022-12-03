package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // статические константы имеет смысл хранить так:
    companion object {
        // версия БД
        const val DATABASE_VERSION = 1
        // название БД
        const val DATABASE_NAME = "AppDB"
        // название таблицы
        const val TABLE_NAME = "valuesList"
        // названия полей
        const val KEY_ID = "id"
        const val NAME = "name"
        const val SURNAME = "surname"
        const val BIRTH_DATE = "birthDate"
        const val PHONE_NUMBER = "phoneNumber"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME TEXT NOT NULL,
                $SURNAME TEXT NOT NULL,
                $BIRTH_DATE TEXT NOT NULL,
                $PHONE_NUMBER TEXT NOT NULL
            )""")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun getAll(): List<Contact> {
        val result = mutableListOf<Contact>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val nameIndex: Int = cursor.getColumnIndex(NAME)
            val snameIndex: Int = cursor.getColumnIndex(SURNAME)
            val birthDateIndex: Int = cursor.getColumnIndex(BIRTH_DATE)
            val phoneNumberIndex: Int = cursor.getColumnIndex(PHONE_NUMBER)
            do {
                val contact = Contact(
                    cursor.getLong(idIndex),
                    cursor.getString(nameIndex),
                    cursor.getString(snameIndex),
                    cursor.getString(birthDateIndex),
                    cursor.getString(phoneNumberIndex)
                )
                result.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }


    fun add(name: String, surname: String, birthDate: String, phoneNumber: String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(SURNAME, surname)
        contentValues.put(BIRTH_DATE, birthDate)
        contentValues.put(PHONE_NUMBER, phoneNumber)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun update(id: Long, name: String, surname: String, birthDate: String, phoneNumber: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, name)
        contentValues.put(SURNAME, surname)
        contentValues.put(BIRTH_DATE, birthDate)
        contentValues.put(PHONE_NUMBER, phoneNumber)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun remove(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun removeAll() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        close()
    }
}