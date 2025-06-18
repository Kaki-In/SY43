package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import e2su.nooble.models.ProfileModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.ui.components.ProfileClassClickable
import e2su.utbm.sy43project.ui.views.ClassPreview
import e2su.utbm.sy43project.viewmodels.CurrentActionUiState
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import org.jetbrains.kotlin.util.profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    accountId: String?,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val requestViewModel = mainViewModel.selfViewModel.retrieveProfileRequest

    if (requestViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            requestViewModel.retrieveData {
                val information = requestViewModel.getNoobleApi().profiles.getInformation(accountId)

                val classes = mutableListOf<NoobleApiClassModel>()

                for (classId in information.classes!!)
                {
                    classes.add(requestViewModel.getNoobleApi().classes.getData(classId))
                }

                return@retrieveData Pair(information, classes)
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = requestViewModel.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            requestViewModel.forget()
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            when (requestViewModel.requestState.value) {
                is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading -> {
                    Text("Loading...")
                }

                is CurrentDataRequestUiState.Success -> {
                    val (profileData, classesData) = (requestViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        val profileImage = profileData.loadedProfileImage

                        if (profileImage == null)
                        {
                            CircularImage(
                                imageRes = R.drawable.profile,
                                size = 100
                            )
                        } else {
                            CircularImage(
                                bitmap = profileImage,
                                size = 100
                            )
                        }

                        Column {
                            Text(
                                text = "${profileData.firstName} ${profileData.lastName}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Text(
                                text = profileData.role!!.description,
                                fontStyle = FontStyle.Italic
                            )
                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )
                            Text(
                                text = profileData.description,
                                fontSize = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Followed classes :",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (noobleClass in classesData) {
                            ClassPreview(
                                noobleClass,
                                onClassClicked = {
                                    onClassClick(noobleClass.id)
                                }
                            )
                        }
                    }

                }

                is CurrentDataRequestUiState.Error -> {
                    val errorMessage = (requestViewModel.requestState.value as CurrentDataRequestUiState.Error).reason
                    Text("An error occurred : $errorMessage")
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ProfileScreen()
}*/