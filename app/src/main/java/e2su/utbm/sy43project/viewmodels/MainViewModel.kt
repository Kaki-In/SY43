package e2su.utbm.sy43project.viewmodels

import androidx.lifecycle.ViewModel
import e2su.utbm.sy43project.api.service.NoobleApi

/*

    The MainViewModel view model contains the data that will be shared toward the different
    composables and views.

    To execute a specific action or retrieve specific data, use the LaunchActionViewModel or the
    RetrieveDataViewModel inside your composable.

 */

class MainViewModel(api: NoobleApi): ViewModel()
{
    private var _api = api
    val selfViewModel = SelfViewModel(api)

    fun createLaunchRequestViewModel(): LaunchActionViewModel
    {
        return LaunchActionViewModel(_api)
    }

    fun<ResponseType> createRetrieveDataViewModel(): RetrieveDataViewModel<ResponseType>
    {
        return RetrieveDataViewModel<ResponseType>(_api)
    }

}

