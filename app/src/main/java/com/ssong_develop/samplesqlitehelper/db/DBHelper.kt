package com.ssong_develop.samplesqlitehelper.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ssong_develop.samplesqlitehelper.config.DBSettings

class DBHelper(
    context: Context?,
    name: String?,
    version: Int
) : SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + DBSettings.SampleDBSettings.TABLE_NAME +
                    " (" + "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBSettings.SampleDBSettings.TEXT + " VARCHAR(20)" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DBSettings.SampleDBSettings.TABLE_NAME)
        onCreate(db)
    }
}