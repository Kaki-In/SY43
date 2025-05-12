package e2su.tools.class_wrap.exporters

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap

class AudioExporter: Exporter<String>("audio") {
    @Composable
    override fun createView(
        data: String,
        map: ExportersMap,
        modifier: Modifier
    ) {
        TODO("download the audio")
    }

}