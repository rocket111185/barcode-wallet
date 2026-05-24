package com.rocket111185.barcodewallet.data

object StubCodeSeeder {
    suspend fun seedIfEmpty(codeDao: StoredCodeDao) {
        if (codeDao.countCodes() > 0) return

        codeDao.insertCodes(stubCodes())
    }

    private fun stubCodes(now: Long = System.currentTimeMillis()): List<StoredCodeEntity> =
        listOf(
            StoredCodeEntity.create(
                id = "5f4f50c4-bc7d-450d-9eb6-b09e51d81201",
                format = CodeFormat.QR_CODE,
                payload = "https://barcode-wallet.example/demo",
                title = "Demo Website",
                notes = "Stub QR code for the home grid.",
                source = CodeSource.IMPORT,
                now = now,
            ),
            StoredCodeEntity.create(
                id = "8f5a84b4-2675-4d2d-a4bd-aa25d0ff2e02",
                format = CodeFormat.EAN_13,
                payload = "5901234123457",
                title = "Grocery Card",
                notes = "Sample EAN-13 barcode.",
                source = CodeSource.SCAN,
                now = now - 1_000,
            ),
            StoredCodeEntity.create(
                id = "e46ff665-c079-41c9-9e97-9a98c1ed3703",
                format = CodeFormat.CODE_128,
                payload = "BW-DEMO-001",
                title = "Membership",
                notes = "Sample CODE-128 barcode.",
                source = CodeSource.MANUAL,
                now = now - 2_000,
            ),
            StoredCodeEntity.create(
                id = "a1e0e65a-070a-4e48-97a0-ee832914d204",
                format = CodeFormat.UPC_A,
                payload = "036000291452",
                title = "Retail Item",
                notes = "Sample UPC-A barcode.",
                source = CodeSource.SCAN,
                now = now - 3_000,
            ),
            StoredCodeEntity.create(
                id = "fb4e0772-35c0-46bc-ac5d-6045fb0caa05",
                format = CodeFormat.PDF_417,
                payload = "Barcode Wallet PDF417 proof of concept",
                title = "PDF417 Pass",
                notes = "Sample stacked barcode.",
                source = CodeSource.MANUAL,
                now = now - 4_000,
            ),
            StoredCodeEntity.create(
                id = "689542b6-ed68-4e80-8972-7a4b7f9d3306",
                format = CodeFormat.DATA_MATRIX,
                payload = "WIFI:T:WPA;S:BarcodeWallet;P:stub-password;;",
                title = "Wi-Fi Token",
                notes = "Sample Data Matrix code.",
                source = CodeSource.IMPORT,
                now = now - 5_000,
            ),
        )
}
