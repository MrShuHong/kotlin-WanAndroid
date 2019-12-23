package com.example.wan

import androidx.multidex.MultiDexApplication
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter


/**
 * Author: shuhong
 * Date: 2019/12/18 16:57
 * Description:
 */
class WanApplication :MultiDexApplication(){


    override fun onCreate() {
        super.onCreate()

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> MaterialHeader(context) }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator{context,_-> ClassicsFooter(context)}
    }
}