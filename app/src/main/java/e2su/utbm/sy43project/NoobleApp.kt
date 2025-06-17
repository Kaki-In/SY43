package e2su.utbm.sy43project

import android.app.Application
import androidx.room.Room
import e2su.nooble.models.AppDatabase

class NoobleApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}