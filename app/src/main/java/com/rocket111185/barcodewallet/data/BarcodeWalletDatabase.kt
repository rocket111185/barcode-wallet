package com.rocket111185.barcodewallet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [StoredCodeEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(CodeTypeConverters::class)
abstract class BarcodeWalletDatabase : RoomDatabase() {
    abstract fun storedCodeDao(): StoredCodeDao

    companion object {
        private const val DATABASE_NAME = "barcode-wallet.db"

        @Volatile
        private var instance: BarcodeWalletDatabase? = null

        fun getInstance(context: Context): BarcodeWalletDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    BarcodeWalletDatabase::class.java,
                    DATABASE_NAME,
                ).build().also { instance = it }
            }
    }
}
