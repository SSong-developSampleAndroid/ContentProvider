package com.ssong_develop.samplesqlitehelper

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ssong_develop.samplesqlitehelper.config.DBSettings

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tuple = ContentValues()
        tuple.put(DBSettings.SampleDBSettings.TEXT,"Hello ContentProvider !!")
        contentResolver.insert(DBSettings.SampleDBSettings.CONTENT_URI,tuple)

        val cols = arrayOf(DBSettings.SampleDBSettings.ID,DBSettings.SampleDBSettings.TEXT)
        val uri = DBSettings.SampleDBSettings.CONTENT_URI
        val cursor = contentResolver.query(uri,cols,null,null,null)
        if(cursor?.moveToLast() == true){
            Toast.makeText(this,cursor.getString(cursor.getColumnIndex(DBSettings.SampleDBSettings.TEXT)),Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this,"sorry",Toast.LENGTH_LONG).show()
        }
    }
}