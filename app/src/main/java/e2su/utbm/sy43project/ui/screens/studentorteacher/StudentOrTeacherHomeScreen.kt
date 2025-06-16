package e2su.utbm.sy43project.ui.screens.studentorteacher

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.ui.views.ActivityPost
import e2su.utbm.sy43project.ui.views.ClassPreview
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@Composable
fun StudentOrTeacherHomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val account = (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account

    Column(
        modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            "Hello, ${account.profile.firstName} ${account.profile.lastName}",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .height(2.dp))

        Spacer(modifier = Modifier.height(10.dp))

        val classesRequest = viewModel.createRetrieveDataViewModel<List<NoobleApiClassModel>>()

        StudentOrTeacherClassesOverview(
            classesRequest
        )

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .height(2.dp))

        Spacer(modifier = Modifier.height(10.dp))

        val threadRequest = viewModel.createRetrieveDataViewModel<List<NoobleApiActivityModel>>()

        StudentOrTeacherHomeThreadOverview(
            threadRequest
        )

    }
}

@Composable
fun StudentOrTeacherHomeThreadOverview(
    threadRequest: RetrieveDataViewModel<List<NoobleApiActivityModel>>,
    modifier: Modifier = Modifier
)
{
    if (threadRequest.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            threadRequest.retrieveData {
                val result = threadRequest.getNoobleApi().thread.getThread(10, 0, true)
                return@retrieveData result
            }
        }
    }

    Text(
        "Your recent activities thread",
        fontSize = 20.sp,
        modifier = modifier
    )

    when (threadRequest.requestState.value)
    {
        is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading ->
            Text("Loading...", color = Color.Gray)

        is CurrentDataRequestUiState.Success ->
        {
            val successThreadRequestState = threadRequest.requestState.value as CurrentDataRequestUiState.Success

            if (successThreadRequestState.responseData.isEmpty())
            {
                Text("No unread activity in the activity thread")
            } else {
                Spacer(modifier = Modifier.height(10.dp))
                for (activity in successThreadRequestState.responseData)
                {
                    Spacer(modifier = Modifier.height(10.dp))
                    ActivityPost(activity.data.title, activity.data.date, activity.data.iconName)
                }
            }
        }

        is CurrentDataRequestUiState.Error ->
        {
            val errorState = threadRequest.requestState.value as CurrentDataRequestUiState.Error
            Text("An error occurred while fetching the thread:" + errorState.reason)
        }
    }
}

@Composable
fun StudentOrTeacherClassesOverview(
    classesRequest: RetrieveDataViewModel<List<NoobleApiClassModel>>,
    modifier: Modifier = Modifier
)
{
    if (classesRequest.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            classesRequest.retrieveData {
                val result = classesRequest.getNoobleApi().profiles.getInformation()
                val classes = result.classes!!

                val classesData = mutableListOf<NoobleApiClassModel>()

                for (classId in classes)
                {
                    val classData = classesRequest.getNoobleApi().classes.getData(classId)
                    classesData.add(classData)
                }

                return@retrieveData classesData
            }
        }
    }

    Text(
        "Your assigned classes",
        fontSize = 20.sp,
        modifier = modifier
    )

    when (classesRequest.requestState.value)
    {
        is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading ->
            Text("Loading...", color = Color.Gray)

        is CurrentDataRequestUiState.Success ->
        {
            val successClassesRequestState = classesRequest.requestState.value as CurrentDataRequestUiState.Success

            if (successClassesRequestState.responseData.isEmpty())
            {
                Text("You don't have any assigned class yet")
            } else {
                Spacer(modifier = Modifier.height(10.dp))

                for (classModel in successClassesRequestState.responseData)
                {
                    Spacer(modifier = Modifier.height(10.dp))

                    ClassPreview(
                        classModel,
                        onClassClicked = {
                            StudentOrTeacherNavigationManager.classOverviewPageAction.navigate(classModel.id)
                        }
                    )
                }
            }
        }

        is CurrentDataRequestUiState.Error ->
        {
            val errorState = classesRequest.requestState.value as CurrentDataRequestUiState.Error
            Text("An error occurred while fetching class data:" + errorState.reason)
        }
    }
}



