package com.example.wan.home.activity

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bumptech.glide.Glide
import com.example.wan.R
import com.example.wan.WebViewActivity
import com.example.wan.base.BaseActivity
import com.example.wan.home.adapter.HomeArticleAdapter
import com.example.wan.home.bean.Banner
import com.example.wan.utils.ConvertUtils
import com.example.wan.utils.http.HttpUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainActivity : BaseActivity(), View.OnClickListener, OnRefreshLoadMoreListener {


    private lateinit var toolbar: Toolbar

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeArticleAdapter: HomeArticleAdapter

    private lateinit var convenientBanner: ConvenientBanner<Banner>

    private lateinit var mSmartRefreshLayout: SmartRefreshLayout
    private val bannerList = mutableListOf<Banner>()
    private var page = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        super.initView()
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = "WanAndroid"
        toolbar.setOnClickListener(this)


        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        homeArticleAdapter = HomeArticleAdapter(R.layout.adapter_home_article)
        recyclerView.adapter = homeArticleAdapter


        convenientBanner = ConvenientBanner(this)
        convenientBanner.layoutParams = CoordinatorLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(200f)
        )


        convenientBanner
            .setPages(object : CBViewHolderCreator {

                override fun createHolder(itemView: View?): Holder<*> {
                    return NetworkImageHolderView(itemView)
                }

                override fun getLayoutId(): Int {
                    return R.layout.home_banner_layout
                }
            }, bannerList)
            .setPageIndicator(
                intArrayOf(
                    R.drawable.ic_page_indicator,
                    R.drawable.ic_page_indicator_focused
                )
            )
            //设置指示器的方向
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
        convenientBanner.isCanLoop = true
        homeArticleAdapter.addHeaderView(convenientBanner)

        mSmartRefreshLayout = findViewById(R.id.smart_refresh_layout)
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this)
        mSmartRefreshLayout.autoRefresh()

    }


    private fun getBannerJson(list: MutableList<Banner>) {

        var disposable = HttpUtils.getApi()
            .getBannerJson()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.errorCode == 0) {
                    list.clear()
                    list.addAll(it.data)
                    convenientBanner.notifyDataSetChanged()
                } else {
                    Log.d("dsh", "获取banner数据失败errorCode = ${it.errorCode}")
                }

            }, {
                it.printStackTrace()
                Log.d("dsh", "获取banner数据失败")
            })

        compositeDisposable.add(disposable)
    }

    override fun onResume() {
        super.onResume()
        convenientBanner.startTurning(2000) // 2s 换一张
    }

    override fun onPause() {
        super.onPause()
        convenientBanner.stopTurning()
    }


    private fun getListData(page: Int) {
        var disposable = HttpUtils
            .getApi()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.errorCode == 0) {
                    var datas = it.data.datas
                    if (datas != null) {
                        var state = mSmartRefreshLayout.getState()
                        if (state == RefreshState.Refreshing) {
                            homeArticleAdapter.setNewData(datas)
                            mSmartRefreshLayout.finishRefresh()
                        } else {
                            homeArticleAdapter.addData(datas)
                            mSmartRefreshLayout.finishLoadMore()
                        }

                    }
                } else {
                    Log.d("dsh", "errorCode")
                }
            }, {
                it.printStackTrace()
                Log.d("dsh", "error")

            })

        compositeDisposable.add(disposable)
    }


    override fun onClick(v: View?) {
        startActivity(Intent(this, WebViewActivity::class.java))
    }

    class NetworkImageHolderView(itemView: View?) : Holder<Banner?>(itemView) {
        private var imageView: ImageView? = null
        private var titleView: TextView? = null

        override fun updateUI(data: Banner?) {
            Glide.with(itemView.context)
                .load(data?.imagePath)
                .placeholder(R.mipmap.default_image)
                .into(this!!.imageView!!)
            titleView?.text = data?.title
        }

        override fun initView(itemView: View?) {
            imageView = itemView?.findViewById(R.id.image_view)
            titleView = itemView?.findViewById(R.id.content_view)
            imageView?.setScaleType(ImageView.ScaleType.CENTER_CROP)
        }
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {

        getListData(page++)
        getBannerJson(bannerList)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        getListData(page++)
        getBannerJson(bannerList)
    }

}


