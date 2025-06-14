package e2su.utbm.sy43project.ui.appsides

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.utbm.sy43project.data.models.MainViewModel
import e2su.utbm.sy43project.ui.screens.LoginScreen


@Composable
fun DisconnectedAppSide(mainModel: MainViewModel, modifier: Modifier = Modifier)
{
    LoginScreen(
        mainModel.selfViewModel,
        modifier = modifier
    )
}

