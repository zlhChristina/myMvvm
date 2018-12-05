package com.zlh.huahua.base.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.zlh.huahua.base.R

object StatusBarUtil {

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun setStatusBar(activity: Activity) {
        //适配状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //4.4到5.0
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorViewGroup: ViewGroup = activity.window.decorView as ViewGroup
            val statusBarView = View(activity.window.decorView.context)
            val statusBarHeight = getStatusBarHeight(activity.window.decorView.context)
            val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight)
            params.gravity = Gravity.TOP
            statusBarView.layoutParams = params
            statusBarView.setBackgroundResource(R.color.colorMain)
            decorViewGroup.addView(statusBarView)
        }
    }

    fun setStatusBarTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = activity.window.decorView
            //设置让应用主题内容占据状态栏和导航栏
            val option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            //设置状态栏和导航栏颜色为透明
            activity.window.statusBarColor = Color.TRANSPARENT
            activity.window.navigationBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}