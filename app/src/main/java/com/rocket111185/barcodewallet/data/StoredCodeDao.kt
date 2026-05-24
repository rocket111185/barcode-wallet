package com.rocket111185.barcodewallet.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredCodeDao {
    @Query(
        """
        SELECT * FROM codes
        WHERE archived_at IS NULL
        ORDER BY is_favorite DESC, updated_at DESC
        """,
    )
    fun observeActiveCodes(): Flow<List<StoredCodeEntity>>

    @Query(
        """
        SELECT * FROM codes
        WHERE archived_at IS NULL
        ORDER BY is_favorite DESC, updated_at DESC
        """,
    )
    suspend fun getActiveCodes(): List<StoredCodeEntity>

    @Query("SELECT * FROM codes WHERE id = :id LIMIT 1")
    suspend fun getCode(id: String): StoredCodeEntity?

    @Query("SELECT COUNT(*) FROM codes")
    suspend fun countCodes(): Int

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM codes
            WHERE format = :format AND payload_hash = :payloadHash
            LIMIT 1
        )
        """,
    )
    suspend fun existsByPayloadHash(format: CodeFormat, payloadHash: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCode(code: StoredCodeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCodes(codes: List<StoredCodeEntity>)

    @Update
    suspend fun updateCode(code: StoredCodeEntity)

    @Upsert
    suspend fun upsertCode(code: StoredCodeEntity)

    @Delete
    suspend fun deleteCode(code: StoredCodeEntity)

    @Query(
        """
        UPDATE codes
        SET archived_at = :archivedAt, updated_at = :updatedAt
        WHERE id = :id
        """,
    )
    suspend fun archiveCode(
        id: String,
        archivedAt: Long,
        updatedAt: Long = archivedAt,
    )

    @Query(
        """
        UPDATE codes
        SET archived_at = NULL, updated_at = :updatedAt
        WHERE id = :id
        """,
    )
    suspend fun restoreCode(id: String, updatedAt: Long)

    @Query(
        """
        UPDATE codes
        SET is_favorite = :isFavorite, updated_at = :updatedAt
        WHERE id = :id
        """,
    )
    suspend fun setFavorite(
        id: String,
        isFavorite: Boolean,
        updatedAt: Long,
    )

    @Query(
        """
        UPDATE codes
        SET last_used_at = :lastUsedAt, updated_at = :updatedAt
        WHERE id = :id
        """,
    )
    suspend fun markUsed(
        id: String,
        lastUsedAt: Long,
        updatedAt: Long = lastUsedAt,
    )
}
