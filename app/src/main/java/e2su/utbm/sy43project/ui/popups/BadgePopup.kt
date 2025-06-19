package e2su.utbm.sy43project.ui.popups

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.ui.views.BadgePreview
import e2su.utbm.sy43project.viewmodels.MainViewModel

@Composable
fun BadgePopup(
    mainViewModel: MainViewModel,
    displayedBadge: NoobleApiBadgeModel?,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val badge = displayedBadge

    Column (
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background.copy(alpha=0.8f)).clickable(true, onClick = onBackClicked),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (badge == null)
            return

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            val badgeBitmap = badge.loadedThumbnail


            Text(
                badge.title,
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Light
            )

            Spacer(Modifier.height(20.dp))

            if (badgeBitmap != null)
            {
                Image(
                    bitmap = badgeBitmap,
                    contentDescription = "Badge icon"
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                badge.description,
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
