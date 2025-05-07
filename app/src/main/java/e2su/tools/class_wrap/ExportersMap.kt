package e2su.tools.class_wrap;

import androidx.compose.runtime.Composable
import e2su.tools.class_wrap.exceptions.InvalidArgumentException
import e2su.tools.class_wrap.exceptions.NoSuchExporterException
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

public class ExportersMap {
    val exporters: MutableMap<String, Exporter<Any>> = mutableMapOf<String, Exporter<Any>>()

    fun getExporter(name: String): Exporter<Any>
    {
        var exporter = exporters[name]

        if (exporter === null)
        {
            throw NoSuchExporterException(name)
        }

        return exporter
    }

    fun addExporter(exporter: Exporter<Any>)
    {
        this.exporters[ exporter.name ] = exporter
    }

    fun getExporterTypes(): Set<String>
    {
        return this.exporters.keys
    }

    @Composable
    fun createView(json_data: JSONObject)
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

        this.getExporter(type).createView(data, this)

    }
}

