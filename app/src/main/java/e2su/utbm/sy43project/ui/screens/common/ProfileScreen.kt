package e2su.utbm.sy43project.ui.screens.common

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.CircularImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiFullProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.ui.views.ClassPreview
import e2su.utbm.sy43project.ui.views.ProfileBadgesListView
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    mainViewModel: MainViewModel,
    accountId: String?,
    onClassClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val requestViewModel = mainViewModel.retrieveProfileRequest

    if (requestViewModel.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            requestViewModel.retrieveData {
                val information = requestViewModel.getNoobleApi().profiles.getInformation(accountId)

                val classes = mutableListOf<NoobleApiClassModel>()

                for (classId in information.classes!!)
                {
                    classes.add(requestViewModel.getNoobleApi().classes.getData(classId))
                }

                val badges = mutableListOf<NoobleApiBadgeModel>()

                for (badge in information.activeBadges)
                {
                    val response = requestViewModel.getNoobleApi().badges.getInformation(badge.first, badge.second)
                    var thumbnail: ImageBitmap?
                    try {
                        val thumbnailInput = requestViewModel.getNoobleApi().badges.getThumbnail(badge.first, badge.second)
                        thumbnail = BitmapFactory.decodeStream(thumbnailInput).asImageBitmap()
                    } catch ( exc: Exception)
                    {
                        Log.e("ProfileScreen", "could not load badge", exc)
                        thumbnail = null
                    }
                    badges.add(
                        NoobleApiBadgeModel(
                            badge.first,
                            badge.second,
                            response.price,
                            response.title,
                            response.description,
                            response.maxLevel,
                            thumbnail
                        )
                    )
                }

                var decorationModel: NoobleApiDecorationModel?

                val profileDecorationId = information.activeDecoration
                if (profileDecorationId == null)
                {
                    decorationModel = null
                } else {
                    try {
                        val decorationData = requestViewModel.getNoobleApi().decorations.getInformation(profileDecorationId)

                        val thumbnail = requestViewModel.getNoobleApi().resources.download(decorationData.image,
                            NoobleApiResourceType.RESOURCE_TYPE_DECORATION_BANNER)

                        decorationModel = NoobleApiDecorationModel(
                            id = profileDecorationId,
                            price = decorationData.price,
                            name = decorationData.name,
                            imageId = decorationData.image,
                            loadedThumbnail = BitmapFactory.decodeStream(thumbnail).asImageBitmap()
                        )
                    } catch (exc: Exception) {
                        Log.e("ProfileScreen", "could not load decoration", exc)
                        decorationModel = null
                    }

                }

                return@retrieveData NoobleApiFullProfileModel(
                    information,
                    classes,
                    badges,
                    decorationModel
                )
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = requestViewModel.requestState.value is CurrentDataRequestUiState.Loading,
        onRefresh = {
            requestViewModel.forget()
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            when (requestViewModel.requestState.value) {
                is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading -> {
                    Text("Loading...")
                }

                is CurrentDataRequestUiState.Success -> {
                    val (profileData, classesData, badgesData, decorationModel) = (requestViewModel.requestState.value as CurrentDataRequestUiState.Success).responseData

                    Box(
                        contentAlignment = Alignment.CenterStart
                    )
                    {
                        if (decorationModel != null)
                        {
                            val bannerThumbnail = decorationModel.loadedThumbnail

                            if (bannerThumbnail == null)
                            {
                                Text("Could not load thumbnail")
                            } else {
                                Image(
                                    bitmap = bannerThumbnail,
                                    contentDescription = decorationModel.name + " banner image",
                                    modifier = modifier.clip(RoundedCornerShape(4.dp))
                                )
                            }

                        }

                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            val profile = profileData
                            val profileImage = profile.loadedProfileImage

                            if (profileImage == null)
                            {
                                Image(
                                    painter = painterResource(R.drawable.profile),
                                    contentDescription = "User profile image",
                                    modifier = modifier.clip(RoundedCornerShape(4.dp)).size(110.dp).padding(20.dp)
                                )
                            } else {
                                Image(
                                    bitmap = profileImage,
                                    contentDescription = "User profile image",
                                    modifier = modifier.clip(RoundedCornerShape(4.dp)).size(110.dp).padding(20.dp)
                                )
                            }

                            Column (
                                modifier = Modifier.weight(1f)
                            )
                            {
                                Text(
                                    "${profile.firstName} ${profile.lastName}",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = TextStyle(lineHeight = 26.sp),
                                    color = Color.White
                                )
                                Text(
                                    profileData.role!!.description,
                                    fontSize = 10.sp,
                                    fontStyle = FontStyle.Italic,
                                    style = TextStyle(lineHeight = 10.sp),
                                    color = Color.White
                                )

                                ProfileBadgesListView(
                                    badgesData
                                ) {
                                    mainViewModel.openBadge(it)
                                }

                                Text(
                                    profile.description,
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Followed classes :",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (noobleClass in classesData) {
                            ClassPreview(
                                noobleClass,
                                onClassClicked = {
                                    onClassClick(noobleClass.id)
                                }
                            )
                        }
                    }

                }

                is CurrentDataRequestUiState.Error -> {
                    val errorMessage = (requestViewModel.requestState.value as CurrentDataRequestUiState.Error).reason
                    Text("An error occurred : $errorMessage")
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ProfileScreen()
}*/