package com.ssong_develop.samplesqlitehelper.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.ssong_develop.samplesqlitehelper.config.DBSettings
import com.ssong_develop.samplesqlitehelper.config.ProviderConfig
import com.ssong_develop.samplesqlitehelper.db.DBHelper
import java.lang.IllegalArgumentException

class DBContentProvider : ContentProvider() {
    private val DATABASE_NAME = "sample_db"
    var DATABASE_VERSION = 1
    private var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private var dbHelper : DBHelper? = null
    private var projMap = mutableMapOf<String,String>()

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context,DATABASE_NAME,DATABASE_VERSION)
        uriMatcher.apply {
            addURI(ProviderConfig.AUTHORITY, DBSettings.SampleDBSettings.TABLE_NAME,1)
            addURI(ProviderConfig.AUTHORITY, DBSettings.SampleDBSettings.TABLE_NAME + "/#",2)
        }
        projMap.put(DBSettings.SampleDBSettings.ID, DBSettings.SampleDBSettings.ID)
        projMap.put(DBSettings.SampleDBSettings.TEXT, DBSettings.SampleDBSettings.TEXT)
        return true
    }

    override fun query(
        uri: Uri,
        p: Array<out String>?,
        s: String?,
        args: Array<out String>?,
        sort: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.apply {
            tables = DBSettings.SampleDBSettings.TABLE_NAME
            projectionMap = projMap
        }
        var s1 = s
        if(uriMatcher.match(uri) != 1) {
            if(uriMatcher.match(uri) == 2){
                s1 = s + "_id" + uri?.lastPathSegment
            }
            else
                throw IllegalArgumentException("Unknown URI$uri")
        }
        val db = dbHelper?.readableDatabase
        val c = queryBuilder.query(db,p,s1,args,null,null,sort)
        c.setNotificationUri(context?.contentResolver,uri)
        return c
    }

    override fun getType(uri: Uri): String? {
        if(uriMatcher.match(uri) == 1){
            return "contentProvider"
        } else {
            throw IllegalArgumentException("Unknown")
        }
    }

    override fun insert(uri: Uri, resolver: ContentValues?): Uri? {
        if(uriMatcher.match(uri) != 1) throw IllegalArgumentException("Unknown URI $uri")
        val db = dbHelper?.writableDatabase
        val rId = db?.insert(DBSettings.SampleDBSettings.TABLE_NAME,"Hello ContentProvider!!",resolver)
        rId?.let {
            sequenceOf(it)
                .filter { it > 0 }
                .map {
                    val uri = ContentUris.withAppendedId(DBSettings.SampleDBSettings.CONTENT_URI,it)
                    context?.contentResolver?.notifyChange(uri,null)
                }

            /*val time = measureTimeMillis {
                if (it > 0) {
                    val uri =
                        ContentUris.withAppendedId(DBSettings.SampleDBSettings.CONTENT_URI, it)
                    context?.contentResolver?.notifyChange(uri, null)
                    return uri
                }
            }*/
        }
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 1
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 1
    }
}