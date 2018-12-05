package com.zlh.huahua.myproject.viewmodel

import com.zlh.huahua.base.http.BaseViewModel
import android.databinding.ObservableField
import android.text.TextUtils
import cn.ejiamall.extension.showShortToast
import com.zlh.huahua.base.http.BaseSubscriber
import com.zlh.huahua.base.http.BaseView
import com.zlh.huahua.base.http.RetrofitManager
import com.zlh.huahua.myproject.api.LoginApi
import com.zlh.huahua.myproject.model.LoginModel
import com.zlh.huahua.myproject.ui.activity.MainActivity
import okhttp3.RequestBody
import org.json.JSONObject


class LoginViewModel(view: BaseView) : BaseViewModel(view) {

    val phone = ObservableField<String>("18206660050")
    val passWord = ObservableField<String>("123456")

    private val loginApi: LoginApi by lazy { RetrofitManager.create(LoginApi::class.java) }

    fun login() {
        val jsonObject = JSONObject()
        val body = JSONObject()
        body.put("account", passWord.get())
        body.put("passwd", passWord.get())
        jsonObject.put("body", body)
        val requestBody =
            RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())
        when {
            TextUtils.isEmpty(phone.get()) -> getActivity().showShortToast("输入手机号")
            TextUtils.isEmpty(passWord.get()) -> getActivity().showShortToast("输入密码")
            else -> flowable(loginApi.login(requestBody)).subscribe(object :
                BaseSubscriber<LoginModel>() {
                override fun onSuccess(t: LoginModel) {
                    getActivity().showShortToast("登录成功")
                    intent(MainActivity::class.java)
                }
            })
        }
    }
}