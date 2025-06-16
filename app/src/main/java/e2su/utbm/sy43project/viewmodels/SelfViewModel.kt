package e2su.utbm.sy43project.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountModel
import e2su.utbm.sy43project.api.service.NoobleApi
import kotlinx.coroutines.launch

/*

    The SelfViewModel contains the data about the connection state, and the used account.

*/

class SelfViewModel(noobleApi: NoobleApi): ViewModel() {
    private val _api = noobleApi

    private val _selfState = mutableStateOf<SelfUiState>(SelfUiState.Unknown)
    val selfState: State<SelfUiState> = _selfState

    suspend fun logout()
    {
        viewModelScope.launch {
            _api.connection.logout()

            _selfState.value = SelfUiState.Disconnected
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
                _selfState.value = SelfUiState.CantConnect(username, password, exc.message.toString())
                Log.e("SELF", "login:", exc)
            }
        }

    }

    suspend fun updateConnection(markLoads: Boolean = true)
    {
        viewModelScope.launch {
            if (markLoads)
                _selfState.value = SelfUiState.Loading

            val accountInformation = _api.connection.getInformation()

            if (accountInformation == null) {
                _selfState.value = SelfUiState.Disconnected
            } else {
                _selfState.value = SelfUiState.Connected(accountInformation)
            }
        }
    }

}

sealed class SelfUiState()
{
    object Unknown: SelfUiState()
    object Disconnected: SelfUiState()
    object Loading: SelfUiState()
    class Connecting(val username: String, val password: String): SelfUiState()
    class Connected(val account: NoobleApiAccountModel): SelfUiState()
    class CantConnect(val username: String, val password: String, val message: String): SelfUiState()
}

