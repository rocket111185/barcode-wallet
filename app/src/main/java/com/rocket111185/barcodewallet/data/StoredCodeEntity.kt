package com.rocket111185.barcodewallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "codes",
    indices = [
        Index(value = ["format", "payload_hash"], unique = true),
        Index(value = ["updated_at"]),
        Index(value = ["is_favorite"]),
    ],
)
data class StoredCodeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "format")
    val format: CodeFormat,

    @ColumnInfo(name = "payload")
    val payload: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "source", defaultValue = "'manual'")
    val source: CodeSource,

    @ColumnInfo(name = "payload_hash")
    val payloadHash: String,

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean,

    @ColumnInfo(name = "archived_at")
    val archivedAt: Long?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,

    @ColumnInfo(name = "last_used_at")
    val lastUsedAt: Long?,
) {
    companion object {
        fun create(
            format: CodeFormat,
            payload: String,
            title: String? = null,
            notes: String? = null,
            source: CodeSource = CodeSource.MANUAL,
            now: Long = System.currentTimeMillis(),
            id: String = UUID.randomUUID().toString(),
        ): StoredCodeEntity =
            StoredCodeEntity(
                id = id,
                format = format,
                payload = payload,
                title = title,
                notes = notes,
                source = source,
                payloadHash = PayloadHasher.sha256(payload),
                isFavorite = false,
                archivedAt = null,
                createdAt = now,
                updatedAt = now,
                lastUsedAt = null,
            )
    }
}
