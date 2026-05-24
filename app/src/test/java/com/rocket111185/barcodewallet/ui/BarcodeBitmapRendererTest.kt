package com.rocket111185.barcodewallet.ui

import com.rocket111185.barcodewallet.data.CodeFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BarcodeBitmapRendererTest {
    @Test
    fun convertsPdf417MatrixUsingActualMatrixDimensions() {
        val matrix = BarcodeBitmapRenderer.createMatrix(
            format = CodeFormat.PDF_417,
            payload = "Barcode Wallet PDF417 proof of concept",
        )

        val pixels = BarcodeBitmapRenderer.createPixels(matrix)

        assertTrue(matrix.width > 0)
        assertTrue(matrix.height > 0)
        assertEquals(matrix.width, pixels.width)
        assertEquals(matrix.height, pixels.height)
        assertEquals(matrix.width * matrix.height, pixels.pixels.size)
    }
}
