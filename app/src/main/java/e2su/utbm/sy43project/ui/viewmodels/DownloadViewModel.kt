package e2su.utbm.sy43project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.NoobleApp
import e2su.utbm.sy43project.dao.DownloadedFileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DownloadViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = (application as NoobleApp).database.downloadedFileDao()

    val downloadedFiles: Flow<List<DownloadedFileEntity>> = dao.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}