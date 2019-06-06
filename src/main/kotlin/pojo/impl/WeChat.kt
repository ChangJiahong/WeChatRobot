package com.cjh.wechatrobot.impl

import com.cjh.wechatrobot.HttpHelper
import com.cjh.wechatrobot.pojo.IWeChat
import com.cjh.wechatrobot.service.IWeChatService
import com.cjh.wechatrobot.utils.FileUtil
import com.cjh.wechatrobot.utils.JsonXMLUtil
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

    /**
     * 登录状态码
     */
    private var loginStatus: Boolean

    val isLogin
        get() = loginStatus

    var redirect_uri: String
    var skey: String
    var wxsid: String
    var wxuin: String
    var passTicket: String


    init {
        /**
         * 初始化登录状态
         */
        val json = FileUtil.readFromFile("./temp/login.json")
        JsonXMLUtil.json2Obj(json, WeChat::class.java).apply {
            this@WeChat.loginStatus = this.loginStatus
            this@WeChat.skey = this.skey
            this@WeChat.wxsid = this.wxsid
            this@WeChat.wxuin = this.wxuin
            this@WeChat.passTicket = this.passTicket
            this@WeChat.redirect_uri = this.redirect_uri
        }
    }

    /**
     * 登录
     */
    override fun login(): Boolean{
        if (isLogin){
            Log.info("已登录！")
            return true
        }

        var loging = true
        // uuid 扫码过程
        while (loging) {
            // 请求uuid
            val uuid = weChatService.getUUID()
            // 获取二维码图片
            var qr = weChatService.getQRCode(uuid, "./temp/temp.png")

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

        Log.info("登录链接：${this.redirect_uri}")

        val map = weChatService.getSkey(redirect_uri)
        if (map != null && map["ret"] == "0"){
            map.apply {
                skey = this["skey"].toString()
                wxsid = this["wxsid"].toString()
                wxuin = this["wxuin"].toString()
                passTicket = this["pass_ticket"].toString()
            }
            /**
             * 保存登录状态
             */
            FileUtil.writeToFile(this.toString(), "./temp/login.json")

            Log.info("登录成功！")
            return true
        }
        Log.info("登录失败！")
        return false
    }

    /**
     * 发送消息
     */
    override fun sendMsg(msg: String) {

    }

    override fun toString(): String {
        return JsonXMLUtil.obj2Json(this)
    }


}