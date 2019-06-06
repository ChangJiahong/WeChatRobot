package com.cjh.wechatrobot.utils


import com.google.gson.Gson
import java.io.ByteArrayInputStream
import org.dom4j.io.SAXReader


/**
 * json xml 工具类
 * @author ChangJiahong
 * @date 2019/6/6
 */
object JsonXMLUtil{

    val gson = Gson()

    /**
     * 解析xml TO map
     */
    fun xml2map(xmlStr: String): Map<String, Any>{
        val map = HashMap<String, Any>()
        val ins = ByteArrayInputStream(xmlStr.toByteArray())
        val read = SAXReader()
        val doc = read.read(ins)
        //得到xml根元素
        val root = doc.getRootElement()
        //遍历  得到根元素的所有子节点
        val list = root.elements()
        for (element in list) {
            //装进map
            map.put(element.getName(), element.getText())
        }
        return map
    }

    /**
     * 反序列化
     */
    fun <T> json2Obj(json: String, classT: Class<T>): T{
        return gson.fromJson<T>(json,classT)
    }

    /**
     * 序列化json
     */
    fun obj2Json(obj: Any): String{
        return gson.toJson(obj)
    }


}