package e2su.utbm.sy43project.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel

@Composable
fun ClassPreview(
    model: NoobleApiClassModel,
    onClassClicked: () -> Unit,
    modifier: Modifier = Modifier)
{
    Box(modifier = modifier.clickable(true, onClick = onClassClicked))
    {
        Row (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(20.dp)
                .fillMaxSize()

        )
        {
            Column (
                modifier = Modifier.weight(1f)
            )
            {
                Text(
                    model.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    model.description,
                    fontSize = 14.sp
                )
            }
        }
    }

}