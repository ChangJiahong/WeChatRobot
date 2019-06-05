package com.cjh.wechatrobot

import com.sun.xml.internal.ws.developer.JAXWSProperties.CONNECT_TIMEOUT
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.apache.log4j.Logger
import java.util.concurrent.TimeUnit

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */
class HttpHelper {

    val Log = Logger.getLogger(HttpHelper::class.java)

    companion object{
        /**
         * 获取uuid
         */
        val UUID = "https://wx.qq.com/jslogin?appid=wx782c26e4c19acffb&fun=new&lang=zh_CN"

        /**
         * 获取二维码
         */
        val QRCODE = "https://login.wx.qq.com/qrcode"

        val QRCODE_RESULT = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login"

        /**
         * okHttp
         */
        val okHttpClient = OkHttpClient.Builder().connectTimeout(5000L, TimeUnit.SECONDS) //连接超时
            .readTimeout(5000L, TimeUnit.SECONDS) //读取超时
            .writeTimeout(5000L, TimeUnit.SECONDS) //写超时
            .addInterceptor(RequestInterceptor()).build();

        /**
         * 单例
         */
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
    fun doGet(url: String): Response{

        val request = Request.Builder().url(url).get().build()

        val call = okHttpClient.newCall(request)

        val response = call.execute()

        return response
    }

    /**
     * get请求封装
     */
    fun doGet(url: String, params: Map<String,Any>): Response{

        val mUrl = "${url}?${params.toQueryString()}"

        return doGet(mUrl)
    }








}