package com.renhuan.utils.other

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.renhuan.utils.CrashActivity
import com.tencent.mmkv.MMKV

/**
 * created by renhuan
 * time : 2022/3/28 13:05
 * describe : 通过此类，实现全局content获取
 */
class ApplicationContentProvider : ContentProvider() {

    companion object {
        lateinit var mAppContext: Context
    }

    override fun onCreate(): Boolean {
        mAppContext = context?.applicationContext!!
        MMKV.initialize(mAppContext)
        CaocConfig.Builder.create()
            .errorActivity(CrashActivity::class.java)
            .apply()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

}