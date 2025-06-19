package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel

@Composable
fun BadgePreview(
    reachable: Boolean,
    badgeModel: NoobleApiBadgeModel,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit,
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
            .width(80.dp)
            .height(140.dp)
    ) {
        if (reachable)
        {
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
        } else {
            val colorMatrix = ColorMatrix()
            colorMatrix.setToSaturation(0f)

            val badgeThumbnail = badgeModel.loadedThumbnail

            if (badgeThumbnail == null)
                Image(
                    painter = painterResource(R.drawable.nooblard),
                    contentDescription = "badge icon",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    colorFilter = ColorFilter.colorMatrix(colorMatrix)
                )
            else
                Image(
                    bitmap = badgeThumbnail,
                    contentDescription = "badge icon",
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    colorFilter = ColorFilter.colorMatrix(colorMatrix)
                )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Text(
            badgeModel.title,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                lineHeight = 15.sp
            )

        )

        Text(
            "${badgeModel.level} of ${badgeModel.maxLevel?:"..."}",
            fontSize = 10.sp,
            fontStyle = FontStyle.Italic
        )
    }
}
