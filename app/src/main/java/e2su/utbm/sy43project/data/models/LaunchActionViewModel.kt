package e2su.utbm.sy43project.data.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.nooble.api.service.NoobleApi
import kotlinx.coroutines.launch

/*

    The LaunchActionViewModel is supposed to be a view model for simple actions launch.
    It must be called for simple actions that does not give any result.

 */

class LaunchActionViewModel(val noobleApi: NoobleApi): ViewModel()
{
    private val _api = noobleApi

    private val _classState = mutableStateOf<CurrentActionUiState>(CurrentActionUiState.Idle)
    val classState: State<CurrentActionUiState> = _classState

    suspend fun retrieveData(action: () -> Unit)
    {
        viewModelScope.launch {
            _classState.value = CurrentActionUiState.Loading

            try {
                val result = action()

                _classState.value = CurrentActionUiState.Success
            } catch (exc: Exception) {
                _classState.value = CurrentActionUiState.Error(exc.message.toString())
            }
        }
    }

}

sealed class CurrentActionUiState
{
    object Idle: CurrentActionUiState()
    object Loading: CurrentActionUiState()
    object Success: CurrentActionUiState()
    class Error(reason: String): CurrentActionUiState()
}


