package com.zlh.huahua.base.http

import java.lang.RuntimeException

class BaseException(val code: String, val msg: String?): RuntimeException(msg) {
}