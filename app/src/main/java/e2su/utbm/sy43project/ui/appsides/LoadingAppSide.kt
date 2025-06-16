package e2su.utbm.sy43project.ui.appsides

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.components.LoadingSpinner

@Composable
fun LoadingAppSide (modifier: Modifier = Modifier) {
    Scaffold (modifier = modifier) { innerPadding ->
        Box(
            Modifier.padding(innerPadding)
        )
        {

            Image(
                painter = painterResource(R.mipmap.ic_launcher_foreground),
                contentDescription = "Main Icon",
                modifier = Modifier.align(Alignment.Center).fillMaxSize().wrapContentSize(unbounded = true).padding(20.dp),
            )

            LoadingSpinner(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom=20.dp)
            )

        }
    }
}
