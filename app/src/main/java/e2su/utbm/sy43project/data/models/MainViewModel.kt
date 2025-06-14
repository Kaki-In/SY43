package e2su.utbm.sy43project.data.models

import androidx.lifecycle.ViewModel
import e2su.nooble.api.service.NoobleApi

/*

    The MainViewModel view model contains the data that will be shared toward the different
    composables and views.

    To execute a specific action or retrieve specific data, use the LaunchActionViewModel or the
    RetrieveDataViewModel inside your composable.

 */

class MainViewModel(val api: NoobleApi): ViewModel()
{

    val selfViewModel = SelfViewModel(api)

}

