package e2su.utbm.sy43project.ui.screens.studentorteacher

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.ui.screens.common.AccountSettingsScreen
import e2su.utbm.sy43project.ui.screens.common.TestScreen
import e2su.utbm.sy43project.viewmodels.LaunchActionViewModel
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentOrTeacherSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
)
{

    var isRefreshing by remember {
        mutableStateOf(false)
    }

    if (isRefreshing)
    {
        LaunchedEffect(3) {
            viewModel.selfViewModel.updateConnection(false)
        }

        isRefreshing = false
    }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = viewModel.selfViewModel.isStillLoadingProfileDetails(),
        onRefresh = {
            isRefreshing = true
        }
    ) {
        AccountSettingsScreen(
            viewModel,
            modifier = modifier
        )
    }

}

