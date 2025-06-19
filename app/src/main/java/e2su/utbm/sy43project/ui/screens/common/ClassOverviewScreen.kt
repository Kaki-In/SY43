package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.tools.class_wrap.DEFAULT_EXPORTERS_MAPS
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavRoutes
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import kotlinx.serialization.json.JsonObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassOverviewScreen(
    classId: String,
    requestViewModel: RetrieveDataViewModel<Pair<NoobleApiClassModel, JsonObject>>,
    modifier: Modifier = Modifier
) {
    val requestState = requestViewModel.requestState

    if (requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            requestViewModel.retrieveData {
                Pair(requestViewModel.getNoobleApi().classes.getData(classId), requestViewModel.getNoobleApi().classes.getContent(classId))
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            requestViewModel.forget()
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when (requestState.value)
            {
                is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading ->
                {
                    Text("Loading ...")
                }

                is CurrentDataRequestUiState.Success ->
                {
                    val (classData, classContent) = (requestState.value as CurrentDataRequestUiState.Success).responseData

                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {

                        Column (
                            modifier = Modifier.weight(1f)
                        )
                        {
                            Text(
                                text = classData.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = classData.description,
                                fontStyle = FontStyle.Italic,
                                fontSize = 14.sp
                            )

                        }

                        IconButton(
                            onClick = {
                                StudentOrTeacherNavigationManager.classDetailsPageAction.navigate(classId)
                            }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.info),
                                contentDescription = "View profiles icon"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))


                    Column (modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                    ) {

                        DEFAULT_EXPORTERS_MAPS.createView(
                            classContent,
                            modifier = Modifier.fillMaxSize()
                        )

                    }
                }

                is CurrentDataRequestUiState.Error ->
                {
                    Text("An error occurred...")
                }

            }
        }
    }
}
