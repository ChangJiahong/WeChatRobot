import com.cjh.wechatrobot.utils.QRCodeUtil
import org.apache.log4j.Logger
import org.jetbrains.annotations.TestOnly
import org.junit.Test

/**
 *
 * @author ChangJiahong
 * @date 2019/6/5
 */
class TestMain {

    @Test
    @TestOnly
    fun TestRegex(){
        val res = "window.code=200;\nwindow.redirect_uri=\"https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AVe88aavnNDHU2g7jKZGU11S@qrticket_0&uuid=oYSteXpBkA==&lang=zh_CN&scan=1559746246\";"
        val r4 = Regex("window.code=(\\d+);")

        println(r4.find(res)!!.groupValues)

        val r5 = Regex("window.code=(\\d+);\\s*window.redirect_uri=\"(\\S+?)\";")

        println(r5.find(res)!!.groupValues[2])
    }

    @Test
    fun TestLog(){
        val Log = Logger.getLogger(TestMain::class.java)
        Log.info("info")
        Log.debug("debug")
        Log.error("error")
    }

    @Test
    fun TestQRCode(){
        val url = "https://login.weixin.qq.com/qrcode/AfGr3DYFSw=="
        println(QRCodeUtil.getQr(url))

    }
}