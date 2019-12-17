package com.example.wan.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * Author: shuhong
 * Date: 2019/12/17 17:01
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {

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