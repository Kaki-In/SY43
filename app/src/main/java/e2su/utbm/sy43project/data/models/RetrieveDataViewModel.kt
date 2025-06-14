package e2su.utbm.sy43project.data.models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.nooble.api.service.NoobleApi
import kotlinx.coroutines.launch

/*

    The RetrieveDataViewModel is supposed to be a view model for simple data retrieves.
    It must be called for simple interactions with the server.
    It requires the response type in order to get what has been answered directly inside the state
    itself.

 */

class RetrieveDataViewModel<WaitedResponse>(val noobleApi: NoobleApi): ViewModel()
{
    private val _api = noobleApi

    private val _classState = mutableStateOf<CurrentDataRequestUiState<WaitedResponse>>(CurrentDataRequestUiState.Idle<WaitedResponse>())
    val classState: State<CurrentDataRequestUiState<WaitedResponse>> = _classState

    suspend fun retrieveData(action: () -> WaitedResponse)
    {
        viewModelScope.launch {
            _classState.value = CurrentDataRequestUiState.Loading()

            try {
                val result = action()

                _classState.value = CurrentDataRequestUiState.Success(result)
            } catch (exc: Exception) {
                _classState.value = CurrentDataRequestUiState.Error(exc.message.toString())
            }
        }
    }

}

sealed class CurrentDataRequestUiState<WaitedResponse>
{
    class Idle<WaitedResponse>(): CurrentDataRequestUiState<WaitedResponse>()
    class Loading<WaitedResponse>(): CurrentDataRequestUiState<WaitedResponse>()
    class Success<WaitedResponse>(classData: WaitedResponse): CurrentDataRequestUiState<WaitedResponse>()
    class Error<WaitedResponse>(reason: String): CurrentDataRequestUiState<WaitedResponse>()
}


