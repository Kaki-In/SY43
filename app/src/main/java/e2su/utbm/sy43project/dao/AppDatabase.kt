package e2su.utbm.sy43project.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DownloadedFileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadedFileDao(): DownloadedFileDao
}
