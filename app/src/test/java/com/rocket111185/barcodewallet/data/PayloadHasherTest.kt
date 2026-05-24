package com.rocket111185.barcodewallet.data

import org.junit.Assert.assertEquals
import org.junit.Test

class PayloadHasherTest {
    @Test
    fun hashesPayloadWithSha256() {
        assertEquals(
            "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
            PayloadHasher.sha256("hello"),
        )
    }
}
