package e2su.utbm.sy43project.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun NoobleIntegrated(content: @Composable (ColumnScope.() -> Unit), modifier: Modifier = Modifier)
{
    Scaffold (modifier = modifier) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)) {
            NoobleHeader()
            content()
            NoobleFooter()
        }
    }
}


