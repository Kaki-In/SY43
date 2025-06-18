package e2su.utbm.sy43project.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.ui.views.ActivityPost
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ActivitiesThreadScreen(
    requestModel: RetrieveDataViewModel<List<NoobleApiActivityModel>>,
    modifier: Modifier = Modifier
) {
    var loadedPages by remember {
        mutableIntStateOf(0)
    }

    if (requestModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            requestModel.retrieveData {
                val activities = requestModel.getNoobleApi().thread.getThread(10, offset = loadedPages, false)

                requestModel.getNoobleApi().thread.markAsRead(activities.stream().map { it.activityId }.toList())

                loadedPages = loadedPages + activities.size

                return@retrieveData activities
            }
        }
    }

    when (requestModel.requestState.value)
    {
        is CurrentDataRequestUiState.Loading, is CurrentDataRequestUiState.Idle ->
        {
            LoadingSpinner()
        }

        is CurrentDataRequestUiState.Error ->
        {
            Text("Could not retrieve thread. ")
        }

        is CurrentDataRequestUiState.Success ->
        {
            val activities = (requestModel.requestState.value as CurrentDataRequestUiState.Success).responseData

            Column {
                Text(
                    "Recent activities",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 20.sp,
                )

                Spacer(
                    Modifier.height(12.dp)
                )

                if (activities.isEmpty()) {
                    Text("No activity for the moment")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(activities.size) { activityIndex ->
                            val activity = activities[activityIndex].data

                            ActivityPost(
                                activity.title,
                                activity.date,
                                activity.iconName
                            )
                        }
                    }
                }
            }
        }
    }
}

