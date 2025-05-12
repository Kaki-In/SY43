package e2su.tools.class_wrap;

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.exceptions.InvalidArgumentException
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
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

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
    fun createView(json_data: JSONObject, modifier: Modifier = Modifier, activity: Activity)
    {
        val type: String
        var data: Any

        try {
            type = json_data.getString("type")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("could not find type in json object")
        }

        try {
            data = json_data.get("data")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("could not find data in json object")
        }

        (this.getExporter(type) as Exporter<Any>).createView(data, this, modifier = modifier, activity = activity)

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

