package e2su.utbm.sy43project.repository

import e2su.utbm.sy43project.local.DownloadedFileEntity
import e2su.utbm.sy43project.local.DownloadedFileEntityDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class OfflineDownloadRepository(private val downloadDAO: DownloadedFileEntityDAO) : DownloadRepository {
    override suspend fun getAllDownloadedFiles(): Flow<List<DownloadedFileEntity>> = downloadDAO.getAll()
    override suspend fun getDownloadedFileById(fileId: Long): DownloadedFileEntity? = downloadDAO.getById(fileId).firstOrNull()

    override suspend fun insertDownloadedFile(file: DownloadedFileEntity) = downloadDAO.insert(file)
    override suspend fun updateDownloadedFile(file: DownloadedFileEntity) = downloadDAO.update(file)
    override suspend fun deleteDownloadedFile(file: DownloadedFileEntity) = downloadDAO.delete(file)
}