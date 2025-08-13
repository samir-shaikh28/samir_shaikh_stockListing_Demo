package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingEntity::class], version = 1, exportSchema = false)
abstract class HoldingDatabase : RoomDatabase() {
    abstract fun holdingDao(): HoldingDao
}
