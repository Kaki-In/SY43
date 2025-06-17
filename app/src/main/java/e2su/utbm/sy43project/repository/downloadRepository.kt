package e2su.utbm.sy43project.repository

import e2su.utbm.sy43project.local.DownloadedFileEntity
import kotlinx.coroutines.flow.Flow

interface DownloadRepository {
    suspend fun getAllDownloadedFiles(): Flow<List<DownloadedFileEntity>>
    suspend fun getDownloadedFileById(id: Long): DownloadedFileEntity?
    suspend fun insertDownloadedFile(file: DownloadedFileEntity)
    suspend fun updateDownloadedFile(file: DownloadedFileEntity)
    suspend fun deleteDownloadedFile(file: DownloadedFileEntity)
}