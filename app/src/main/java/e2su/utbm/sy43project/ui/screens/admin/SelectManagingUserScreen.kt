package e2su.utbm.sy43project.ui.screens.admin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.tools.class_wrap.extensions.toImageBitmapDefinedInSY43Context
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import java.time.Month
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectManagingUserScreen (
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onAccountClicked: (String) -> Unit,
    onCreateAccountClicked: () -> Unit
)
{
    var searchingUser by remember {
        mutableStateOf("")
    }

    var loadedUsers by remember {
        mutableStateOf<List<NoobleApiAccountModel?>>(
            if (viewModel.retrieveAllStudentsClassRequest.requestState.value is CurrentDataRequestUiState.Success)
                (viewModel.retrieveAllStudentsClassRequest.requestState.value as CurrentDataRequestUiState.Success).responseData
            else listOf()
        )
    }

    if (viewModel.retrieveAllStudentsClassRequest.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            viewModel.retrieveAllStudentsClassRequest.retrieveData {
                loadedUsers = loadedUsers.filter { it != null }

                val newUsers = viewModel.noobleApi.accounts.searchAccount(searchingUser, 5, loadedUsers.size)

                for (user in newUsers)
                {
                    user.profile.profileImage?.let {
                        user.profile.loadedProfileImage = viewModel.noobleApi.resources.download(it,
                            NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON
                        ).toImageBitmapDefinedInSY43Context()
                    }

                    loadedUsers += user
                }

                if (newUsers.size != 5)
                    loadedUsers += null

                return@retrieveData loadedUsers.filter { it != null }.map { it -> it as NoobleApiAccountModel } + newUsers
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Row{
            Button(
                onClick = { onCreateAccountClicked }
            ) {
                Text("Create account")
            }
        }
        Row{
            Text(text = "All students", fontSize = 24.sp)
            Spacer(Modifier.weight(1f))
        }

        Row {

            TextField(
                searchingUser,
                onValueChange = {
                    searchingUser = it
                }
            )

            Button(
                enabled = viewModel.retrieveAllStudentsClassRequest.requestState.value !is CurrentDataRequestUiState.Loading,
                onClick = {
                    loadedUsers = listOf()
                    viewModel.retrieveAllStudentsClassRequest.forget()
                }
            ) {
                Text("Search")
            }
        }

        for (student in loadedUsers)
        {
            if (student == null)
            {
                break
            }

            Spacer(Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(true)
                    {
                        onAccountClicked(student.id)
                    }
                    .padding(10.dp)
            )
            {
                val profileImage = student.profile.loadedProfileImage

                if (profileImage == null)
                {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Account image",
                        modifier = Modifier.size(60.dp).padding(10.dp)
                    )
                } else {
                    Image(
                        bitmap = profileImage,
                        contentDescription = "Account image",
                        modifier = Modifier.size(60.dp).padding(10.dp)
                    )
                }

                Column (
                    modifier = Modifier.weight(1f)
                )
                {
                    Text(
                        text = "${student.profile.firstName} ${student.profile.lastName}",
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = student.role.description,
                        fontStyle = FontStyle.Italic,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                }
            }
        }

        Spacer(Modifier.height(10.dp))

        if (loadedUsers.isNotEmpty() && loadedUsers[loadedUsers.size -1] == null) {
            Text("No more user found")
        } else if (viewModel.retrieveAllStudentsClassRequest.requestState.value is CurrentDataRequestUiState.Loading) {
            Box (
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                LoadingSpinner()
            }
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
                    .clip(RoundedCornerShape(5.dp))
                    .clickable(true)
                    {
                        viewModel.retrieveAllStudentsClassRequest.forget()
                    }
                    .padding(10.dp)
            )
            {
                Image(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Account image",
                    modifier = Modifier.size(60.dp).padding(10.dp)
                )

                Text(
                    text = "Load more...",
                    fontStyle = FontStyle.Italic,
                    fontSize = 17.sp,
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}