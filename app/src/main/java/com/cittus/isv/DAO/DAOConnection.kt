package com.cittus.isv.DAO

    import android.content.Context
    import android.database.sqlite.SQLiteOpenHelper
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteDatabase.CursorFactory

    class DAOConnection (context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            //db.execSQL()
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        }
    }