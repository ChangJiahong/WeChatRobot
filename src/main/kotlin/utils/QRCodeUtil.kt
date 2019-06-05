package com.cjh.wechatrobot.utils

import com.google.zxing.*
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.io.File
import java.util.Hashtable
import java.io.FileInputStream
import javax.imageio.ImageIO
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.EncodeHintType
import org.apache.log4j.Logger
import java.util.EnumMap


/**
 *
 * @author ChangJiahong
 * @date 2019/6/5
 */
object QRCodeUtil {

    val Log = Logger.getLogger(QRCodeUtil::class.java)

    /**
     * 控制台显示二维码
     * @param file: 二维码图片
     */
    fun showQR(file: File){

        val hintMap = EnumMap<DecodeHintType, Any>(DecodeHintType::class.java)
        hintMap[DecodeHintType.CHARACTER_SET] = "UTF-8"

        val qrContent = readQRCode(file, hintMap)

        if (qrContent!=null){
            println(getQr(qrContent))
        }

    }

    /**
     * 生成二维码字符
     * @param text: 二维码内容
     * @return 二维码字符
     */
    fun getQr(text: String): String {
        var s = "生成二维码失败"
        val width = 20
        val height = 20
        // 用于设置QR二维码参数
        val qrParam = Hashtable<EncodeHintType, Any>()
        // 设置QR二维码的纠错级别——这里选择最低L级别
        qrParam[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L
        qrParam[EncodeHintType.CHARACTER_SET] = "utf-8"
        try {
            val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, qrParam)
            s = toAscii(bitMatrix)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return s
    }


    /**
     * 生成二维码字符
     * @param bitMatrix: 二维码矩阵
     * @return 二维码字符
     */
    fun toAscii(bitMatrix: BitMatrix): String {
        val sb = StringBuilder()
        for (rows in 0 until bitMatrix.getHeight()) {
            for (cols in 0 until bitMatrix.getWidth()) {
                val x = bitMatrix.get(rows, cols)
                if (!x) {
                    // white
                    sb.append("\u001b[47m  \u001b[0m")
                } else {
                    sb.append("\u001b[40m  \u001b[0m")
                }
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    /**
     * 读取二维码信息
     *
     * @param filePath 文件路径
     * @param hintMap  hintMap
     * @return 二维码内容
     */
    private fun readQRCode(filePath: File, hintMap: Map<DecodeHintType,Any>): String? {
        try {
            val binaryBitmap = BinaryBitmap(
                HybridBinarizer(
                    BufferedImageLuminanceSource(ImageIO.read(FileInputStream(filePath)))
                )
            )

            val qrCodeResult = MultiFormatReader().decode(binaryBitmap, hintMap)
            return qrCodeResult.text
        } catch (e: Exception) {
            Log.error(e.message)
            return null
        }

    }

}