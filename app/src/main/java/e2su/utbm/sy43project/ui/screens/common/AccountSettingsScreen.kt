package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.utbm.sy43project.viewmodels.MainViewModel

@Composable
fun AccountSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    Column (modifier = modifier){
        Text(
            "Account"
        )
    }
}

