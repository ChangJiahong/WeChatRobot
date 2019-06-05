package com.cjh.wechatrobot

import okhttp3.Interceptor
import okhttp3.Response
import com.sun.corba.se.spi.presentation.rmi.StubAdapter.request



/**
 * 添加请求头参数 过滤器
 * @author ChangJiahong
 * @date 2019/6/4
 */
class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = request.newBuilder()
            .addHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
            .build()
        return chain.proceed(request)
    }
}