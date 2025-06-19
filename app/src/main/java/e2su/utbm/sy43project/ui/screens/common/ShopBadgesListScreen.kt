package e2su.utbm.sy43project.ui.screens.common

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        mutableStateOf<Pair<NoobleApiBadgeModel, Boolean>?>(null)
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

                        for (badge in response.reached.sortedBy { badge -> badge.price })
                        {

                            BadgePreview(
                                reachable = true,
                                badgeModel = badge,
                            )  {
                                openedBadge = Pair(badge, true)
                            }

                        }

                        for (badge in response.unreached.sortedBy { badge -> badge.price })
                        {

                            BadgePreview(
                                reachable = false,
                                badgeModel = badge,
                            ) {
                                openedBadge = Pair(badge, false)
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

        AnimatedVisibility(
            openedBadge != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(true)
                    {
                        openedBadge = null
                    }
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (openedBadge != null)
                {
                    val (badge, reachable) = openedBadge!!
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "Buy this badge?",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = badge.description
                        )

                        Spacer(
                            Modifier.height(20.dp)
                        )

                        BadgePreview (
                            reachable = viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Success && (viewModel.selfViewModel.retrieveSafeRequest.requestState.value as CurrentDataRequestUiState.Success).responseData.quota >= badge.price && reachable,
                            badgeModel = badge
                        ) {}

                        Row (
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Button(
                                enabled = viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Success && (viewModel.selfViewModel.retrieveSafeRequest.requestState.value as CurrentDataRequestUiState.Success).responseData.quota >= badge.price && reachable,
                                onClick = {

                                }
                            ) {
                                Text("Buy this badge")
                            }

                            Spacer(
                                Modifier.width(4.dp)
                            )

                            Button(
                                onClick = {
                                    openedBadge = null
                                },
                                colors = ButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                                )
                            ) {
                                Text("Cancel")
                            }
                        }
                    }
                }
            }
        }
    }
}