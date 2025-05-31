package e2su.utbm.sy43project.ui.components.border


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import e2su.nooble.models.ProfileBorderData
import e2su.nooble.models.ProfileModel
import androidx.compose.foundation.clickable

@Composable
fun ProfileWithBorder(
    profileModel: ProfileModel,
    borderData: ProfileBorderData?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if (borderData != null) {
        AnimatedProfileBorder(
            borderData = borderData,
            content = {
                Image(
                    painter = painterResource(id = profileModel.image),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .clickable(onClick = onClick)
                )
            },
            modifier = modifier.aspectRatio(1f)
        )
    } else {
        // Profil sans bordure animée
        Image(
            painter = painterResource(id = profileModel.image),
            contentDescription = "Profile Picture",
            modifier = modifier
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickable(onClick = onClick)
        )
    }
}