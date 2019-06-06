import com.cjh.wechatrobot.impl.WeChat
import com.cjh.wechatrobot.utils.JsonXMLUtil
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

    @Test
    fun TestXML(){
        val str = "<error>\n" +
                "    <ret>0</ret>\n" +
                "    <message></message>\n" +
                "    <skey>@crypt_jkde99da_b4xxxxxxxxxxxxxxx76d9yualp</skey>\n" +
                "    <wxsid>8rwxxxxxxxxxHq2P</wxsid>\n" +
                "    <wxuin>26xxx7</wxuin>\n" +
                "    <pass_ticket>AFsdZ7eHxxxxxxxxxxxxxxxxfr1vjHPn=</pass_ticket>\n" +
                "    <isgrayscale>1</isgrayscale>\n" +
                "</error>"

        val map = JsonXMLUtil.xml2map(str)

        println(map["ret"])
        println(map["pass_ticket"])

    }

    @Test
    fun TestJson(){
        val json = "{\n" +
                "  \"loginStatus\": true,\n" +
                "  \"redirect_uri\": \"https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AU5u92DnqExex5MgbAy3yYHg@qrticket_0&uuid=Ie9onl47_w==&lang=zh_CN&scan=1559802431\",\n" +
                "  \"skey\": \"@crypt_62c76d76_4e22cf01a1ce443ba248e349ff62df8f\",\n" +
                "  \"wxsid\": \"LUbmHq4I5i7GCrNp\",\n" +
                "  \"wxuin\": \"1476275105\",\n" +
                "  \"passTicket\": \"Kd2YVKhL6MwX%2FLj4sXb3%2FDSOcmT45M5IF9RSxWVVxunzjconJMCxh6Ip0gU%2FDouN\"\n" +
                "}"

        val weChat = JsonXMLUtil.json2Obj(json, WeChat::class.java)
        println(weChat.isLogin)
        println(weChat.passTicket)

    }
}