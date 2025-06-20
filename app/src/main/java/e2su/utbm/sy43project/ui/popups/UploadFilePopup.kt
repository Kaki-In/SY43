package e2su.utbm.sy43project.ui.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import java.io.File

@Composable
fun UploadFilePopup(
    mainViewModel: MainViewModel,
    profileIcon: Pair<ImageBitmap, File>?,
    onClose: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var startUploading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .clickable(true, onClick = {
                onClose(false)
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (profileIcon == null)
            return

        var resourceName by remember {
            mutableStateOf("")
        }

        val (bitmap, file) = profileIcon

        if (startUploading && mainViewModel.selfViewModel.uploadProfileIconResource.requestState.value !is CurrentDataRequestUiState.Loading)
        {
            LaunchedEffect(true) {
                mainViewModel.selfViewModel.uploadProfileIconResource.retrieveData {
                    val result = mainViewModel.noobleApi.resources.upload(resourceName, file.name, NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON, file)

                    mainViewModel.selfViewModel.retrieveSentProfileIcons.forget()

                    mainViewModel.closeUploadFileDialog()

                    return@retrieveData NoobleApiResourceModel(
                        result.newFileId,
                        resourceName,
                        file.name,
                        result.date,
                        (mainViewModel.selfViewModel.selfState.value as SelfUiState.Connected).account.id,
                        result.size,
                        NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON
                    )
                }

                startUploading = false
            }
        }

        Text("Upload profile image")

        Image(
            bitmap = bitmap,
            contentDescription = "Image waiting to be upload",
            modifier = Modifier.fillMaxWidth().padding(50.dp)
        )

        TextField(
            value = resourceName,
            onValueChange = {
                resourceName = it
            }
        )

        Button(
            enabled = mainViewModel.selfViewModel.uploadProfileIconResource.requestState.value !is CurrentDataRequestUiState.Loading && resourceName != "",
            onClick = {
                startUploading = true
            }
        )
        {
            if (mainViewModel.selfViewModel.uploadProfileIconResource.requestState.value is CurrentDataRequestUiState.Loading)
            {
                Row {
                    Text("Please wait...")
                    LoadingSpinner(20.dp)
                }
            } else {
                Text("Save in my profile")
            }
        }

    }
}

