package e2su.utbm.sy43project.ui.screens.common

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.tools.class_wrap.extensions.toImageBitmapDefinedInSY43Context
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiResourceType
import e2su.utbm.sy43project.ui.components.BadgePreview
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.ui.views.ProfileBadgesListView
import e2su.utbm.sy43project.viewmodels.CurrentActionUiState
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AccountSettingsScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
)
{
    val accountState = viewModel.selfViewModel.selfState.value as SelfUiState.Connected

    val accountData = accountState.account

    val currentlyOwnedDecorations = accountData.safe!!.decorations
    val currentlyOwnedBadges = accountData.safe.badges

    var ownedProfileIcons = listOf<Pair<NoobleApiResourceModel, ImageBitmap>>()

    if (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value is CurrentDataRequestUiState.Success)
    {
        ownedProfileIcons = (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value as CurrentDataRequestUiState.Success).responseData
    } else if (viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value !is CurrentDataRequestUiState.Loading) {
        LaunchedEffect(true) {
            viewModel.selfViewModel.retrieveSentProfileIcons.retrieveData {
                val resources = viewModel.noobleApi.resources.getSelfFiles(NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON)

                val bitmaps = mutableListOf<Pair<NoobleApiResourceModel, ImageBitmap>>()
                for (resource in resources.sortedBy { it.sentDate })
                {
                    val stream = viewModel.noobleApi.resources.download(resource.id, NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON)
                    bitmaps.add(Pair(resource, stream.toImageBitmapDefinedInSY43Context()))
                }

                return@retrieveData bitmaps
            }
        }
    }

    var ownedDecorations = listOf<NoobleApiDecorationModel>()

    if (viewModel.selfViewModel.retrieveSentDecorationsBanner.requestState.value is CurrentDataRequestUiState.Success)
    {
        ownedDecorations = (viewModel.selfViewModel.retrieveSentDecorationsBanner.requestState.value as CurrentDataRequestUiState.Success).responseData
    } else if (viewModel.selfViewModel.retrieveSentDecorationsBanner.requestState.value !is CurrentDataRequestUiState.Loading) {
        LaunchedEffect(38) {
            viewModel.selfViewModel.retrieveSentDecorationsBanner.retrieveData {
                val finalDecorationsList = mutableListOf<NoobleApiDecorationModel>()

                for (decoration in currentlyOwnedDecorations)
                {
                    val decorationData = viewModel.noobleApi.decorations.getInformation(decoration)
                    finalDecorationsList.add(
                        NoobleApiDecorationModel(
                            decoration,
                            decorationData.price,
                            decorationData.name,
                            decorationData.image,
                            viewModel.noobleApi.resources.download(decorationData.image,
                                NoobleApiResourceType.RESOURCE_TYPE_DECORATION_BANNER).toImageBitmapDefinedInSY43Context()
                        ),
                    )
                }

                return@retrieveData finalDecorationsList
            }
        }
    }

    var ownedBadges = listOf<NoobleApiBadgeModel>()

    if (viewModel.selfViewModel.retrieveSafeBadgesRequest.requestState.value is CurrentDataRequestUiState.Success)
    {
        ownedBadges = (viewModel.selfViewModel.retrieveSafeBadgesRequest.requestState.value as CurrentDataRequestUiState.Success).responseData
    } else if (viewModel.selfViewModel.retrieveSentDecorationsBanner.requestState.value !is CurrentDataRequestUiState.Loading) {
        LaunchedEffect(38) {
            viewModel.selfViewModel.retrieveSafeBadgesRequest.retrieveData {
                val finalBadgesList = mutableListOf<NoobleApiBadgeModel>()

                for (badge in currentlyOwnedBadges)
                {
                    val badgeData = viewModel.noobleApi.badges.getInformation(badge.first, badge.second)
                    finalBadgesList.add(
                        NoobleApiBadgeModel(
                            badge.first,
                            badge.second,
                            badgeData.price,
                            badgeData.title,
                            badgeData.description,
                            badgeData.maxLevel,
                            viewModel.noobleApi.badges.getThumbnail(
                                badge.first,
                                badge.second
                            ).toImageBitmapDefinedInSY43Context()
                        ),
                    )
                }

                return@retrieveData finalBadgesList
            }
        }
    }

    var accountFirstName by remember {
        mutableStateOf(accountData.profile.firstName)
    }

    var accountLastName by remember {
        mutableStateOf(accountData.profile.lastName)
    }

    var accountDescription by remember {
        mutableStateOf(accountData.profile.description)
    }

    var selectedProfileImage by remember {
        mutableStateOf(accountData.profile.profileImage)
    }

    var selectedDecoration by remember {
        mutableStateOf<String?>(accountData.profile.activeDecoration)
    }

    val selectedBadges = remember {
        accountData.profile.activeBadges.stream().map { badge -> badge.first }.toList().toMutableStateList()
    }

    val localContext = LocalContext.current

    var deletingProfileIcon by remember {
        mutableStateOf<Triple<NoobleApiResourceModel, ImageBitmap, Boolean>?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.openUploadFileDialog(localContext, it)
        }
    }

    var saving by remember {
        mutableStateOf(false)
    }

    if (saving)
    {
        LaunchedEffect(2049) {
            viewModel.selfViewModel.updateProfileAction.execute {
                viewModel.noobleApi.profiles.update(
                    firstName = accountFirstName,
                    lastName = accountLastName,
                    description = accountDescription,
                    activeBadges = selectedBadges,
                    activeDecoration = selectedDecoration,
                    profileImage = selectedProfileImage
                )

                viewModel.selfViewModel.updateConnection(false)
            }
        }

        saving = false
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Row {
            Text(
                "Account",
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp)
            )

            Spacer(Modifier.weight(1f))

            Button(
                enabled = viewModel.selfViewModel.updateProfileAction.requestState.value !is CurrentActionUiState.Loading,
                onClick = {
                    saving = true
                }
            ) {
                Text("Save")
            }

        }

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "First Name",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountFirstName,
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                    onValueChange = {
                        accountFirstName = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Last Name",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountLastName,
                    modifier = Modifier.weight(2f),
                    singleLine = true,
                    onValueChange = {
                        accountLastName = it
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Description",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )

                TextField(
                    accountDescription,
                    singleLine = true,
                    modifier = Modifier.weight(2f),
                    onValueChange = {
                        accountDescription = it
                    }
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Displayed profile picture",
            fontSize = 16.sp
        )

        Row (
            modifier = Modifier.horizontalScroll(rememberScrollState())
        )
        {
            ImageSelection(
                "No profile picture",
                active = accountData.profile.profileImage == null,
                selected = selectedProfileImage == null,
                onClick = {
                    selectedProfileImage = if (selectedProfileImage == null) {
                        accountData.profile.profileImage
                    } else {
                        null
                    }
                },
            )
            {
                Image(
                    painter = painterResource(R.drawable.no_image),
                    contentDescription = "no image",
                    modifier = it
                )
            }

            Row (
                modifier.animateContentSize()
            ){
                if (
                    viewModel.selfViewModel.retrieveSentProfileIcons.requestState.value is CurrentDataRequestUiState.Loading
                )
                {
                    ImageSelection(
                        "Loading profiles pictures...",
                        active = accountData.profile.profileImage != null,
                        selected = selectedProfileImage != null,
                        onClick = {}
                    )
                    { modifier ->
                        Box(
                            modifier = Modifier.padding(20.dp)
                        )
                        {
                            LoadingSpinner(size = 30.dp)
                        }
                    }
                }

                for ((resource, icon) in ownedProfileIcons)
                {
                    ImageSelection(
                        resource.name,
                        active = resource.id == accountData.profile.profileImage,
                        selected = selectedProfileImage == resource.id,
                        onClick = {
                            if (selectedProfileImage == resource.id)
                            {
                                selectedProfileImage = accountData.profile.profileImage
                            } else {
                                selectedProfileImage = resource.id
                            }
                        },
                        onLongClick = {
                            deletingProfileIcon = Triple(resource, icon, false)
                        }
                    )
                    {
                        Image(
                            bitmap = icon,
                            contentDescription = "Profile image",
                            modifier = it
                        )
                    }

                }


            }
            ImageSelection(
                "Add another image...",
                active = false,
                selected = false,
                onClick = {
                    launcher.launch("image/*")
                }

            )
            {
                Image(
                    painter = painterResource(R.drawable.add_picture),
                    contentDescription = "no image",
                    modifier = it
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Displayed profile banner",
            fontSize = 16.sp
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            DecorationSelection(
                mainViewModel = viewModel,
                decorationModel = null,
                active = accountData.profile.activeDecoration == null,
                selected = selectedDecoration == null
            ) {
                selectedDecoration = if (selectedDecoration == null) {
                    accountData.profile.activeDecoration
                } else {
                    null
                }
            }

            if (viewModel.selfViewModel.retrieveSentDecorationsBanner.requestState.value is CurrentDataRequestUiState.Loading)
            {
                Text("Loading your decorations, please wait...")
            }

            for (decorationModel in ownedDecorations)
            {
                Spacer(Modifier.height(10.dp))

                DecorationSelection(
                    mainViewModel = viewModel,
                    decorationModel = decorationModel,
                    active = accountData.profile.activeDecoration == decorationModel.id,
                    selected = decorationModel.id == selectedDecoration
                )
                {
                    selectedDecoration = if (selectedDecoration == decorationModel.id) {
                        accountData.profile.activeDecoration
                    } else {
                        decorationModel.id
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Active badges",
            fontSize = 16.sp
        )

        Row (
            Modifier.horizontalScroll(rememberScrollState())
        )
        {

            for (badge in ownedBadges)
            {
                BadgeSelection(
                    badgeModel = badge,
                    selected = selectedBadges.contains(badge.name),
                )
                {
                    if (selectedBadges.contains(badge.name))
                        selectedBadges.remove(badge.name)
                    else
                        selectedBadges.add(badge.name)
                }
            }

        }
    }

    val dpi = deletingProfileIcon

    if (dpi != null && !dpi.third)
    {
        AlertDialog(
            onDismissRequest = {
                deletingProfileIcon = null
           },
            title = {
                Text("Delete this file?")
            },
            text = {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Image(
                        bitmap = dpi.second,
                        contentDescription = "Image to delete",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(Modifier.height(4.dp))

                    Text("This action cannot be undone!")
                }
            },
            confirmButton = {
                TextButton (onClick = {
                    deletingProfileIcon = Triple(
                        dpi.first,
                        dpi.second,
                        true
                    )
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    deletingProfileIcon = null
                }) {
                    Text("Cancel")
                }
            }
        )

    }

    if (dpi != null && dpi.third)
    {
        LaunchedEffect(4) {
            viewModel.selfViewModel.deleteProfileIconResource.execute {
                viewModel.noobleApi.resources.delete(dpi.first.id)
                viewModel.selfViewModel.retrieveSentProfileIcons.forget()
                deletingProfileIcon = null
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSelection(
    text: String,
    selected: Boolean,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    imageProvider: @Composable ColumnScope.(modifier: Modifier) -> Unit,
)
{
    Box (
        modifier = modifier
            .width(110.dp)
            .height(150.dp)
            .padding(5.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(true, onClick = onClick, onLongClick = onLongClick),
        contentAlignment = Alignment.TopEnd
    )
    {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                imageProvider(Modifier.size(50.dp))

                Spacer(Modifier.weight(1f))

                Text(
                    text,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    style = TextStyle(lineHeight = 13.sp)
                )

                Spacer(Modifier.weight(1f))

            }
        }

        AnimatedVisibility(
            active || selected,
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        )
        {
            Image(
                painter = painterResource(if (active) R.drawable.checked_green else R.drawable.checked),
                contentDescription = "This profile image is applied",
                modifier = Modifier.size(30.dp),
            )
        }
    }
}

@Composable
fun DecorationSelection(
    decorationModel: NoobleApiDecorationModel?,
    mainViewModel: MainViewModel,
    selected: Boolean,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
)
{
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    )
    {
        AnimatedVisibility(
            selected || active,
            enter = slideInHorizontally(initialOffsetX = {
                -it * 2
            }) + expandHorizontally(),
            exit = slideOutHorizontally(targetOffsetX = {
                -it * 2
            }) + shrinkHorizontally()
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            )
            {
                Image(
                    painter = painterResource(if (active) R.drawable.checked_green else R.drawable.checked),
                    contentDescription = "This decoration is active"
                )
            }
        }

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.clickable(true, onClick = onClick)
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
                        modifier = Modifier.clip(RoundedCornerShape(4.dp))
                    )
                }

            }

            Row (
                verticalAlignment = Alignment.CenterVertically
            )
            {
                val account = (mainViewModel.selfViewModel.selfState.value as SelfUiState.Connected).account
                val profile = account.profile
                val profileImage = profile.loadedProfileImage

                if (profileImage == null)
                {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "User profile image",
                        modifier = modifier.clip(RoundedCornerShape(4.dp))
                    )
                } else {
                    Image(
                        bitmap = profileImage,
                        contentDescription = "User profile image",
                        modifier = modifier.clip(RoundedCornerShape(4.dp))
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
                        color = if (decorationModel == null) MaterialTheme.colorScheme.onBackground else Color.White
                    )
                    Text(
                        account.role.description,
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic,
                        style = TextStyle(lineHeight = 10.sp),
                        color = if (decorationModel == null) MaterialTheme.colorScheme.onBackground else Color.White
                    )

                    ProfileBadgesListView(listOf()) {}

                    Text(
                        profile.description,
                        fontSize = 15.sp,
                        color = if (decorationModel == null) MaterialTheme.colorScheme.onBackground else Color.White
                    )
                }

            }

        }
    }
}


@Composable
fun BadgeSelection(
    badgeModel: NoobleApiBadgeModel,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit
) {
    Box(
        modifier = Modifier.width(110.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
                .clickable(
                    true,
                    onClick = onItemClicked
                )
                .padding(14.dp)
                .height(140.dp)
        ) {
            val badgeThumbnail = badgeModel.loadedThumbnail

            if (badgeThumbnail == null)
                Image(
                    painter = painterResource(R.drawable.nooblard),
                    contentDescription = "badge icon",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            else
                Image(
                    bitmap = badgeThumbnail,
                    contentDescription = "badge icon",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                badgeModel.title,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    lineHeight = 15.sp
                ),
                color = MaterialTheme.colorScheme.onSurface

            )

            Text(
                "${badgeModel.level} of ${badgeModel.maxLevel ?: "..."}",
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurface

            )
        }

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        )
        {
            AnimatedVisibility(
                selected,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Image(
                    painter = painterResource(R.drawable.checked_green),
                    contentDescription = "This badge is selected",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}



