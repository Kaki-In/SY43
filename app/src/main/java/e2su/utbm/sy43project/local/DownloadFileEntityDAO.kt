package e2su.utbm.sy43project.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DownloadedFileEntityDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(downloadedFile: DownloadedFileEntity)

    @Update
    fun update(downloadedFile: DownloadedFileEntity)

    @Delete
    fun delete(downloadedFile: DownloadedFileEntity)

    @Query("SELECT * FROM downloaded_files WHERE id = :id")
    fun getById(id: Long): Flow<DownloadedFileEntity?>

    @Query("SELECT * FROM downloaded_files")
    fun getAll(): Flow<List<DownloadedFileEntity>>
}