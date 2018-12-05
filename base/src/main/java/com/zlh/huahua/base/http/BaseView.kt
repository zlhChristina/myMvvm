package com.zlh.huahua.base.http

import android.app.Activity

interface BaseView {

    fun showLoading()
    fun hideLoading()

    fun getActivity(): Activity
}