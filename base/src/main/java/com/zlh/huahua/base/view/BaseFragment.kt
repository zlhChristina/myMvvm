package com.zlh.huahua.base.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zlh.huahua.base.http.BaseViewModel

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected var binding: V? = null
    protected var viewModel: VM? = null

    /**
     * 当前界面是否可见
     */
    protected var isVisibleToUser: Boolean = false
    /**
     * 是否加载过数据
     */
    protected var isDataInitiated: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(bindContentLayout(), container)
        binding = DataBindingUtil.setContentView(activity!!, bindContentLayout())
        viewModel = initViewModel()
        //让ViewModel拥有View的生命周期感应
        if (viewModel != null) {
            lifecycle.addObserver(viewModel!!)
        }
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel != null) {
            lifecycle.removeObserver(viewModel!!)
            viewModel=null
        }
        binding?.unbind()
    }

    fun showLoading() {
        (activity as BaseActivity<*, *>).showLoading()
    }

    fun hideLoading() {
        (activity as BaseActivity<*, *>).hideLoading()
    }

//    abstract fun onViewCreated()

    abstract fun initViewModel(): VM?

    abstract fun bindContentLayout(): Int
}