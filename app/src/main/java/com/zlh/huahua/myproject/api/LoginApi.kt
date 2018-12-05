package com.zlh.huahua.myproject.api

import com.zlh.huahua.base.http.BaseResponse
import com.zlh.huahua.myproject.model.LoginModel
import io.reactivex.Flowable
import okhttp3.RequestBody
import retrofit2.http.*

interface LoginApi {

    @POST("0010102")
    fun login(@Body body:RequestBody): Flowable<BaseResponse<LoginModel?>>
}