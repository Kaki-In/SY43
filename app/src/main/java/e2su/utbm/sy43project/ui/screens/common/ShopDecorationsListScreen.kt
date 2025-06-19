package e2su.utbm.sy43project.ui.screens.common

import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.ui.views.DecorationPreview
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShopDecorationsListScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var openedDecoration by remember {
        mutableStateOf<NoobleApiDecorationModel?>(null)
    }
    var buyingDecoration by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    if (buyingDecoration)
    {
        LaunchedEffect(true) {
            scope.launch {
                try {
                    viewModel.noobleApi.decorations.buy(openedDecoration!!.id)
                    viewModel.selfViewModel.retrieveSafeRequest.forget()
                    viewModel.getDecorationsViewModel.forget()

                    openedDecoration = null

                    buyingDecoration = false
                } catch (exc: Exception) {
                    Toast.makeText(context, "Error when buying the badge: " + exc.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    if (viewModel.getDecorationsViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            viewModel.getDecorationsViewModel.retrieveData {
                val decorationsList = viewModel.noobleApi.decorations.list()

                for (decoration in decorationsList)
                {

                    try {
                        val decorationThumbnail = viewModel.noobleApi.resources.download(decoration.imageId,
                            NoobleApiResourceType.RESOURCE_TYPE_DECORATION_BANNER)

                        decoration.loadedThumbnail = BitmapFactory.decodeStream(decorationThumbnail).asImageBitmap()
                    } catch (exc: Exception) {

                    }

                }

                return@retrieveData decorationsList
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    )
    {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        )
        {
            when (viewModel.getDecorationsViewModel.requestState.value)
            {
                is CurrentDataRequestUiState.Success ->
                {
                    val decorationsList = (viewModel.getDecorationsViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                    FlowRow (
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    )
                    {
                        for (decoration in decorationsList.sortedBy { decoration -> decoration.price })
                        {
                            DecorationPreview (
                                mainViewModel = viewModel,
                                decorationModel = decoration
                            ) {
                                openedDecoration = decoration
                            }

                            Spacer(Modifier.height(5.dp))
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
            openedDecoration != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(true)
                    {
                        openedDecoration = null
                    }
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (openedDecoration != null)
                {
                    val decoration = openedDecoration!!
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        Text(
                            text = "Buy this decoration?",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Light,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(
                            Modifier.height(20.dp)
                        )

                        DecorationPreview(
                            mainViewModel = viewModel,
                            decoration
                        ) {}

                        Row (
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        {
                            Button(
                                enabled = viewModel.selfViewModel.retrieveSafeRequest.requestState.value is CurrentDataRequestUiState.Success && (viewModel.selfViewModel.retrieveSafeRequest.requestState.value as CurrentDataRequestUiState.Success).responseData.quota >= decoration.price,
                                onClick = {
                                    buyingDecoration = true
                                }
                            ) {
                                Text("Buy this decoration")
                            }

                            Spacer(
                                Modifier.width(4.dp)
                            )

                            Button(
                                onClick = {
                                    openedDecoration = null
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