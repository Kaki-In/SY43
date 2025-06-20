package e2su.utbm.sy43project.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.ui.views.ClassPreview
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAllClassesScreen(
    classesRequestViewModel: RetrieveDataViewModel<List<NoobleApiClassModel>>,
    onClassClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (classesRequestViewModel.requestState.value is CurrentDataRequestUiState.Idle) {
        LaunchedEffect(true) {
            classesRequestViewModel.retrieveData {
                val allClasses = mutableListOf<NoobleApiClassModel>()
                var offset = 0
                var currentBatch: List<NoobleApiClassModel>

                do {
                    currentBatch = classesRequestViewModel.getNoobleApi().classes.searchClass(
                        pattern = "",
                        count = 1,
                        offset = offset
                    )

                    if (currentBatch.isNotEmpty()) {
                        allClasses.addAll(currentBatch)
                    }
                    offset += 1

                } while (currentBatch.isNotEmpty())

                return@retrieveData allClasses
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = (classesRequestViewModel.requestState.value is CurrentDataRequestUiState.Loading),
        onRefresh = {
            classesRequestViewModel.forget()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        when (classesRequestViewModel.requestState.value) {
            is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading -> {
                Text("Chargement...")
            }

            is CurrentDataRequestUiState.Success -> {
                val classesList = (classesRequestViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                Column (
                    modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Toutes les classes",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 20.sp
                    )

                    for (noobleClass in classesList) {
                        Spacer(
                            Modifier.height(10.dp)
                        )

                        ClassPreview(
                            noobleClass,
                            onClassClicked = {
                                onClassClicked(noobleClass.id)
                            }
                        )
                    }
                }
            }

            is CurrentDataRequestUiState.Error -> {
                Text("Erreur lors du chargement des classes")
            }
        }
    }
}