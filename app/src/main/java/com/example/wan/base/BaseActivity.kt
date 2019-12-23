package com.example.wan.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable


/**
 * Author: shuhong
 * Date: 2019/12/17 17:01
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {

    public  var  compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun getLayoutId(): Int

    protected open fun initView(){}

    protected open fun initData(){}
}