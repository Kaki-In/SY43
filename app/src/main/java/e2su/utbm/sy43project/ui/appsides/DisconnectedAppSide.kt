package e2su.utbm.sy43project.ui.appsides

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.ui.drawers.DisconnectedDrawer
import e2su.utbm.sy43project.ui.navgraphs.DisconnectedNavGraph
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.ui.theme.NoobleGreen


@Composable
fun DisconnectedAppSide(mainModel: MainViewModel, modifier: Modifier = Modifier)
{
    Scaffold { innerPadding ->

        val drawerState = rememberDrawerState(DrawerValue.Closed)

        DisconnectedDrawer(
            drawerState = drawerState
        ) {
            Column(modifier
                .verticalScroll(rememberScrollState())) {

                Spacer(modifier = Modifier
                    .height(innerPadding.calculateTopPadding())
                    .background(NoobleGreen)
                    .fillMaxWidth())

                DisconnectedHeader()

                Spacer(modifier = Modifier.size(30.dp))

                DisconnectedNavGraph(
                    viewModel = mainModel
                )

                Spacer(modifier = Modifier.size(70.dp))
            }
        }

    }

}

@Composable
fun DisconnectedHeader(modifier: Modifier = Modifier) {
    Box (modifier = modifier
        .background(NoobleGreen)
        .fillMaxWidth()) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null
        )

        Text (
            "Nooble",
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFFFFFFFF),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

