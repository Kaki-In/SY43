package e2su.utbm.sy43project

import android.app.Application
import androidx.room.Room
import e2su.nooble.models.AppDatabase

class NoobleApp : Application() {
    companion object {
        lateinit var instance: NoobleApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}