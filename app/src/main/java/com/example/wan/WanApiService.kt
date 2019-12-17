package com.example.wan

import com.example.wan.home.bean.ArticleListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface WanApiService {

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int):Observable<ArticleListResponse>
}