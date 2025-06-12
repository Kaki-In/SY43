package e2su.utbm.sy43project.ui.api

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RequestViewModel<RequestType, ResponseType> : ViewModel()
{
    private val _requestState = mutableStateOf<RequestUiState<RequestType, ResponseType>>(RequestUiState.Idle())
    val requestState: State<RequestUiState<RequestType, ResponseType>> = _requestState

    fun fetchRequest(request: RequestType, requestHandler: () -> ResponseType)
    {
        viewModelScope.launch {
            _requestState.value = RequestUiState.Loading(request)

            try {
                val response = requestHandler()

                _requestState.value = RequestUiState.Success(request, response)
            } catch (e: Exception) {
                _requestState.value = RequestUiState.Error(request, e)
            }
        }
    }
}

sealed class RequestUiState<RequestType, ResponseType>
{
    class Idle<RequestType, ResponseType> (): RequestUiState<RequestType, ResponseType>()
    data class Loading<RequestType, ResponseType>(val request: RequestType) : RequestUiState<RequestType, ResponseType>()
    data class Success<RequestType, ResponseType>(val request: RequestType, val response: ResponseType) : RequestUiState<RequestType, ResponseType>()
    data class Error<RequestType, ResponseType>(val request: RequestType, val error: Exception): RequestUiState<RequestType, ResponseType>()
}

