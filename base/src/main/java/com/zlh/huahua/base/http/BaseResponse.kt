package com.zlh.huahua.base.http

data class BaseResponse<T>(val head: Head, val body: T) {
}

data class Head(val bzflag: String, val sysflag: String, val errmsg: String) {}