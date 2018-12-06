package com.zlh.huahua.base.util

import android.util.Log
import com.zlh.huahua.base.contsant.AppConfig

object LogUtil {

    private fun getTag(): String? {
        val sts = Thread.currentThread().stackTrace
        if (sts != null) {
            for (st in sts) {
                if (st.isNativeMethod) continue
                if (st.className === Thread::javaClass.name) continue
                if (st.className === LogUtil::javaClass.name) continue
                return st.className + "." + st.methodName + "(" + st.fileName + ":" + st.lineNumber + ")"
            }
        }
        return null
    }

    fun v(msg: String) {
        if (AppConfig.isDebug) {
            Log.v(getTag(), msg)
        }
    }

    fun i(msg: String) {
        if (AppConfig.isDebug) {
            Log.i(getTag(), msg)
        }
    }

    fun d(msg: String) {
        if (AppConfig.isDebug) {
            Log.d(getTag(), msg)
        }
    }

    fun w(msg: String) {
        if (AppConfig.isDebug) {
            Log.w(getTag(), msg)
        }
    }

    fun e(msg: String) {
        if (AppConfig.isDebug) {
            Log.e(getTag(), msg)
        }
    }
}