package com.example.wan

import com.example.wan.home.bean.ArticleListResponse
import com.example.wan.home.bean.Banner
import com.example.wan.home.bean.Data
import com.example.wan.home.bean.ResultResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryName

interface WanApiService {

    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page:Int):Observable<ResultResponse<Data>>

    @GET("banner/json")
    fun getBannerJson():Observable<ResultResponse<MutableList<Banner>>>
}