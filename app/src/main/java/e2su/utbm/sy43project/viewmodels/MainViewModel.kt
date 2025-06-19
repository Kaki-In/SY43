package e2su.utbm.sy43project.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiFullProfileModel
import e2su.utbm.sy43project.api.models.responses.GetBadgeInfoResponseModel
import e2su.utbm.sy43project.api.models.responses.ListBadgesResponseModel
import e2su.utbm.sy43project.api.service.NoobleApi
import kotlinx.serialization.json.JsonObject

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

    val overviewClassRequest = RetrieveDataViewModel<Pair<NoobleApiClassModel, JsonObject>>(noobleApi)
    val retrieveProfileRequest = RetrieveDataViewModel<NoobleApiFullProfileModel>(noobleApi)
    val retrieveThreadRequest = RetrieveDataViewModel<List<NoobleApiActivityModel>>(noobleApi)
    val retrieveClassesListRequest = RetrieveDataViewModel<List<NoobleApiClassModel>>(noobleApi)

    val getBadgesViewModel = RetrieveDataViewModel<ListBadgesResponseModel>(noobleApi)
    val getDecorationsViewModel = RetrieveDataViewModel<List<NoobleApiDecorationModel>>(noobleApi)

    private val _displayedBadge = mutableStateOf<NoobleApiBadgeModel?>(null)
    val displayedBadge: State<NoobleApiBadgeModel?> = _displayedBadge

    val retrieveClassDataViewModel = RetrieveDataViewModel<Triple<NoobleApiClassModel, List<Pair<String, NoobleApiAccountProfileModel>>, NoobleApiAccountProfileModel>>(noobleApi)

    fun openBadge(badge: NoobleApiBadgeModel)
    {
        _displayedBadge.value = badge
    }

    fun closeBadge()
    {
        _displayedBadge.value = null
    }

}

