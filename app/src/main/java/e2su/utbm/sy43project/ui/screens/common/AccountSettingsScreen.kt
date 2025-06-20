package e2su.utbm.sy43project.ui.screens.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val accountState = viewModel.selfViewModel.selfState.value as SelfUiState.Connected

    val accountData = accountState.account

    val currentlyOwnedDecorations = accountData.safe!!.decorations
    val currentlyOwnedBadges = accountData.safe.badges

    var ownedProfileIcons = listOf<ImageBitmap>()

    if (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value is CurrentDataRequestUiState.Success)
    {
        ownedProfileIcons = (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value as CurrentDataRequestUiState.Success).responseData
    } else if (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value !is CurrentDataRequestUiState.Loading) {
        LaunchedEffect(true) {
            viewModel.selfViewModel.retrieveSentProfileIcons.retrieveData {
                val resources = viewModel.noobleApi.resources.getSelfFiles(NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON)

                val bitmaps = mutableListOf<ImageBitmap>()
                for (resource in resources.sortedBy { it.sentDate })
                {
                    val stream = viewModel.noobleApi.resources.download(resource.id, NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON)
                    bitmaps.add(BitmapFactory.decodeStream(stream).asImageBitmap())
                }

                return@retrieveData bitmaps
            }
        }
    }

    var accountFirstName by remember {
        mutableStateOf(accountData.profile.firstName)
    }

    var accountLastName by remember {
        mutableStateOf(accountData.profile.lastName)
    }

    var accountDescription by remember {
        mutableStateOf(accountData.profile.description)
    }

    var accountProfileImage by remember {
        mutableStateOf(accountData.profile.profileImage)
    }

    var selectedDecoration by remember {
        mutableStateOf<String?>(accountData.profile.activeDecoration)
    }

    val selectedBadges = remember {
        accountData.profile.activeBadges.toMutableStateList()
    }

    Column (modifier = modifier){
        Text(
            "Account",
            fontSize = 20.sp,
            modifier = Modifier.padding(4.dp)
        )

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "First Name",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountFirstName,
                    modifier = Modifier.weight(2f),
                    onValueChange = {
                        accountFirstName = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Last Name",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountLastName,
                    modifier = Modifier.weight(2f),
                    onValueChange = {
                        accountLastName = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Description",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountDescription,
                    maxLines = 1,
                    modifier = Modifier.weight(2f),
                    onValueChange = {
                        accountDescription = it
                    }
                )
            }
        }

        FlowRow ()
        {
            for (icon in ownedProfileIcons)
            {
                Image(
                    bitmap = icon,
                    contentDescription = "A profile image"
                )
            }
        }
    }
}

