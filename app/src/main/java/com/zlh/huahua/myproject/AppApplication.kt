package com.zlh.huahua.myproject

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.zlh.huahua.base.util.ContextHolder
import java.lang.ref.WeakReference

class AppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        ContextHolder.setApplicationContext(WeakReference(this))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}