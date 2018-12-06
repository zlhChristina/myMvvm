package com.zlh.huahua.base.util

import android.text.TextUtils
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonUtil {

    val gson: Gson
        get() {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.addSerializationExclusionStrategy(IgnoreStrategy())
            return gsonBuilder.create()
        }

    fun <T> fromJson(json: String, clazz: Class<T>): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        return gson.fromJson(json, clazz)
    }

    fun toJson(obj: Any?): String {
        if (obj == null) {
            return ""
        }
        return gson.toJson(obj)
    }

    internal class IgnoreStrategy : ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }

        override fun shouldSkipField(f: FieldAttributes?): Boolean {
            val annotations = f?.annotations
            annotations?.forEach {
                if (it.annotationClass == GsonIgnore::class.java) {
                    return true
                }
            }
            return false
        }
    }

    @Target(AnnotationTarget.FIELD)
    @Retention(AnnotationRetention.RUNTIME)
    internal annotation class GsonIgnore
}