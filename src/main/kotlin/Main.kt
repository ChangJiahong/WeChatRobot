package com.cjh.wechatrobot

import com.cjh.wechatrobot.impl.WeChat
import com.cjh.wechatrobot.service.WeChatService

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */


fun main() {

    val weChatService = WeChatService()
    WeChat(weChatService).login()


}
