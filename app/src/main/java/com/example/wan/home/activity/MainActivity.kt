package com.example.wan.home.activity

import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wan.R
import com.example.wan.base.BaseActivity
import com.example.wan.home.adapter.HomeArticleAdapter
import com.example.wan.utils.http.HttpUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {

    private lateinit var toolbar:Toolbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeArticleAdapter: HomeArticleAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "WanAndroid"

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        homeArticleAdapter = HomeArticleAdapter(R.layout.adapter_home_article)
        recyclerView.adapter = homeArticleAdapter

        var disposable = HttpUtils
            .getApi()
            .getArticleList(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.errorCode == 0) {
                    var datas = it.data.datas
                    if (datas != null) {
                        homeArticleAdapter.addData(datas)
                    }
                }else{
                    Log.d("dsh","errorCode")
                }
            }, {
                it.printStackTrace()
                Log.d("dsh","error")

            })
    }





}
