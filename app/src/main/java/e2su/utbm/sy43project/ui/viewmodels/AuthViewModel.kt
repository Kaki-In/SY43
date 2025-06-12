package e2su.utbm.sy43project.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import e2su.nooble.api.models.requests.LoginRequestModel
import e2su.nooble.api.service.NoobleApiRetrofitService
import kotlinx.coroutines.launch
import e2su.nooble.api.service.retrofitService

class AuthViewModel : ViewModel() {
    private val api = retrofitService.create(NoobleApiRetrofitService::class.java)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.logToAccount(LoginRequestModel(username, password))
                // Traite la réponse (ex : sauvegarde du token, navigation, etc.)
            } catch (e: Exception) {
            }
        }
    }
}