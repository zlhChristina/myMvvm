package com.zlh.huahua.base.view

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zlh.huahua.base.http.BaseViewModel

abstract class BaseListActivity<V : ViewDataBinding, VM : BaseViewModel, T> : BaseActivity<V, VM>() {

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRcy().layoutManager = getLayoutManager()
        getAdapter().bindToRecyclerView(getRcy())
        if (isLoadMore()) {
            getAdapter().setOnLoadMoreListener({
                page++
                loadData(true)
            }, getRcy())
        }
        getAdapter().isUpFetchEnable = isPullToRefresh()
        if (isPullToRefresh()) {
            getAdapter().setUpFetchListener {
                page = 0
                loadData(false)
            }
        }
    }

    protected open fun loadData(isLoadMore: Boolean) {
    }

    protected open fun isLoadMore(): Boolean {
        return false
    }

    protected open fun isPullToRefresh(): Boolean {
        return false
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    abstract fun getRcy(): RecyclerView

    abstract fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>
}