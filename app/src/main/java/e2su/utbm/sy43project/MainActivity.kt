package e2su.utbm.sy43project

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiRole
import e2su.utbm.sy43project.api.service.NoobleApi
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import e2su.utbm.sy43project.ui.appsides.ConnectedAsAdminAppSide
import e2su.utbm.sy43project.ui.appsides.ConnectedAsAdminTeacherAppSide
import e2su.utbm.sy43project.ui.appsides.ConnectedAsStudentOrTeacherAppSide
import e2su.utbm.sy43project.ui.appsides.DisconnectedAppSide
import e2su.utbm.sy43project.ui.appsides.LoadingAppSide
import e2su.utbm.sy43project.ui.popups.BadgePopup
import e2su.utbm.sy43project.ui.popups.UploadFilePopup
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val noobleApi = NoobleApi(this, "https://api.nooble-angular.flopcreation.fr")
        val mainViewModel = MainViewModel(noobleApi)

        setContent {
            SY43ProjectTheme {
                var selfViewModel = mainViewModel.selfViewModel

                Box (
                    modifier = Modifier.fillMaxSize()
                )
                {
                    when (selfViewModel.selfState.value) {
                        is SelfUiState.Unknown -> {
                            LaunchedEffect(key1 = true) {
                                selfViewModel.updateConnection()
                            }

                            LoadingAppSide(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        is SelfUiState.Loading -> {
                            LoadingAppSide(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        is SelfUiState.NoInternet -> {
                            Scaffold() { innerPadding ->
                                Column(
                                    modifier = Modifier.padding(innerPadding)
                                ) {
                                    Text("Could not connect to the API. Please ensure your internet connection and retry.")
                                    Button(
                                        onClick = {
                                            mainViewModel.selfViewModel.forgetConnection()
                                        }
                                    ) {
                                        Text("Rafraichir")
                                    }
                                }

                            }
                        }

                        is SelfUiState.Disconnected, is SelfUiState.CantConnect, is SelfUiState.Connecting -> {
                            DisconnectedAppSide(mainViewModel)
                        }

                        is SelfUiState.Connected -> {
                            val state = selfViewModel.selfState.value as SelfUiState.Connected
                            val account = state.account

                            when (account.role) {
                                NoobleApiRole.ROLE_ADMIN -> {
                                    ConnectedAsAdminAppSide(viewModel = mainViewModel)
                                }

                                NoobleApiRole.ROLE_STUDENT -> {
                                    ConnectedAsStudentOrTeacherAppSide(viewModel = mainViewModel)
                                }

                                NoobleApiRole.ROLE_TEACHER -> {
                                    ConnectedAsStudentOrTeacherAppSide(viewModel = mainViewModel)
                                }

                                NoobleApiRole.ROLE_TEACHER_ADMIN -> {
                                    ConnectedAsAdminTeacherAppSide(viewModel = mainViewModel)
                                }
                            }
                        }

                    }

                    AnimatedVisibility(
                        mainViewModel.displayedBadge.value != null,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        BadgePopup(
                            mainViewModel = mainViewModel,
                            displayedBadge = mainViewModel.displayedBadge.value,
                            onBackClicked = {
                                mainViewModel.closeBadge()
                            }
                        )
                    }

                    AnimatedVisibility(
                        mainViewModel.upLoadingProfileIcon.value != null,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        UploadFilePopup(
                            mainViewModel = mainViewModel,
                            profileIcon = mainViewModel.upLoadingProfileIcon.value,
                            onClose = { success ->
                                mainViewModel.closeUploadFileDialog()

                                if (success)
                                {
                                    mainViewModel.selfViewModel.retrieveSentProfileIcons.forget()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
