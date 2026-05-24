package com.rocket111185.barcodewallet.data

import org.junit.Assert.assertEquals
import org.junit.Test

class CodeTypeConvertersTest {
    private val converters = CodeTypeConverters()

    @Test
    fun convertsCodeFormatToStorageValue() {
        assertEquals("QR_CODE", converters.fromCodeFormat(CodeFormat.QR_CODE))
        assertEquals(CodeFormat.EAN_13, converters.toCodeFormat("EAN_13"))
    }

    @Test
    fun convertsCodeSourceToStorageValue() {
        assertEquals("manual", converters.fromCodeSource(CodeSource.MANUAL))
        assertEquals(CodeSource.SCAN, converters.toCodeSource("scan"))
    }
}
