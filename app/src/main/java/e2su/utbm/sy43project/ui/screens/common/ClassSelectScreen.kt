package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.testing.TestNavHostController
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.ui.components.ClassButton
import e2su.utbm.sy43project.ui.views.ClassPreview
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@Composable
fun ClassSelectScreen(
    viewModel: MainViewModel,
    classesRequestViewModel: RetrieveDataViewModel<List<NoobleApiClassModel>>,
    modifier: Modifier = Modifier
) {
    val selfAccount = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    if (classesRequestViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            classesRequestViewModel.retrieveData {
                val profileInformation = classesRequestViewModel.getNoobleApi().profiles.getInformation(selfAccount.id)

                val classes = mutableListOf<NoobleApiClassModel>()

                for (classId in profileInformation.classes!!)
                {
                    classes.add(classesRequestViewModel.getNoobleApi().classes.getData(classId))
                }

                return@retrieveData classes
            }
        }
    }

    when (classesRequestViewModel.requestState.value)
    {
        is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading ->
        {
            Text("Loading...")
        }

        is CurrentDataRequestUiState.Success ->
        {
            val classesList = (classesRequestViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

            Column (modifier = modifier ) {
                Text(text = "Followed classes", modifier = Modifier.padding(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.padding(16.dp) ,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(classesList) { course ->
                        ClassPreview(
                            course,
                            onClassClicked = {

                            }
                        )
                    }
                }
            }
        }

        is CurrentDataRequestUiState.Error ->
        {
            Text("Error while getting classes")
        }

    }
}

