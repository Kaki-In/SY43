package e2su.utbm.sy43project.ui.screens.common

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.tools.class_wrap.DEFAULT_EXPORTERS_MAPS
import e2su.utbm.sy43project.api.models.objects.NoobleApiClassModel
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.RetrieveDataViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

@Composable
fun OverviewScreen(
    classId: String,
    requestViewModel: RetrieveDataViewModel<NoobleApiClassModel>,
    modifier: Modifier = Modifier
) {
    val requestState = requestViewModel.requestState

    if (requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            requestViewModel.retrieveData {
                requestViewModel.getNoobleApi().classes.getData(classId)
            }
        }
    }

    Column(modifier = modifier) {
        Log.i("TAG", "OverviewScreen: " + requestState.value + ", " + requestViewModel)

        when (requestState.value)
        {
            is CurrentDataRequestUiState.Idle, is CurrentDataRequestUiState.Loading ->
            {
                Text("Loading ...")
            }

            is CurrentDataRequestUiState.Success ->
            {
                val classData = (requestState.value as CurrentDataRequestUiState.Success).responseData

                Text(
                    text = classData.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = classData.description,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))


                Column (modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
                ) {

                    DEFAULT_EXPORTERS_MAPS.createView(classData.content)

                }
            }

            is CurrentDataRequestUiState.Error ->
            {
                Text("An error occurred...")
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityViewPreview() {
    ActivityScreen()
}