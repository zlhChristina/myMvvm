package com.zlh.huahua.base.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils

object SPUtil {

    private val SP_FILE_NAME = "sp_file.xml"

    fun getSP(): SharedPreferences? {
        val sp = ContextHolder.getApplicationContext()?.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
        return sp
    }

    fun <T> saveData(key: String, value: T) {
        if (TextUtils.isEmpty(key)) return
        val editor = getSP()?.edit()
        when (value) {
            is Int -> {
                editor?.putInt(key, value)
            }
            is String -> {
                editor?.putString(key, value)
            }
            is Boolean -> {
                editor?.putBoolean(key, value)
            }
            is Float -> {
                editor?.putFloat(key, value)
            }
            is Long -> {
                editor?.putLong(key, value)
            }
            else -> {
                editor?.putString(key, GsonUtil.toJson(value))
            }
        }
        editor?.apply()
    }

    fun <T> getData(key: String, clazz: Class<T>): T? {
        if (TextUtils.isEmpty(key)) return null
        when (clazz) {
            Int::class.java -> {
                return getSP()?.getInt(key, 0) as T
            }
            Long::class.java -> {
                return getSP()?.getLong(key, 0L) as T
            }
            String::class.java -> {
                return getSP()?.getString(key, "") as T
            }
            Boolean::class.java -> {
                return getSP()?.getBoolean(key, false) as T
            }
            Float::class.java -> {
                return getSP()?.getFloat(key, 0f) as T
            }
            else -> {
                val str = getSP()?.getString(key, "")
                return GsonUtil.fromJson(str!!, clazz)
            }
        }
    }

    fun deleteData(key: String) {
        if (TextUtils.isEmpty(key)) return
        val editor = getSP()?.edit()
        editor?.remove(key)
        editor?.apply()
    }
}