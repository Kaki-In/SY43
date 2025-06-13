package e2su.utbm.sy43project.data.models

import androidx.lifecycle.ViewModel
import e2su.nooble.api.models.responses.LogInfoResponseModel
import e2su.nooble.api.service.NoobleApi
import e2su.utbm.sy43project.ui.api.RequestViewModel

class MainViewModel(api: NoobleApi): ViewModel()
{

    val selfViewModel = SelfViewModel(api)

}