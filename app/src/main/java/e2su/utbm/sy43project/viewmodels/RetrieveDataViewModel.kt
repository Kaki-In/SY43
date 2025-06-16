package e2su.utbm.sy43project.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.api.service.NoobleApi
import kotlinx.coroutines.launch

/*

    The RetrieveDataViewModel is supposed to be a view model for simple data retrieves.
    It must be called for simple interactions with the server.
    It requires the response type in order to get what has been answered directly inside the state
    itself.

 */

class RetrieveDataViewModel<WaitedResponse>(noobleApi: NoobleApi): ViewModel()
{
    private val _api = noobleApi

    private val _requestState = mutableStateOf<CurrentDataRequestUiState<WaitedResponse>>(CurrentDataRequestUiState.Idle<WaitedResponse>())
    val requestState: State<CurrentDataRequestUiState<WaitedResponse>> = _requestState

    suspend fun retrieveData(action: suspend () -> WaitedResponse)
    {
        viewModelScope.launch {
            _requestState.value = CurrentDataRequestUiState.Loading()

            try {
                val result = action()

                _requestState.value = CurrentDataRequestUiState.Success(result)
            } catch (exc: Exception) {
                _requestState.value = CurrentDataRequestUiState.Error(exc.message.toString())
            }
        }
    }

    fun getNoobleApi(): NoobleApi
    {
        return _api
    }

    fun forget()
    {
        _requestState.value = CurrentDataRequestUiState.Idle()
    }

}

sealed class CurrentDataRequestUiState<WaitedResponse>
{
    class Idle<WaitedResponse>(): CurrentDataRequestUiState<WaitedResponse>()
    class Loading<WaitedResponse>(): CurrentDataRequestUiState<WaitedResponse>()
    class Success<WaitedResponse>(val classData: WaitedResponse): CurrentDataRequestUiState<WaitedResponse>()
    class Error<WaitedResponse>(val reason: String): CurrentDataRequestUiState<WaitedResponse>()
}


