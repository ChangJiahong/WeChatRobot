package com.cjh.wechatrobot.impl

import com.cjh.wechatrobot.HttpHelper
import com.cjh.wechatrobot.pojo.IWeChat
import com.cjh.wechatrobot.service.IWeChatService
import com.cjh.wechatrobot.utils.QRCodeUtil
import org.apache.log4j.Logger
import java.io.File
import java.lang.Thread.sleep

/**
 *
 * @author ChangJiahong
 * @date 2019/6/4
 */
class WeChat(val weChatService: IWeChatService) : IWeChat {

    val Log = Logger.getLogger(WeChat::class.java)

    lateinit var redirect_uri: String

    /**
     * 登录
     */
    override fun login() {

        var loging = true
        // uuid 扫码过程
        while (loging) {
            // 请求uuid
            val uuid = weChatService.getUUID()
            // 获取二维码图片
            var qr = weChatService.getQRCode(uuid, "temp.png")

            if (qr != null) {
                // 如果二维码不为空，在控制台显示
                QRCodeUtil.showQR(qr)

                Log.info("请用手机登录微信扫一扫！！。。")

                var tip = 0
                while (true){
                    // 循环查询扫码结果
                    val pair = weChatService.queryQRCodeScanResults(uuid, tip)
                    if (pair != null){
                        val (code, redirect_uri) = pair
                        if (code == "200"){
                            // 成功确认返回，跳出登录过程
                            this.redirect_uri = redirect_uri
                            loging = false
                            break
                        }else if (code == "201"){
                            // 等待确认，延迟3s再次请求
                            tip = 1
                            Log.info("已扫码，正在等待确认！")
                            sleep(3000)
                        }else if (code == "400"){
                            // 此次会话过期，重新请求uuid建立会话
                            Log.info("二维码失效, 重新获取~！")
                            break
                        }
                    }

                }

            } else {
                Log.info("获取二维码失败，重新获取")
            }

        }

        Log.info("登录链接${this.redirect_uri}")

        //TODO: 登录获取参数



    }

    /**
     * 发送消息
     */
    override fun sendMsg(msg: String) {

    }


}