package com.example.wan.home.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.wan.R
import com.example.wan.home.bean.ArticleItem
import org.apache.commons.lang.StringEscapeUtils


class HomeArticleAdapter(var layoutId: Int) :
    BaseQuickAdapter<ArticleItem, BaseViewHolder>(layoutId) {

    override fun convert(helper: BaseViewHolder, item: ArticleItem?) {
        val result = StringEscapeUtils.unescapeHtml(item?.title)
        helper.setText(R.id.txt_name, TextUtils.htmlEncode(result))
        var name = item?.author ?: item?.shareUser
        helper.setText(R.id.txt_author, name)
        helper.setText(R.id.txt_chapter_name, item?.chapterName)
    }


}