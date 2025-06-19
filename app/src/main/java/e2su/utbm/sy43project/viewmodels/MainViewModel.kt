package e2su.utbm.sy43project.viewmodels

import androidx.lifecycle.ViewModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.api.models.responses.GetBadgeInfoResponseModel
import e2su.utbm.sy43project.api.models.responses.ListBadgesResponseModel
import e2su.utbm.sy43project.api.service.NoobleApi

/*

    The MainViewModel view model contains the data that will be shared toward the different
    composables and views.

    To execute a specific action or retrieve specific data, use the LaunchActionViewModel or the
    RetrieveDataViewModel inside your composable.

 */

class MainViewModel(val noobleApi: NoobleApi): ViewModel()
{
    val selfViewModel = SelfViewModel(noobleApi)

    val classesRequest = RetrieveDataViewModel<List<NoobleApiClassModel>>(noobleApi)
    val threadRequest = RetrieveDataViewModel<List<NoobleApiActivityModel>>(noobleApi)

    val overviewClassRequest = RetrieveDataViewModel<NoobleApiClassModel>(noobleApi)
    val retrieveProfileRequest = RetrieveDataViewModel<Pair<NoobleApiAccountProfileModel, List<NoobleApiClassModel>>>(noobleApi)
    val retrieveThreadRequest = RetrieveDataViewModel<List<NoobleApiActivityModel>>(noobleApi)
    val retrieveClassesListRequest = RetrieveDataViewModel<List<NoobleApiClassModel>>(noobleApi)

    val getBadgesViewModel = RetrieveDataViewModel<ListBadgesResponseModel>(noobleApi)
    val getDecorationsViewModel = RetrieveDataViewModel<ListBadgesResponseModel>(noobleApi)

}

