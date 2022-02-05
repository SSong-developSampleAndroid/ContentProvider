package com.ssong_develop.samplesqlitehelper.config

import android.net.Uri
import android.provider.BaseColumns

class DBSettings {
    interface BaseDBSetting : BaseColumns {
        val ID: String
            get() = "_ID"

        val TEXT: String
            get() = "MESSAGE"
    }

    object SampleDBSettings : BaseDBSetting {
        const val TABLE_NAME = "test_table"
        val CONTENT_URI = Uri.parse("content://" + ProviderConfig.AUTHORITY + "/" + TABLE_NAME)
    }
}