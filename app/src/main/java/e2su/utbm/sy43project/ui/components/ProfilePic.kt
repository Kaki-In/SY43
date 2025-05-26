package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.navigation.NavigationManager

@Composable
fun ProfilePic(
    profile: ProfileModel,
    size: Int,
    modifier: Modifier = Modifier
) {
    CircularImage(
        imageRes = profil.image,
        size = size,
        modifier = modifier.clickable {
            NavigationManager.navigateToProfile()
        }
    )
}