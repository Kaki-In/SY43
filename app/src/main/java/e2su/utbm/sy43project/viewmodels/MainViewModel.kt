package e2su.utbm.sy43project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.api.service.NoobleApi
import e2su.utbm.sy43project.local.DownloadedFileEntity
import e2su.utbm.sy43project.local.DownloadedFileEntityDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.api.models.responses.ListBadgesResponseModel
import e2su.utbm.sy43project.NoobleApp

class MainViewModel(val noobleApi: NoobleApi) : ViewModel() {
    val selfViewModel = SelfViewModel(noobleApi)

    val classesRequest = RetrieveDataViewModel<List<NoobleApiClassModel>>(noobleApi)
    val threadRequest = RetrieveDataViewModel<List<NoobleApiActivityModel>>(noobleApi)

    val overviewClassRequest = RetrieveDataViewModel<NoobleApiClassModel>(noobleApi)
    val retrieveProfileRequest = RetrieveDataViewModel<Pair<NoobleApiAccountProfileModel, List<NoobleApiClassModel>>>(noobleApi)
    val retrieveThreadRequest = RetrieveDataViewModel<List<NoobleApiActivityModel>>(noobleApi)
    val retrieveClassesListRequest = RetrieveDataViewModel<List<NoobleApiClassModel>>(noobleApi)

    val getBadgesViewModel = RetrieveDataViewModel<ListBadgesResponseModel>(noobleApi)
    val getDecorationsViewModel = RetrieveDataViewModel<ListBadgesResponseModel>(noobleApi)

    val downloadViewModel by lazy {
        DownloadViewModel(NoobleApp.instance.database.downloadedFileDao())
    }
}

class DownloadViewModel(private val dao: DownloadedFileEntityDAO) : ViewModel() {
    private val _downloads = MutableStateFlow<List<DownloadedFileEntity>>(emptyList())
    val downloads: StateFlow<List<DownloadedFileEntity>> = _downloads

    fun getAllDownloads() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getAll().collect { downloadsList ->
                _downloads.value = downloadsList
            }
        }
    }

    fun addDownload(downloadId: Long, filename: String, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val downloadedFile = DownloadedFileEntity(
                id = downloadId,
                fileName = filename,
                filePath = path,
                fileSize = 0L
            )
            dao.insert(downloadedFile)
            getAllDownloads()
        }
    }
}