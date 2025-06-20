package e2su.utbm.sy43project.ui.screens.studentorteacher

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.ui.screens.common.AccountSettingsScreen
import e2su.utbm.sy43project.viewmodels.MainViewModel

@Composable
fun StudentOrTeacherSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
)
{
    Column (modifier = modifier.fillMaxSize())
    {
        Text(
            text = "Settings",
            fontSize = 28.sp,
            modifier = Modifier.padding(4.dp)
        )

        AccountSettingsScreen(
            viewModel
        )
    }

}

