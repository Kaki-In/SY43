package e2su.utbm.sy43project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.api.models.objects.NoobleApiRole
import e2su.utbm.sy43project.api.service.NoobleApi
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import e2su.utbm.sy43project.ui.appsides.ConnectedAsAdminAppSide
import e2su.utbm.sy43project.ui.appsides.ConnectedAsStudentOrTeacherAppSide
import e2su.utbm.sy43project.ui.appsides.DisconnectedAppSide
import e2su.utbm.sy43project.ui.appsides.LoadingAppSide
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noobleApi = NoobleApi(this, "https://api.nooble-angular.flopcreation.fr")
        val mainViewModel = MainViewModel(noobleApi)

        setContent {
            SY43ProjectTheme {
                var selfViewModel = mainViewModel.selfViewModel

                when (selfViewModel.selfState.value)
                {
                    is SelfUiState.Unknown ->
                    {
                        LaunchedEffect(key1=true) {
                            selfViewModel.updateConnection()
                        }

                        LoadingAppSide(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is SelfUiState.Loading ->
                    {
                        LoadingAppSide(
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is SelfUiState.Connected ->
                    {
                        val state = selfViewModel.selfState.value as SelfUiState.Connected
                        val account = state.account

                        when (account.role)
                        {
                            NoobleApiRole.ROLE_ADMIN ->
                            {
                                ConnectedAsAdminAppSide(viewModel = mainViewModel)
                            }

                            NoobleApiRole.ROLE_STUDENT ->
                            {
                                ConnectedAsStudentOrTeacherAppSide(viewModel = mainViewModel)
                            }

                            NoobleApiRole.ROLE_TEACHER ->
                            {
                            }

                            NoobleApiRole.ROLE_TEACHER_ADMIN ->
                            {
                            }
                        }
                    }

                    else ->
                    {
                        DisconnectedAppSide(mainViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun MainPage() {
    Text("Bienvenue sur Nooble !")
    Spacer(modifier = Modifier.size(16.dp))
    Button(onClick = {

    }) { Text(
        text = "Page de login"
    ) }
    Spacer(modifier = Modifier.size(16.dp))
    Button(onClick = {

    }) { Text(
        text = "Page de profil"
    ) }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SY43ProjectTheme {
        MainPage()
    }
}