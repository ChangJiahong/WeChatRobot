package com.cjh.wechatrobot.service

import com.cjh.wechatrobot.HttpHelper
import com.cjh.wechatrobot.HttpHelper.Companion.QRCODE_RESULT
import org.apache.log4j.Logger
import java.io.File

/**
 *
 * @author ChangJiahong
 * @date 2019/6/5
 */
class WeChatService : IWeChatService {

    val Log = Logger.getLogger(WeChatService::class.java)

    /**
     * 获取uuid
     */
    override fun getUUID(): String{
        var pc = 0
        // 循环计数
        // 超过3次请求失败
        while (pc < 3) {

            Log.debug("第${pc}次尝试请求uuid!")

            val response = HttpHelper.instance.doGet(
                HttpHelper.UUID, mapOf(
                    "appid" to "wx782c26e4c19acffb",
                    "fun" to "new",
                    "lang" to "zh_CN"
                )
            )

            if (response.isSuccessful) {
                val res = response.body!!.string()

                val r4 = Regex("window.QRLogin.code = (\\d+);")

                if (r4.find(res)!!.groupValues[1] == "200"){
                    val r5 = Regex("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";")

                    val (body, code, uuid) = r5.find(res)?.groupValues!!

                    return uuid

                }


            }

        }

        return ""
    }

    /**
     * 获取二维码
     * @param uuid: uuid
     * @return 二维码文件
     */
    override fun getQRCode(uuid: String, fileP: String): File? {

        Log.info("开始获取二维码图片")

        val response = HttpHelper.instance.doGet(HttpHelper.QRCODE + "/$uuid")
        if (response.isSuccessful){
            val inS = response.body!!.byteStream()

            val file = File(fileP)
            if (!file.exists()){
                file.createNewFile()
            }

            inS.use { input ->
                file.outputStream().use { fileOut ->
                    input.copyTo(fileOut)
                }
            }
            Log.info("获取成功，文件路径：${file.absolutePath}")
            return file
        }

        Log.info("获取失败，链接错误！")
        return null
    }

    /**
     * 查询二维码扫描结果
     * @param uuid: uuid
     * @param tip: 0表示等待用户扫码确认；1表示等待用户直接确认，用于push登陆时；
     * @return code, redirect_uri
     */
    override fun queryQRCodeScanResults(uuid: String, tip: Int): Pair<String,String>?{
        var pc = 0
        // 循环计数
        // 超过3次请求失败
        while (pc < 3) {
            val response = HttpHelper.instance.doGet(
                HttpHelper.QRCODE_RESULT,
                mapOf(
                    "uuid" to uuid,
                    "tip" to tip,
                    "loginicon" to "true"
                )
            )

            if (response.isSuccessful) {
                val res = response.body!!.string()

                val r4 = Regex("window.code=(\\d+);")

                var code = r4.find(res)!!.groupValues[1]

                when (code) {
                    "408" -> {
                        Log.debug("未及时扫码，重新请求")
                        pc++
                    }

                    "400","201" ->{
                        return Pair(code, "")
                    }

                    "200" -> {
                        val r5 = Regex("window.code=(\\d+);\\s*window.redirect_uri=\"(\\S+?)\";")

                        val (body, code, redirect_uri) = r5.find(res)?.groupValues!!

                        return Pair(code,redirect_uri)
                    }
                }


            }
        }

        return null
    }
}