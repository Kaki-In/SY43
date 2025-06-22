package e2su.tools.class_wrap.exporters;

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.Exporter;
import e2su.tools.class_wrap.ExportersMap
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

public class ImageExporter: Exporter<JsonPrimitive>("image") {
    @Composable
    override fun createView(
        data: JsonPrimitive,
        map: ExportersMap,
        mainViewModel: MainViewModel,
        modifier: Modifier
    ) {
        // TODO("download the image")
    }
}
