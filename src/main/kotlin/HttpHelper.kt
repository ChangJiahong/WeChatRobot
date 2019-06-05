package com.cjh.wechatrobot

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.properties.Delegates

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */
class HttpHelper {


    companion object{
        /**
         * 获取uuid
         */
        val UUID = "https://wx.qq.com/jslogin?appid=wx782c26e4c19acffb&fun=new&lang=zh_CN"

        /**
         * 获取二维码
         */
        val QRCODE = "https://login.wx.qq.com/jslogin"


        val okHttpClient = OkHttpClient.Builder().addInterceptor(RequestInterceptor()).build();

        private var mInstance: HttpHelper? = null

        val instance
            get() = mInstance?:run {
                mInstance = HttpHelper()
                mInstance}!!

    }


    private fun <K, V> Map<K, V>.toQueryString(): String = this.map { "${it.key}=${it.value}" }.joinToString("&")


    /**
     * get请求封装
     */
    fun get(url: String, params: Map<String,String>): Response{

        val mUrl = "${url}?${params.toQueryString()}"

        val request = Request.Builder().url(mUrl).get().build()

        val call = okHttpClient.newCall(request)

        val response = call.execute()

        return response
    }




}