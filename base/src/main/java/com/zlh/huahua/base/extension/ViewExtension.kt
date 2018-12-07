package com.zlh.huahua.base.extension

import android.util.SparseArray
import android.view.View
import android.view.View.*
import android.view.ViewTreeObserver
import android.webkit.WebView
import android.widget.LinearLayout

/**
 * View 扩展函数
 * author: leeorz
 * email:378229364@qq.com
 * created on: 2017/7/7 上午11:03
 * description:
 */

/**
 * 判断控件是否消失
 */
fun View.isGone(): Boolean {
    return visibility == GONE
}

/**
 * 设置控件消失
 */
fun View.gone() {
    visibility = GONE
}

/**
 * 判断控件是否显示
 */
fun View.isVisible(): Boolean {
    return visibility == VISIBLE
}

/**
 * 显示控件
 */
fun View.show() {
    visibility = VISIBLE
}

/**
 * 判断控件是否隐藏
 */
fun View.isInvisible(): Boolean {
    return visibility == INVISIBLE
}

/**
 * 隐藏控件
 */
fun View.hide() {
    visibility = INVISIBLE
}

/**
 * 设置控件宽
 * @param width
 */
fun View.setWidth(width: Int) {
    val params = layoutParams as LinearLayout.LayoutParams
    params.width = width
    layoutParams = params
}

/**
 * 设置控件高
 * @param height
 */
fun View.setHeight(height: Int) {
    val params = layoutParams as LinearLayout.LayoutParams
    params.height = height
    layoutParams = params
}

/**
 * 直接获取控件的宽、高
 * @return IntArray
 */
fun View.getWidgetWH(): IntArray {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeGlobalOnLayoutListener(this)
        }
    })
    return intArrayOf(width, height)
}

/**
 * 简化loadData函数
 */
fun WebView.loadData(content: String) {
    loadData(content, "text/html; charset=UTF-8", null)
}

/**
 * 实现viewHolder
 */
fun <T : View> View.findViewOften(viewId: Int): T {
    val viewHolder: SparseArray<View> = tag as? SparseArray<View> ?: SparseArray()
    tag = viewHolder
    var childView: View? = viewHolder.get(viewId)
    if (null == childView) {
        childView = findViewById(viewId)
        viewHolder.put(viewId, childView)
    }
    return childView as T
}

