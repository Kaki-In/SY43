package e2su.utbm.sy43project.ui.screens.common

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.ui.views.BadgePreview
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShopBadgesListScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var openedBadge by remember {
        mutableStateOf<NoobleApiBadgeModel?>(null)
    }

    if (viewModel.getBadgesViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            viewModel.getBadgesViewModel.retrieveData {
                val badgesList = viewModel.noobleApi.badges.list()

                for (badge in badgesList.reached)
                {
                    val badgeInfo = viewModel.noobleApi.badges.getInformation(badge.name, badge.level)
                    val badgeThumbnail = viewModel.noobleApi.badges.getThumbnail(badge.name, badge.level)

                    badge.maxLevel = badgeInfo.maxLevel
                    badge.loadedThumbnail = BitmapFactory.decodeStream(badgeThumbnail).asImageBitmap()

                }

                for (badge in badgesList.unreached)
                {
                    val badgeInfo = viewModel.noobleApi.badges.getInformation(badge.name, badge.level)
                    val badgeThumbnail = viewModel.noobleApi.badges.getThumbnail(badge.name, badge.level)

                    badge.maxLevel = badgeInfo.maxLevel
                    badge.loadedThumbnail = BitmapFactory.decodeStream(badgeThumbnail).asImageBitmap()

                }

                return@retrieveData badgesList
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            when (viewModel.getBadgesViewModel.requestState.value)
            {
                is CurrentDataRequestUiState.Success ->
                {
                    val response = (viewModel.getBadgesViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                    FlowRow (
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    )
                    {

                        for (badge in response.reached)
                        {

                            BadgePreview(
                                reachable = true,
                                badgeModel = badge,
                            )  {
                                openedBadge = badge
                            }

                        }

                        for (badge in response.unreached)
                        {

                            BadgePreview(
                                reachable = false,
                                badgeModel = badge,
                            ) {
                                openedBadge = badge
                            }

                        }

                    }

                }

                is CurrentDataRequestUiState.Error ->
                {
                    Text("error when loading shop")
                }

                else ->
                {
                    Text("Loading...")
                }
            }
        }

        if (openedBadge != null)
        {
            Column(
                modifier = Modifier.fillMaxSize().background(Color(0x80808080)).padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.height(140.dp).width(20.dp)
                ){

                }
            }
        }

    }
}