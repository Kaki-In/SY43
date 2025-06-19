package e2su.utbm.sy43project.ui.screens.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiActivityModel
import e2su.utbm.sy43project.ui.views.ActivityPost
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.foundation.layout.Row
import e2su.utbm.sy43project.ui.components.CircularImage
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import androidx.compose.foundation.Image
import e2su.utbm.sy43project.R
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ActivitiesThreadScreen(
    requestModel: RetrieveDataViewModel<List<NoobleApiActivityModel>>,
    modifier: Modifier = Modifier
) {
    var loadNext by remember { mutableStateOf(true) }

    var currentLoadedNotifications = remember { mutableStateListOf<NoobleApiActivityModel?>() }

    var ptrState = rememberPullToRefreshState()

    var selectedActivity by remember { mutableStateOf<NoobleApiActivityModel?>(null) }

    if (loadNext)
    {
        LaunchedEffect(true) {
            if (currentLoadedNotifications.isEmpty() || currentLoadedNotifications.last() != null)
            {
                requestModel.retrieveData {
                    val activities = requestModel.getNoobleApi().thread.getThread(1, offset = currentLoadedNotifications.size, false)

                    requestModel.getNoobleApi().thread.markAsRead(activities.stream().map { it.activityId }.toList())

                    currentLoadedNotifications.addAll(activities)

                    if (activities.isEmpty()) currentLoadedNotifications.add(null)

                    return@retrieveData activities
                }
            } else {
                ptrState.snapTo(0f)
            }

        }

        loadNext = false
    }

    PullToRefreshBox(
        isRefreshing = requestModel.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            loadNext = true
        },
        state = ptrState,
        modifier = modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            when (requestModel.requestState.value)
            {
                is CurrentDataRequestUiState.Error ->
                {
                    Text("Could not retrieve thread. ")
                }

                else ->
                {
                    Text(
                        "Recent activities",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 20.sp,
                    )

                    Spacer(
                        Modifier.height(12.dp)
                    )

                    if (currentLoadedNotifications.isEmpty()) {
                        Text("No activity for the moment")
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (activity in currentLoadedNotifications)
                            {
                                if (activity == null)
                                {
                                    Text("No more notification to display")
                                } else {
                                    ActivityPost(
                                        activity.data.title,
                                        activity.data.date,
                                        activity.data.iconName,
                                        onClick = {
                                            selectedActivity = activity
                                        }
                                    )
                                }
                            }

                            if (requestModel.requestState.value is CurrentDataRequestUiState.Loading
                                || requestModel.requestState.value is CurrentDataRequestUiState.Idle)
                            {
                                Text("Loading...")
                            }
                        }
                    }
                }
            }
        }
    }
    selectedActivity?.let { activity ->
        ActivityDetailsDialog(
            requestModel = requestModel,
            activity = activity,
            onDismiss = { selectedActivity = null }
        )
    }
}

@Composable
fun ActivityDetailsDialog(
    requestModel: RetrieveDataViewModel<List<NoobleApiActivityModel>>,
    activity: NoobleApiActivityModel,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    var creatorProfile by remember { mutableStateOf<NoobleApiAccountProfileModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(activity.data.creator) {
        try {
            isLoading = true
            val profileInfo = requestModel.getNoobleApi().profiles.getInformation(activity.data.creator)
            creatorProfile = profileInfo
            isLoading = false
        } catch (e: Exception) {
            error = "Impossible de charger les informations du profil"
            isLoading = false
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // En-tête avec informations du créateur
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Avatar du créateur
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp))
                    } else if (creatorProfile != null) {
                        AsyncImage(
                            model = creatorProfile?.profileImage,
                            contentDescription = "Photo de profil",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        CircularImage(
                            imageRes = R.drawable.profile,
                            size = 48
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Nom et prénom du créateur
                    Column {
                        if (isLoading) {
                            Text("Chargement...")
                        } else if (creatorProfile != null) {
                            Text(
                                "${creatorProfile?.firstName} ${creatorProfile?.lastName}",
                                fontWeight = FontWeight.Bold
                            )
                        } else if (error != null) {
                            Text(error!!)
                        }

                        // Date formatée
                        val datetime = activity.data.date
                        Text(
                            "${datetime}",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Icône du post
                    CircularImage(
                        imageRes = when(activity.data.iconName) {
                            "account" -> R.drawable.profile
                            "class" -> R.drawable.book
                            "role" -> R.drawable.profile
                            else -> R.drawable.bell
                        },
                        size = 40
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                // Titre
                Text(
                    activity.data.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Contenu
                Text(
                    activity.data.content ?: "Pas de contenu disponible",
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Bouton pour fermer
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Fermer")
                }
            }
        }
    }
}
