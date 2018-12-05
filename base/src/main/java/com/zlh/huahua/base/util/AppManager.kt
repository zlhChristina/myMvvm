package com.zlh.huahua.base.util

import android.app.Activity
import com.zlh.huahua.base.util.AppManager.activityStack
import java.util.*

object AppManager {

    var activityStack: Stack<Activity>? = null
    val appManager: AppManager? = null

    fun getActivity(cls: Class<*>): Activity? {
        if (activityStack != null)
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    return activity
                }
            }
        return null
    }

    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack?.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack?.remove(activity)
    }

    fun finishAllActivity() {
        if (activityStack != null)
            for (activity in activityStack!!) {
                activity.finish()
            }
        activityStack?.clear()

    }
}