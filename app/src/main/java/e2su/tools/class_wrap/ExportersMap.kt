package e2su.tools.class_wrap;

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.exceptions.NoSuchExporterException
import e2su.tools.class_wrap.exporters.ActivityExporter
import e2su.tools.class_wrap.exporters.AudioExporter
import e2su.tools.class_wrap.exporters.ContainerExporter
import e2su.tools.class_wrap.exporters.FileExporter
import e2su.tools.class_wrap.exporters.ImageExporter
import e2su.tools.class_wrap.exporters.IntegrationExporter
import e2su.tools.class_wrap.exporters.RawTextExporter
import e2su.tools.class_wrap.exporters.RichTextExporter
import e2su.tools.class_wrap.exporters.VideoExporter
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

public class ExportersMap {
    val exporters: MutableMap<String, Exporter<*>> = mutableMapOf<String, Exporter<*>>()

    fun getExporter(name: String): Exporter<*>
    {
        var exporter = exporters[name]

        if (exporter === null)
        {
            throw NoSuchExporterException(name)
        }

        return exporter
    }

    fun addExporter(exporter: Exporter<*>): ExportersMap
    {
        this.exporters[ exporter.name ] = exporter
        return this
    }

    fun getExporterTypes(): Set<String>
    {
        return this.exporters.keys
    }

    @Composable
    fun createView(jsonData: JsonObject, modifier: Modifier = Modifier)
    {
        Log.i("TAG", "createView: " + jsonData)

        val type = jsonData["type"]?.jsonPrimitive?.content!!
        val data = jsonData["data"]!!

        (this.getExporter(type) as Exporter<JsonElement>).createView(data, this, modifier = modifier)

    }

}

public val DEFAULT_EXPORTERS_MAPS = ExportersMap()
    .addExporter(ContainerExporter())
    .addExporter(FileExporter())
    .addExporter(RawTextExporter())
    .addExporter(RichTextExporter())
    .addExporter(ImageExporter())
    .addExporter(ActivityExporter())
    .addExporter(IntegrationExporter())
    .addExporter(VideoExporter())
    .addExporter(AudioExporter())

