package com.zlh.huahua.myproject.ui.activity

import android.os.Bundle
import com.zlh.huahua.base.view.BaseActivity
import com.zlh.huahua.myproject.R
import com.zlh.huahua.myproject.databinding.ActivityLoginBinding
import com.zlh.huahua.myproject.viewmodel.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun bindContentLayout(): Int = R.layout.activity_login

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.string_login))
        binding?.loginVm = viewModel
    }

    override fun initViewModel(): LoginViewModel = LoginViewModel(this)

}
