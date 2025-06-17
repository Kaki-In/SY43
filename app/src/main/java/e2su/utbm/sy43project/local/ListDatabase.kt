package e2su.utbm.sy43project.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DownloadedFileEntity::class], version =2, exportSchema = false)
abstract class ListDatabase : RoomDatabase(){

    abstract fun downloadedFileEntityDAO(): DownloadedFileEntityDAO

    companion object {
        @Volatile
        private var Instance : ListDatabase? = null

        fun getDatabase(context : Context): ListDatabase{
            return Instance ?:synchronized(this) {
                Room.databaseBuilder(context, ListDatabase::class.java, "download_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}