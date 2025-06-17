package e2su.nooble.models

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import e2su.utbm.sy43project.local.DownloadedFileEntityDAO
import e2su.utbm.sy43project.local.DownloadedFileEntity
import e2su.utbm.sy43project.repository.DownloadRepository
import e2su.utbm.sy43project.repository.OfflineDownloadRepository
import e2su.utbm.sy43project.local.ListDatabase

@Database(entities = [DownloadedFileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun downloadedFileDao(): DownloadedFileEntityDAO
}

interface AppContainer {
    val downloadRepository: DownloadRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val downloadRepository: DownloadRepository by lazy {
        OfflineDownloadRepository(ListDatabase.getDatabase(context).downloadedFileEntityDAO())
    }
}

