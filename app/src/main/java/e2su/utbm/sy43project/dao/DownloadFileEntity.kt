package e2su.utbm.sy43project.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloaded_files")
data class DownloadedFileEntity(
    @PrimaryKey val path: String,
    val name: String,
    val size: Long
)