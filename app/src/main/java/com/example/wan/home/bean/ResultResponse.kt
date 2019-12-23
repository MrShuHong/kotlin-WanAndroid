package com.example.wan.home.bean

class ResultResponse<T> (
    var errorCode: Int,
    var errorMsg: String?,
    var data: T
)