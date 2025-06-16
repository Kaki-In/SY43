package e2su.utbm.sy43project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.ui.navigation.admin.AdminNavigationManager
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileButton(
    profile: ProfileModel,
    size: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { AdminNavigationManager.profilePageAction.navigate() },
        contentPadding = PaddingValues(0.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.size(size.dp)
    ) {
        CircularImage(
            imageRes = profile.image,
            size = size,
            modifier = Modifier.fillMaxSize()
        )
    }
}