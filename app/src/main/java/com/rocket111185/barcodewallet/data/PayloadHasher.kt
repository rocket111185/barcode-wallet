package com.rocket111185.barcodewallet.data

import java.security.MessageDigest
import kotlin.ExperimentalStdlibApi

object PayloadHasher {
    @OptIn(ExperimentalStdlibApi::class)
    fun sha256(payload: String): String {
        val digest = MessageDigest
            .getInstance("SHA-256")
            .digest(payload.toByteArray(Charsets.UTF_8))

        return digest.toHexString()
    }
}
