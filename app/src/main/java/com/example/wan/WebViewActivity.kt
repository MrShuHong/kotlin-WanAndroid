package com.example.wan


import android.os.Handler
import android.os.Message
import com.example.wan.base.BaseActivity
import wendu.dsbridge.DWebView

class WebViewActivity : BaseActivity() {

    private lateinit var mDWebView: DWebView
    override fun getLayoutId(): Int = R.layout.activity_web_view


    override fun initView() {
        super.initView()
        mDWebView = findViewById(R.id.chart_view)
        mDWebView.settings.setAppCacheEnabled(false)//是否使用缓存
        DWebView.setWebContentsDebuggingEnabled(true)
        mDWebView.loadUrl("file:///android_asset/charts/chart.html")

        /*object : Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
            }
        }*/

        var handler = Handler()
        var arr1 = arrayOf("2019-01-01","2019-12-12","ccc")
        handler.postDelayed(object :Runnable{
            override fun run() {
                mDWebView.callHandler("drawLine",arr1)
            }
        },1000)
    }
}
