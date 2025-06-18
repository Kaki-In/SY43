package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.nooble.models.ShopItemModel
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.ui.views.BadgePreview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShopScreen(
    userCoins: Int,
    shopItems: List<ShopItemModel>,
    onBuyItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Affichage du porte-monnaie
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Shop",
                fontSize = 20.sp,
                modifier = modifier.padding(4.dp)
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "$userCoins",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDE8900)
            )

            Spacer(Modifier.width(3.dp))

            Image(
                painter = painterResource(R.drawable.nooblard),
                contentDescription = "Nooblard Piece Icon",
                modifier = Modifier.size(18.dp)
            )
        }

        FlowRow(
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