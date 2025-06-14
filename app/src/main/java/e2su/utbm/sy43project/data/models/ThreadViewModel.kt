package e2su.utbm.sy43project.data.models

import androidx.lifecycle.ViewModel
import e2su.nooble.api.service.NoobleApi

class ThreadViewModel(noobleApi: NoobleApi): ViewModel() {
    private val _api = noobleApi


}