package e2su.utbm.sy43project.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import e2su.nooble.api.models.requests.LoginRequestModel
import e2su.nooble.api.models.responses.LoginResponseModel
import e2su.nooble.api.service.NoobleApi
import e2su.utbm.sy43project.ui.api.RequestUiState
import e2su.utbm.sy43project.ui.api.RequestViewModel

@Composable
public fun requestExample(viewModel: RequestViewModel<LoginRequestModel, LoginResponseModel>, nooble_api: NoobleApi) {
    val requestState = viewModel.requestState.value
    var loginState by remember {
        mutableStateOf<LoginResponseModel?>(null)
    }

    if (requestState is RequestUiState.Idle) {
        LaunchedEffect(key1 = true) {
            viewModel.fetchRequest(
                LoginRequestModel("nicolas.daval1@utbm.fr", "motdepasse")
            ) { lrm -> nooble_api.connection.login(lrm.username, lrm.password) }
        }
    }

    when (requestState)
    {
        is RequestUiState.Idle -> {
            Text("Bzzz bzz bzzz")
        }

        is RequestUiState.Loading -> {
            Text("Loading...")
        }

        is RequestUiState.Success -> {
            Text("Connected successfully!")
        }

        is RequestUiState.Error -> {
            Text("An error occurred :" + requestState.error)
        }
    }
}
