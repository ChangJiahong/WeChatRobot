package com.cjh.wechatrobot

import com.cjh.wechatrobot.impl.WeChat

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */


fun main() {
    val response = HttpHelper.instance.get(HttpHelper.UUID,
        mapOf("appid" to "wx782c26e4c19acffb",
            "fun" to "new",
            "lang" to "zh_CN"))

    if (response.isSuccessful){
        val re = response.body!!.string()

        println(re)
    }


//    login()
//    login()

}

var weChat: WeChat? = null

fun login() = weChat?:run {
    println("new")
    weChat = WeChat()
    weChat }