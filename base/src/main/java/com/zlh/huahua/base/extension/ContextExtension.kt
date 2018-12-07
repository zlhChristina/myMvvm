package com.zlh.huahua.base.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.File

/**
 * Context扩展函数
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/7/7 上午11:02
 * description:
 */

/**
 * 显示短时间系统Toast
 */
fun Context.showShortToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * 显示长时间系统Toast
 */
fun Context.showLongToast(message: CharSequence) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * dp转px
 */
fun Context.dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, resources.displayMetrics).toInt()
}

/**
 * sp转px
 */
fun Context.sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, resources.displayMetrics).toInt()
}

/**
 * px转dp
 */
fun Context.px2dp(pxVal: Float): Float {
    return pxVal / resources.displayMetrics.density
}

/**
 * px转sp
 */
fun Context.px2sp(pxVal: Float): Float {
    return pxVal / resources.displayMetrics.scaledDensity
}

/**
 * 复制,不弹出默认提示
 * @param str
 */
fun Context.copy(str: String) {
    copy(str, false)
}

/**
 * 复制,弹出默认提示
 * @param str
 * @param isShowToast boolean 是否弹出默认提示
 *
 */
fun Context.copy(str: String, isShowToast: Boolean) {
    val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clip.primaryClip = ClipData.newPlainText(null, str) // 复制
    if (isShowToast)
        showShortToast("文本已复制")
}

/**
 * 复制，自定义提示内容
 * @param str
 * @param text 自定义提示内容
 */
fun Context.copy(str: String, text: String?) {
    val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    clip!!.primaryClip = ClipData.newPlainText(null, str) // 复制
    if (!text.isNullOrEmpty())
        showShortToast(text)
}

/**
 * 黏贴
 * @return
 */
fun Context.paste(): String {
    val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
    val clipData = clip!!.getPrimaryClip()
    if (clipData != null && clipData.getItemCount() > 0) {
        return clipData.getItemAt(0).coerceToText(this).toString()
    }
    return ""
}

/**
 * 判断是否有网络连接
 */
fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (cm != null) {
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isAvailable) {
            return true
        }
    }
    return false
}

/**
 * 判断wifi 是否可用
 * @return boolean
 */
fun Context.isWifiDataEnable(context: Context): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (cm != null) {
        val networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
            return true
        }
    }
    return false
}

/**
 * 判断wifi,是否已经连接
 * @return boolean
 */
fun Context.isWifiDataConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    if (cm != null) {
        val networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (networkInfo != null && networkInfo.isConnected) {
            return true
        }
    }
    return false
}

/**
 * 获取网络类型
 * @return
 */
fun Context.getNetworkType(): Int {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val networkInfo = connectivityManager?.activeNetworkInfo
    return networkInfo?.type ?: -1
}

/**
 * 获取网络供应商名称
 * @return
 */
fun Context.getNetworkOperatorName(): String {
    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
    if (telephonyManager != null) {
        return telephonyManager.networkOperatorName
    }
    return ""
}

/**
 * 获得屏幕高度
 * @return
 */
fun Context.getScreenWH(context: Context): IntArray {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(outMetrics)
    return intArrayOf(outMetrics.widthPixels, outMetrics.heightPixels)
}

fun Context.load(context: Context, url: String, imageView: ImageView) {
    Glide.with(context).load(url).into(imageView)
}

fun Context.load(context: Context, resId: Int, imageView: ImageView) {
    Glide.with(context).load(resId).into(imageView)
}

fun Context.load(context: Context, file: File, imageView: ImageView) {
    Glide.with(context).load(file).into(imageView)
}