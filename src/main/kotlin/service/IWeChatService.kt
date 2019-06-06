package com.cjh.wechatrobot.service

import java.io.File

/**
 *
 * @author ChangJiahong
 * @date 2019/6/5
 */
interface IWeChatService {

    /**
     * 获取uuid
     */
    fun getUUID(): String

    /**
     * 获取二维码
     * @param uuid: uuid
     * @return 二维码文件
     */
    fun getQRCode(uuid: String, fileP: String): File?

    /**
     * 查询二维码扫描结果
     * @param uuid: uuid
     * @param tip: 0表示等待用户扫码确认；1表示等待用户直接确认，用于push登陆时；
     * @return code, redirect_uri
     */
    fun queryQRCodeScanResults(uuid: String, tip: Int): Pair<String,String>?

    /**
     * 获取登录参数
     * skey、wxsid、wxuin、pass_ticket
     * @param redirect_uri 重定向
     * @return 参数
     */
    fun getSkey(redirect_uri: String): Map<String, Any>?
}