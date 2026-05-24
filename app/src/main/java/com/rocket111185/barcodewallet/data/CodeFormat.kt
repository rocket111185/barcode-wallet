package com.rocket111185.barcodewallet.data

enum class CodeFormat(val storageValue: String) {
    QR_CODE("QR_CODE"),
    EAN_8("EAN_8"),
    EAN_13("EAN_13"),
    UPC_A("UPC_A"),
    UPC_E("UPC_E"),
    CODE_39("CODE_39"),
    CODE_93("CODE_93"),
    CODE_128("CODE_128"),
    ITF("ITF"),
    CODABAR("CODABAR"),
    PDF_417("PDF_417"),
    AZTEC("AZTEC"),
    DATA_MATRIX("DATA_MATRIX");

    companion object {
        fun fromStorageValue(value: String): CodeFormat =
            entries.firstOrNull { it.storageValue == value }
                ?: throw IllegalArgumentException("Unknown code format: $value")
    }
}
