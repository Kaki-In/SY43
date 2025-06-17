package e2su.utbm.sy43project.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "downloaded_files")
data class DownloadedFileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fileName: String,
    val filePath: String,
    val fileSize: Long
)