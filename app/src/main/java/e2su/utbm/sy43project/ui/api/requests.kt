package e2su.utbm.sy43project.ui.api

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RequestViewModel<RequestType, ResponseType> : ViewModel()
{
    private val _requestState = mutableStateOf<RequestUiState<RequestType, ResponseType>>(RequestUiState.Idle())
    val requestState: State<RequestUiState<RequestType, ResponseType>> = _requestState

    fun fetchRequest(request: RequestType, requestHandler: suspend (request: RequestType) -> ResponseType)
    {
        viewModelScope.launch {
            _requestState.value = RequestUiState.Loading(request)

            try {
                val response = requestHandler(request)

                _requestState.value = RequestUiState.Success(request, response)
            } catch (e: Exception) {
                Log.e("API Request View Model", "fetchRequest: ", e)
                _requestState.value = RequestUiState.Error(request, e)
            }
        }
    }

    fun invalidate()
    {
        _requestState.value = RequestUiState.Idle()
    }
}

sealed class RequestUiState<RequestType, ResponseType>
{
    class Idle<RequestType, ResponseType> (): RequestUiState<RequestType, ResponseType>()
    data class Loading<RequestType, ResponseType>(val request: RequestType) : RequestUiState<RequestType, ResponseType>()
    data class Success<RequestType, ResponseType>(val request: RequestType, val response: ResponseType) : RequestUiState<RequestType, ResponseType>()
    data class Error<RequestType, ResponseType>(val request: RequestType, val error: Exception): RequestUiState<RequestType, ResponseType>()
}

