package e2su.utbm.sy43project.ui.screens.common

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var targetPage by remember { mutableIntStateOf(-1) }
    var pagerState = rememberPagerState { 2 }

    var scope = rememberCoroutineScope()

    if (viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            viewModel.selfViewModel.retrieveSafeRequest.retrieveData {
                viewModel.noobleApi.safe.getWholeSafe()
            }
        }
    }

    if (targetPage != pagerState.currentPage && targetPage != -1)
    {
        LaunchedEffect(true) {
            scope.launch {
                pagerState.animateScrollToPage(targetPage)

                targetPage = -1
            }
        }
    }

    val tabBackgroundColor by animateColorAsState (
        if (pagerState.currentPage == 0)
            MaterialTheme.colorScheme.tertiary
        else
            MaterialTheme.colorScheme.primary,
    )

    PullToRefreshBox (
        isRefreshing = viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            viewModel.selfViewModel.retrieveSafeRequest.forget()
            viewModel.getBadgesViewModel.forget()
        },
        modifier = modifier.fillMaxSize()
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Shop",
                    fontSize = 20.sp,
                    modifier = modifier.padding(4.dp)
                )

                Spacer(Modifier.weight(1f))

                if (viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Success)
                {
                    val userCoins = (viewModel.selfViewModel.retrieveSafeRequest.requestState.value as CurrentDataRequestUiState.Success).responseData.quota

                    Text(
                        text = "$userCoins",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDE8900)
                    )
                } else if (viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Error) {

                    Text(
                        text = "...",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                } else {

                    Text(
                        text = "...",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDE8900)
                    )
                }

                Spacer(Modifier.width(3.dp))

                Image(
                    painter = painterResource(R.drawable.nooblard),
                    contentDescription = "Nooblard Piece Icon",
                    modifier = Modifier.size(18.dp)
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = modifier.weight(1f),
            ) { tabIndex ->
                when (tabIndex)
                {
                    0 ->
                    {
                        ShopBadgesListScreen(
                            viewModel,
                        )
                    }
                }
            }

            TabRow(
                selectedTabIndex = 0,
                modifier = Modifier.background(Color(0)/*MaterialTheme.colorScheme.surface*/, shape = RoundedCornerShape(50.dp)),
                containerColor = Color(0),
                contentColor = MaterialTheme.colorScheme.onSurface,
                indicator = { tabPositions ->
                    val transition = updateTransition(pagerState, label = "Tab Indicator")
                    val indicatorLeft by transition.animateDp(label="Indicator Left")
                    { pagerState ->
                        tabPositions[pagerState.currentPage].left
                    }

                    val indicatorRight by transition.animateDp(label="Indicator Right")
                    { pagerState ->
                        tabPositions[pagerState.currentPage].right
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
                    {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.BottomStart)
                                .offset(x = indicatorLeft)
                                .width(indicatorRight - indicatorLeft)
                                .fillMaxHeight()
                                .background(tabBackgroundColor, RoundedCornerShape(50.dp)),
                        )

                        Text(
                            arrayOf("Badges", "Decorations")[pagerState.currentPage],
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(align = Alignment.BottomStart)
                                .offset(x = tabPositions[pagerState.currentPage].left)
                                .width(tabPositions[pagerState.currentPage].right - tabPositions[pagerState.currentPage].left),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            ){
                Row (horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.clickable(true) {
                        targetPage = 0
                    }
                ){
                    Text("Badges", modifier = Modifier
                        .padding(20.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row (horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.clickable(true) {
                        targetPage = 1
                    }
                ) {
                    Text(
                        "Decorations", modifier = Modifier
                        .padding(20.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}