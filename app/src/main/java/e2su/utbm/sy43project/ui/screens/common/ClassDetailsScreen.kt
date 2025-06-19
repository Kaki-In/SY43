package e2su.utbm.sy43project.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.ui.navigation.studentorteacher.StudentOrTeacherNavigationManager
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okhttp3.internal.connection.RouteDatabase
import java.time.Month

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailsScreen(
    mainViewModel: MainViewModel,
    classId: String,
    modifier: Modifier = Modifier
) {
    if (mainViewModel.retrieveClassDataViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            mainViewModel.retrieveClassDataViewModel.retrieveData {
                val noobleApi = mainViewModel.noobleApi

                val details = noobleApi.classes.getData(classId)

                val accounts = mutableListOf<Pair<String, NoobleApiAccountProfileModel>>()

                for (account in noobleApi.classes.getAccounts(classId))
                {
                    accounts.add(Pair(account, noobleApi.profiles.getInformation(account, true)))
                }

                val modifierData = noobleApi.profiles.getInformation(details.lastModifier)

                return@retrieveData Triple(details, accounts, modifierData)
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = mainViewModel.retrieveClassDataViewModel.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            mainViewModel.retrieveClassDataViewModel.forget()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        when (mainViewModel.retrieveClassDataViewModel.requestState.value)
        {
            is CurrentDataRequestUiState.Success ->
            {
                val (classData, classAccounts, lastModifier) = (mainViewModel.retrieveClassDataViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                val modificationDateTime = classData.lastModification.toLocalDateTime(TimeZone.UTC)

                Column (
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ){
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

                    Spacer(Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        val modifierImage = lastModifier.loadedProfileImage

                        if (modifierImage == null)
                        {
                            Image(
                                painter = painterResource(R.drawable.profile),
                                contentDescription = "Last modifier image",
                                modifier = Modifier.size(30.dp).padding(5.dp).clickable(true){
                                    StudentOrTeacherNavigationManager.profilePageAction.navigate(classData.lastModifier)
                                }
                            )
                        } else {
                            Image(
                                bitmap = modifierImage,
                                contentDescription = "Last modifier image",
                                modifier = Modifier.size(30.dp).padding(5.dp).clickable(true){
                                    StudentOrTeacherNavigationManager.profilePageAction.navigate(classData.lastModifier)
                                }
                            )
                        }

                        Text(
                            text = "Modified by ${lastModifier.firstName} ${lastModifier.lastName} the ${modificationDateTime.year}/${modificationDateTime.month.value}/${modificationDateTime.dayOfMonth} at  ${modificationDateTime.hour}:${modificationDateTime.minute}",
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )

                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        "Accounts in this class",
                        fontSize = 20.sp
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        for ((accountId, account) in classAccounts) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable(true)
                                    {
                                        StudentOrTeacherNavigationManager.profilePageAction.navigate(accountId)
                                    }
                                    .padding(10.dp)
                            )
                            {
                                val profileImage = account.loadedProfileImage

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

                                Text(
                                    text = "${account.firstName} ${account.lastName}",
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 17.sp,
                                    modifier = Modifier.weight(1f)
                                )

                            }
                        }
                    }
                }
            }

            is CurrentDataRequestUiState.Error ->
            {
                Text("An error occured...")
            }

            else ->
            {
                Text("Loading...")
            }
        }
    }

}



