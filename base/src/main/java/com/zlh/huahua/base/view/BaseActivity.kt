package com.zlh.huahua.base.view

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.zlh.huahua.base.R
import com.zlh.huahua.base.http.BaseView
import com.zlh.huahua.base.http.BaseViewModel
import com.zlh.huahua.base.util.AppManager
import com.zlh.huahua.base.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_base.*


abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView {

    private var ivBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvRight: TextView? = null
    private var ivRight: ImageView? = null

    protected var binding: V? = null
    protected var viewModel: VM? = null

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        if (isHasTitleBar() && bindTitleLayout() != 0) {
            LayoutInflater.from(this).inflate(bindTitleLayout(), ll_base)
            ivBack = findViewById(R.id.iv_back)
            ivBack?.setOnClickListener { onBackClick() }
            tvTitle = findViewById(R.id.tv_title)
            ivRight = findViewById(R.id.iv_right)
            ivRight?.setOnClickListener { onRightClick() }
            tvRight = findViewById(R.id.tv_right)
            tvRight?.setOnClickListener { onRightClick() }
        }
        if (bindContentLayout() != 0) {
            LayoutInflater.from(this).inflate(bindContentLayout(), ll_base)
            binding = DataBindingUtil.setContentView(this, bindContentLayout())
        }
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this)
        }
        viewModel = initViewModel()
        if (viewModel != null) {
            lifecycle.addObserver(viewModel!!)
        }
        StatusBarUtil.setStatusBar(this)
        AppManager.addActivity(this)
        onActivityCreate(savedInstanceState)
    }

    override fun getActivity(): Activity {
        return this
    }

    //点击了右边的字体或是图标
    open fun onRightClick() {
    }

    fun setTitle(title: String) {
        tvTitle?.text = title
    }

    fun setRightText(title: String) {
        tvRight?.text = title
    }

    open fun onBackClick() {
        finish()
    }

    open fun bindTitleLayout(): Int {
        return R.layout.base_title_bar
    }

    open fun isHasTitleBar(): Boolean {
        return true
    }

    override fun showLoading() {
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.removeActivity(this)
        binding?.unbind()
        if (viewModel != null) {
            lifecycle.removeObserver(viewModel!!)
            viewModel = null
        }
    }

    abstract fun initViewModel(): VM?

    abstract fun bindContentLayout(): Int

    abstract fun onActivityCreate(savedInstanceState: Bundle?)
}