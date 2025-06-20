package e2su.utbm.sy43project.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiSafeModel
import e2su.utbm.sy43project.api.models.responses.ForgotPasswordResponseModel
import e2su.utbm.sy43project.api.service.NoobleApi
import kotlinx.coroutines.launch


/*

    The SelfViewModel contains the data about the connection state, and the used account.

*/

class SelfViewModel(noobleApi: NoobleApi): ViewModel() {
    private val _api = noobleApi

    private val _selfState = mutableStateOf<SelfUiState>(SelfUiState.Unknown)
    val selfState: State<SelfUiState> = _selfState

    val retrieveSafeRequest = RetrieveDataViewModel<NoobleApiSafeModel>(noobleApi)
    val retrieveSentProfileIcons = RetrieveDataViewModel<List<Pair<NoobleApiResourceModel, ImageBitmap>>>(noobleApi)
    val retrieveSentDecorationsBanner = RetrieveDataViewModel<List<NoobleApiDecorationModel>>(noobleApi)
    val uploadProfileIconResource = RetrieveDataViewModel<NoobleApiResourceModel>(noobleApi)
    val retrieveSafeBadgesRequest = RetrieveDataViewModel<List<NoobleApiBadgeModel>>(noobleApi)

    val deleteProfileIconResource = LaunchActionViewModel(noobleApi)
    val updateProfileAction = LaunchActionViewModel(noobleApi)

    suspend fun logout()
    {
        viewModelScope.launch {
            _api.connection.logout()

            _selfState.value = SelfUiState.Disconnected(_api)
        }
    }

    suspend fun login(username: String, password: String)
    {
        viewModelScope.launch {
            try {
                _selfState.value = SelfUiState.Connecting(username, password)

                _api.connection.login(username, password)

                updateConnection(false)
            } catch (exc: Exception) {
                _selfState.value = SelfUiState.CantConnect(username, password, exc.message.toString(), _api)
                Log.e("SelfViewModel", "login:", exc)
            }
        }

    }

    fun forgetConnection()
    {
        _selfState.value = SelfUiState.Unknown
    }

    suspend fun updateConnection(markLoads: Boolean = true)
    {
        viewModelScope.launch {
            try {
                if (markLoads)
                    _selfState.value = SelfUiState.Loading

                val accountInformation = _api.connection.getInformation()

                if (accountInformation == null) {
                    _selfState.value = SelfUiState.Disconnected(_api)
                } else {
                    _selfState.value = SelfUiState.Connected(accountInformation)
                }

                retrieveSafeRequest.forget()
                retrieveSentProfileIcons.forget()
                retrieveSentDecorationsBanner.forget()
                retrieveSafeBadgesRequest.forget()
            } catch (exc: Exception) {
                Log.e("SelfViewModel", "updateConnection: ", exc)
                _selfState.value = SelfUiState.NoInternet
            }
        }
    }

}

sealed class SelfUiState()
{
    object Unknown: SelfUiState()
    class Disconnected(api: NoobleApi): SelfUiState()
    {
        val launchForgotPasswordRequest = RetrieveDataViewModel<ForgotPasswordResponseModel>(api)
    }
    object Loading: SelfUiState()
    class Connecting(val username: String, val password: String): SelfUiState()
    class Connected(val account: NoobleApiAccountModel): SelfUiState()
    class CantConnect(val username: String, val password: String, val message: String, api: NoobleApi): SelfUiState()    {
        val launchForgotPasswordRequest = RetrieveDataViewModel<ForgotPasswordResponseModel>(api)
    }

    object NoInternet: SelfUiState()
}

