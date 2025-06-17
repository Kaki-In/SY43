package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.theme.NoobleGreen
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

@Composable
fun NoobleHeader(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onToggleDrawerState: () -> Unit,
    onProfileClick: () -> Unit = { /* Default no-op */ }
) {
    val connectedSelfState = viewModel.selfViewModel.selfState.value as SelfUiState.Connected

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NoobleGreen)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                onToggleDrawerState()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "Nooble",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )

        IconButton(
            onClick = onProfileClick
        ) {
            val profileImage = connectedSelfState.account.profile.loadedProfileImage

            if (profileImage == null)
            {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Icon",
                )
            } else {
                Image(
                    bitmap = profileImage,
                    contentDescription = "Profile Icon",
                )
            }
        }
    }
}