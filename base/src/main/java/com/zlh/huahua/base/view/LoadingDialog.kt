package com.zlh.huahua.base.view

import android.app.Activity
import android.view.View
import com.zlh.huahua.base.extension.load
import com.zlh.huahua.base.R

class LoadingDialog(activity: Activity) : BaseDialog(activity) {

    override fun getLayoutID(): Int = R.layout.dialog_loading

    override fun initViews(v: View) {
        activity.load(activity, R.drawable.loading, v.findViewById(R.id.iv_loading))
    }

}