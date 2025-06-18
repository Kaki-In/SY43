package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.ui.views.BadgePreview
import e2su.utbm.sy43project.viewmodels.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShopBadgesListScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize().padding(20.dp)
    )
    {
        FlowRow (
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        )
        {
            val context = LocalContext.current

            fun loadBitmap(resource: Int): ImageBitmap
            {
                val resource = context.getDrawable(resource)!!

                val bitmap = ImageBitmap(resource.intrinsicWidth, resource.intrinsicHeight)
                val canvas = android.graphics.Canvas(bitmap.asAndroidBitmap())

                resource.setBounds(0, 0, canvas.width, canvas.height)

                resource.draw(canvas)

                return bitmap
            }

            BadgePreview(
                true,
                NoobleApiBadgeModel(
                    "coucou",
                    10,
                    30,
                    "Bonjour",
                    "salut"
                ),
                20,
                loadBitmap(R.drawable.nooblard),
            )

            BadgePreview(
                false,
                NoobleApiBadgeModel(
                    "Chien",
                    10,
                    30,
                    "Un gentil poti chien",
                    "salut"
                ),
                20,
                loadBitmap(R.drawable.nooblard),
            )
        }
    }
}