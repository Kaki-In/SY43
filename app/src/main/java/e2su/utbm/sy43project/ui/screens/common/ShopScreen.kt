package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ShopItemModel
import e2su.utbm.sy43project.ui.components.ShopItem

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
            .padding(16.dp)
    ) {
        // Affichage du porte-monnaie
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "$userCoins pièces",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
        }

        // Liste des items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(shopItems) { item ->
                ShopItem(
                    item = item,
                    onBuyClick = { onBuyItem(item.id) }
                )
            }
        }
    }
}