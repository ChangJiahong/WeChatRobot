package com.cjh.wechatrobot.pojo

/**
 *
 * @author ChangJiahong
 * @date 2019/6/5
 */
interface IWeChat {

    /**
     * 登录
     */
    fun login(): Boolean

    /**
     * 发送消息
     */
    fun sendMsg(msg: String)


}