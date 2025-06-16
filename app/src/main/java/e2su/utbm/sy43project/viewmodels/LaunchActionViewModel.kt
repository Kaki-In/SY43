package e2su.utbm.sy43project.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.api.service.NoobleApi
import kotlinx.coroutines.launch

/*

    The LaunchActionViewModel is supposed to be a view model for simple actions launch.
    It must be called for simple actions that does not give any result.

 */

class LaunchActionViewModel(noobleApi: NoobleApi): ViewModel()
{
    val TAG = "LaunchAction"

    private val _api = noobleApi

    private val _requestState = mutableStateOf<CurrentActionUiState>(CurrentActionUiState.Idle)
    val requestState: State<CurrentActionUiState> = _requestState

    suspend fun retrieveData(action: suspend () -> Unit)
    {
        viewModelScope.launch {
            _requestState.value = CurrentActionUiState.Loading

            try {
                val result = action()

                _requestState.value = CurrentActionUiState.Success
            } catch (exc: Exception) {
                Log.e(TAG, "Error while launching action\n", exc)
                _requestState.value = CurrentActionUiState.Error(exc.message.toString())
            }
        }
    }

    fun getNoobleApi(): NoobleApi
    {
        return this._api
    }

}

sealed class CurrentActionUiState
{
    object Idle: CurrentActionUiState()
    object Loading: CurrentActionUiState()
    object Success: CurrentActionUiState()
    class Error(val reason: String): CurrentActionUiState()
}


