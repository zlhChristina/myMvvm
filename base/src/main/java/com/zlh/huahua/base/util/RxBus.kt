package com.zlh.huahua.base.util

import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

object RxBus {

    // 支持背压且线程安全的，保证线程安全需要调用 toSerialized() 方法
    private val mBus:FlowableProcessor<Any> by lazy { PublishProcessor.create<Any>().toSerialized() }

    //发送事件
    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    //订阅事件
    fun <T> toFlowable(c: Class<T>) = mBus.ofType(c)

    fun toFlowable() = mBus

    fun hasSubscribers() = mBus.hasSubscribers()
}