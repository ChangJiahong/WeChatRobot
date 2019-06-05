package com.cjh.wechatrobot

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */
interface IWeChat {

    /**
     * 登录
     */
    fun login()

    /**
     * 发送消息
     */
    fun sendMsg(msg: String)

}