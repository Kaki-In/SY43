package e2su.utbm.sy43project.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.ui.views.ActivityPost
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ActivitiesThreadScreen(
    requestModel: RetrieveDataViewModel<List<NoobleApiActivityModel>>,
    modifier: Modifier = Modifier
) {
    var loadNext by remember {
        mutableStateOf(true)
    }

    var currentLoadedNotifications = remember {
        mutableStateListOf<NoobleApiActivityModel?>()
    }

    var ptrState = rememberPullToRefreshState()

    if (loadNext)
    {
        LaunchedEffect(true) {
            if (currentLoadedNotifications.isEmpty() || currentLoadedNotifications.last() != null)
            {
                requestModel.retrieveData {
                    val activities = requestModel.getNoobleApi().thread.getThread(1, offset = currentLoadedNotifications.size, false)

                    requestModel.getNoobleApi().thread.markAsRead(activities.stream().map { it.activityId }.toList())

                    currentLoadedNotifications.addAll(activities)

                    if (activities.isEmpty()) currentLoadedNotifications.add(null)

                    return@retrieveData activities
                }
            } else {
                ptrState.snapTo(0f)
            }

        }

        loadNext = false
    }

    PullToRefreshBox(
        isRefreshing = requestModel.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            loadNext = true
        },
        state = ptrState,
        modifier = modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            when (requestModel.requestState.value)
            {
                is CurrentDataRequestUiState.Error ->
                {
                    Text("Could not retrieve thread. ")
                }

                else ->
                {
                    Text(
                        "Recent activities",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 20.sp,
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    if (currentLoadedNotifications.isEmpty()) {
                        Text("No activity for the moment")
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (activity in currentLoadedNotifications)
                            {
                                if (activity == null)
                                {
                                    Text("No more notification to display")
                                } else {
                                    ActivityPost(
                                        activity.data.title,
                                        activity.data.date,
                                        activity.data.iconName
                                    )
                                }
                            }

                            if (requestModel.requestState.value is CurrentDataRequestUiState.Loading
                                || requestModel.requestState.value is CurrentDataRequestUiState.Idle)
                            {
                                Text("Loading...")
                            }
                        }
                    }
                }
            }
        }
    }

}

