package com.krishnatune.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.krishnatune.db.daos.RecentSearchDao
import com.krishnatune.db.daos.SpeedDialDao
import com.krishnatune.db.entities.RecentSearchEntity
import com.krishnatune.db.entities.SpeedDialItemEntity

@Database(
    entities = [SpeedDialItemEntity::class, RecentSearchEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun speedDialDao(): SpeedDialDao
    abstract fun recentSearchDao(): RecentSearchDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val current = instance
            if (current != null) {
                return current
            }

            return synchronized(this) {
                val created =
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "saavn.db",
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                instance = created
                created
            }
        }
    }
}
