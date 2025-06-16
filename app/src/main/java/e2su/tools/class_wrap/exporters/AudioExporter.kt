package e2su.tools.class_wrap.exporters

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import kotlinx.serialization.json.JsonPrimitive

class AudioExporter: Exporter<JsonPrimitive>("audio") {
    @Composable
    override fun createView(
        data: JsonPrimitive,
        map: ExportersMap,
        modifier: Modifier
    ) {
        // TODO("download the audio")
    }

}