package com.rocket111185.barcodewallet.ui

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.rocket111185.barcodewallet.data.CodeFormat

object BarcodeBitmapRenderer {
    fun render(
        format: CodeFormat,
        payload: String,
        width: Int = defaultWidth(format),
        height: Int = defaultHeight(format),
    ): Bitmap? =
        runCatching {
            val barcodePixels = createPixels(createMatrix(format, payload, width, height))

            Bitmap.createBitmap(
                barcodePixels.width,
                barcodePixels.height,
                Bitmap.Config.ARGB_8888,
            ).apply {
                setPixels(
                    barcodePixels.pixels,
                    0,
                    barcodePixels.width,
                    0,
                    0,
                    barcodePixels.width,
                    barcodePixels.height,
                )
            }
        }.getOrNull()

    internal fun createMatrix(
        format: CodeFormat,
        payload: String,
        width: Int = defaultWidth(format),
        height: Int = defaultHeight(format),
    ): BitMatrix =
        MultiFormatWriter().encode(
            payload,
            format.toZxingFormat(),
            width,
            height,
            mapOf(EncodeHintType.MARGIN to 1),
        )

    internal fun createPixels(matrix: BitMatrix): BarcodePixels {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix[x, y]) Color.BLACK else Color.WHITE
            }
        }

        return BarcodePixels(width, height, pixels)
    }

    internal data class BarcodePixels(
        val width: Int,
        val height: Int,
        val pixels: IntArray,
    )

    private fun defaultWidth(format: CodeFormat): Int =
        when (format) {
            CodeFormat.QR_CODE,
            CodeFormat.AZTEC,
            CodeFormat.DATA_MATRIX,
            -> 360

            CodeFormat.PDF_417 -> 420
            else -> 420
        }

    private fun defaultHeight(format: CodeFormat): Int =
        when (format) {
            CodeFormat.QR_CODE,
            CodeFormat.AZTEC,
            CodeFormat.DATA_MATRIX,
            -> 360

            CodeFormat.PDF_417 -> 180
            else -> 160
        }

    private fun CodeFormat.toZxingFormat(): BarcodeFormat =
        when (this) {
            CodeFormat.QR_CODE -> BarcodeFormat.QR_CODE
            CodeFormat.EAN_8 -> BarcodeFormat.EAN_8
            CodeFormat.EAN_13 -> BarcodeFormat.EAN_13
            CodeFormat.UPC_A -> BarcodeFormat.UPC_A
            CodeFormat.UPC_E -> BarcodeFormat.UPC_E
            CodeFormat.CODE_39 -> BarcodeFormat.CODE_39
            CodeFormat.CODE_93 -> BarcodeFormat.CODE_93
            CodeFormat.CODE_128 -> BarcodeFormat.CODE_128
            CodeFormat.ITF -> BarcodeFormat.ITF
            CodeFormat.CODABAR -> BarcodeFormat.CODABAR
            CodeFormat.PDF_417 -> BarcodeFormat.PDF_417
            CodeFormat.AZTEC -> BarcodeFormat.AZTEC
            CodeFormat.DATA_MATRIX -> BarcodeFormat.DATA_MATRIX
        }
}
