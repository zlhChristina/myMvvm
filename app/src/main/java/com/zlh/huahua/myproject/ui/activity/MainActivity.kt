package com.zlh.huahua.myproject.ui.activity

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.zlh.huahua.base.http.BaseViewModel
import com.zlh.huahua.base.view.BaseActivity
import com.zlh.huahua.myproject.R

class MainActivity : BaseActivity<ViewDataBinding, BaseViewModel>() {

    override fun initViewModel(): BaseViewModel? = null

    override fun bindContentLayout(): Int = R.layout.activity_main

    override fun onActivityCreate(savedInstanceState: Bundle?) {

    }

    override fun isHasTitleBar(): Boolean {
        return false
    }
}

