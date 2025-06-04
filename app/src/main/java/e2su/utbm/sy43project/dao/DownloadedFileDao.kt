package e2su.utbm.sy43project.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface DownloadedFileDao {
    @Query("SELECT * FROM downloaded_files")
    fun getAll(): Flow<List<DownloadedFileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(file: DownloadedFileEntity)

    @Delete
    suspend fun delete(file: DownloadedFileEntity)
}