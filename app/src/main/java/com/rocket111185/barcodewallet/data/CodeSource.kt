package com.rocket111185.barcodewallet.data

enum class CodeSource(val storageValue: String) {
    MANUAL("manual"),
    SCAN("scan"),
    IMPORT("import");

    companion object {
        fun fromStorageValue(value: String): CodeSource =
            entries.firstOrNull { it.storageValue == value }
                ?: throw IllegalArgumentException("Unknown code source: $value")
    }
}
