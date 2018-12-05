package com.zlh.huahua.base.http

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.content.Intent
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel(val view: BaseView) : ViewModel(), IBaseViewModel {

    var lifecycleOwner: LifecycleOwner? = null

    fun <T> flowable(flowable: Flowable<BaseResponse<T?>>): Flowable<T> {
        view.showLoading()
        val f = flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return f.map {
            view.hideLoading()
            when (it.head.bzflag) {
                "200" -> {
                    it.body ?: Unit as T
                }
                else -> {
                    throw BaseException(it.head.bzflag, it.head.errmsg)
                }
            }
        }
    }

    fun getActivity(): Activity {
        return view.getActivity()
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        this.lifecycleOwner = null
    }

    //适用于普通的跳转
    fun <T> intent(clazz: Class<T>) {
        val intent = Intent(getActivity(), clazz)
        getActivity().startActivity(intent)
    }
}