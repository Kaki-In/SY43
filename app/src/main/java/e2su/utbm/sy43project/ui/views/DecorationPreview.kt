package e2su.utbm.sy43project.ui.views

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.squareup.wire.internal.decodePrimitive_float
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiBadgeModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiDecorationModel
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState
import e2su.utbm.sy43project.viewmodels.SelfViewModel
import kotlin.math.log

@Composable
fun DecorationPreview(
    mainViewModel: MainViewModel,
    decorationModel: NoobleApiDecorationModel,
    badges: List<NoobleApiBadgeModel>,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .clickable(true, onClick = onItemClicked)
            .padding(10.dp)
    ) {

        Box(
            contentAlignment = Alignment.CenterStart
        )
        {
            val bannerThumbnail = decorationModel.loadedThumbnail

            if (bannerThumbnail == null)
            {
                Text("Could not load thumbnail")
            } else {
                Image(
                    bitmap = bannerThumbnail,
                    contentDescription = decorationModel.name + " banner image",
                    modifier = modifier.clip(RoundedCornerShape(4.dp))
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                val account = (mainViewModel.selfViewModel.selfState.value as SelfUiState.Connected).account
                val profile = account.profile
                val profileImage = profile.loadedProfileImage

                if (profileImage == null)
                {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "User profile image",
                        modifier = modifier.clip(RoundedCornerShape(4.dp)).size(50.dp).padding(10.dp)
                    )
                } else {
                    Image(
                        bitmap = profileImage,
                        contentDescription = "User profile image",
                        modifier = modifier.clip(RoundedCornerShape(4.dp)).size(50.dp).padding(10.dp)
                    )
                }

                Column (
                    modifier = Modifier.weight(1f)
                )
                {
                    Text(
                        "${profile.firstName} ${profile.lastName}",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(lineHeight = 26.sp),
                        color = Color.White
                    )
                    Text(
                        account.role.description,
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Italic,
                        style = TextStyle(lineHeight = 10.sp),
                        color = Color.White
                    )

                    ProfileBadgesListView(
                        badges
                    ) {
                        mainViewModel.openBadge(it)
                    }

                    Text(
                        profile.description,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }

            }
        }

        Spacer(Modifier.height(20.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = decorationModel.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Thin
            )

            Spacer(
                Modifier.weight(1f)
            )

            Text(
                "${decorationModel.price}",
                color = if (mainViewModel.selfViewModel.retrieveSafeRequest.requestState.value !is CurrentDataRequestUiState.Success || (mainViewModel.selfViewModel.retrieveSafeRequest.requestState.value as CurrentDataRequestUiState.Success).responseData.quota >= decorationModel.price) Color(0xFFDE8900) else Color(0xFFFF0000)
            )

            Spacer(Modifier.width(3.dp))

            Image(
                painter = painterResource(R.drawable.nooblard),
                contentDescription = "Nooblard Piece Icon",
                modifier = Modifier.size(18.dp)
            )
        }
    }

}

