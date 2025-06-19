package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel

@Composable
fun ProfileBadgesListView(
    badges: List<NoobleApiBadgeModel>,
    modifier: Modifier = Modifier,
    onSelectBadge: (NoobleApiBadgeModel) -> Unit
) {
    Row (
        modifier = modifier.height(30.dp).padding(4.dp)
    ) {
        for (badge in badges)
        {
            val badgeThumbnail = badge.loadedThumbnail

            if (badgeThumbnail != null)
            {
                Image(
                    modifier = Modifier.clickable(true, onClick = {
                        onSelectBadge(badge)
                    }),
                    bitmap = badgeThumbnail,
                    contentDescription = "cannot load image"
                )

                Spacer(Modifier.width(10.dp))

            }
        }
    }
}