package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.navigation.NavigationManager
import android.util.Log

@Composable
fun ProfilePic(
    profile: ProfileModel,
    size: Int,
    modifier: Modifier = Modifier
) {
    CircularImage(
        imageRes = profile.image,
        size = size,
        modifier = Modifier.clickable {
            Log.e("ProfilePic", "Clicked on profile picture of ${profile.name} ${profile.surname}")
            NavigationManager.navigateToProfile()
        }
            .then(modifier)
    )
}