package com.zlh.huahua.base.view

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.zlh.huahua.base.R


abstract class BaseDialog(val activity: Activity) : DialogInterface.OnCancelListener,
    DialogInterface.OnDismissListener {

    private var dialog: Dialog? = null

    protected fun initDialog(): Dialog {
        dialog = Dialog(activity, R.style.dialog)
        val view = activity.layoutInflater.inflate(getLayoutID(), null)
        dialog?.setContentView(view)
        dialog?.setOnCancelListener(this)
        dialog?.setOnDismissListener(this)
        dialog?.setCancelable(isCancelable())
        val win = dialog?.window
        val params = win?.attributes
        params?.width = getWidth()
        params?.height = getHeight()
        params?.dimAmount = getDimAmount()
        params?.gravity = getDialogGravity()
        win?.attributes = params
        initViews(view)
        return dialog!!
    }

    fun show() {
        if (activity.isFinishing) {
            if (dialog != null) {
                dialog?.cancel()
                dialog = null
            }
            return
        }
        if (dialog == null) {
            initDialog()
        }
        dialog?.show()
    }

    fun cancel() {
        if (dialog != null && dialog?.isShowing!!) {
            dialog?.cancel()
        }
        dialog = null
    }

    fun dismiss() {
        if (dialog != null && dialog?.isShowing!!) {
            try {
                dialog?.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
                cancel()
            }
        }
    }

    fun onDestroy() {
        dialog = null
        System.gc()
    }

    fun isCancelable(): Boolean {
        return true
    }

    fun getDimAmount(): Float {
        return 0.7f
    }

    fun getHeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    fun getDialogGravity(): Int {
        return Gravity.CENTER
    }

    /**
     * Dailog的像素宽度
     *
     * @return
     */
    fun getWidth(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }

    override fun onCancel(dialog: DialogInterface?) {
        onDestroy()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        onDestroy()
    }

    abstract fun getLayoutID(): Int

    abstract fun initViews(v: View)
}