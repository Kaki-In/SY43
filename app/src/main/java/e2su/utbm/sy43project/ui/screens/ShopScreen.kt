package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import e2su.utbm.sy43project.ui.components.ShopItemPreviewDialog
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items

@Composable
fun ShopScreen(
    userCoins: Int,
    shopItems: List<ShopItemModel>,
    onBuyItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf<ShopItemModel?>(null) }

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
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "$userCoins pièces",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
        }

        // Grille des items
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(shopItems) { item: ShopItemModel ->
                ShopItem(
                    item = item,
                    onBuyClick = { onBuyItem(item.id) },
                    onItemClick = { selectedItem = item },
                    modifier = Modifier
                        .aspectRatio(0.75f)  // Rapport hauteur/largeur fixe
                        .fillMaxWidth()
                )
            }
        }
    }

    // Dialog de prévisualisation
    selectedItem?.let { item ->
        ShopItemPreviewDialog(
            item = item,
            onBuyClick = { onBuyItem(item.id) },
            onDismiss = { selectedItem = null }
        )
    }
}