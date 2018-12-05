package com.zlh.huahua.base.http

import cn.ejiamall.extension.showShortToast
import com.zlh.huahua.base.util.ContextHolder
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

abstract class BaseSubscriber<T> : Subscriber<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(s: Subscription?) {
        s?.request(Long.MAX_VALUE)
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(t: Throwable?) {
        when (t) {
            is BaseException -> {
                onFail(t.msg!!)
            }
            else -> {
                onException(t)
            }
        }
    }

    open fun onException(e: Throwable?) {
        e?.printStackTrace()
    }

    open fun onSuccess(t: T) {
    }

    open fun onFail(msg: String) {
        ContextHolder.getApplicationContext()?.showShortToast(msg)
    }
}