package com.benitobertoli.androidplayground.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.benitobertoli.androidplayground.data.persistence.OwnerContract

@Entity(tableName = OwnerContract.TABLE_NAME)
data class OwnerEntity(
    @PrimaryKey
    @ColumnInfo(name = OwnerContract.ID)
    val id: Long,
    @ColumnInfo(name = OwnerContract.NAME)
    val name: String,
    @ColumnInfo(name = OwnerContract.AVATAR)
    val avatar: String?
)