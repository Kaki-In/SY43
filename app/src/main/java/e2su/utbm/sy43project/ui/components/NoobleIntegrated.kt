package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
public fun NoobleIntegrated(
    navHostController: NavHostController,
    content: @Composable (modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier)
{
    Scaffold (modifier = modifier) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            NoobleHeader(
                modifier = Modifier.height(IntrinsicSize.Min)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                content(Modifier.fillMaxSize())
            }

            NoobleFooter(
                navController = navHostController,
                modifier = Modifier.height(IntrinsicSize.Min)
            )
        }
    }
}


