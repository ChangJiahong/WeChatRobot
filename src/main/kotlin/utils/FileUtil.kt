package com.cjh.wechatrobot.utils

import java.io.File
import java.io.InputStream

/**
 * 文件操作工具类
 * @author ChangJiahong
 * @date 2019/6/6
 */
object FileUtil {

    /**
     * 写文件
     */
    fun writeToFile(text: String, path: String): File{
        val file = File(path)
        if (!file.parentFile.exists()){
            file.parentFile.mkdirs()
        }
        if (!file.exists()){
            file.createNewFile()
        }
        file.writeText(text)
        return file
    }

    fun writeToFile(fileP: String, inS: InputStream): File {
        val file = File(fileP)

        if (!file.parentFile.exists()) {
            println("文件夹不存在")
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }

        inS.use { input ->
            file.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
        return file
    }

    /**
     * 读文件
     */
    fun readFromFile(path: String): String{
        val file = File(path)
        if (!file.exists()){
            return ""
        }
        return file.readText()
    }
}