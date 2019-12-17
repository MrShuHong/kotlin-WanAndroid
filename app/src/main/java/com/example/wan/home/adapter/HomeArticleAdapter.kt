package com.example.wan.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wan.R
import com.example.wan.home.bean.ArticleItem

class HomeArticleAdapter(var layoutId:Int) : BaseQuickAdapter<ArticleItem, BaseViewHolder>(layoutId) {

    override fun convert(helper: BaseViewHolder?, item: ArticleItem?) {

        helper?.setText(R.id.txt_name,item?.title)
        var name = item?.author ?: item?.shareUser
        helper?.setText(R.id.txt_author,name)
    }

}