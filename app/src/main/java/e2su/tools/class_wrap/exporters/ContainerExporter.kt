package e2su.tools.class_wrap.exporters

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.tools.class_wrap.exceptions.InvalidArgumentException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ContainerExporter: Exporter<JSONObject>("container") {

    @Composable
    override fun createView(data: JSONObject, map: ExportersMap) {
        val children: JSONArray

        try {
            children = data.getJSONArray("children")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("Could not find children for the container. Please go check in the white van. ")
        }

        Column {
            for (i in 0..(children.length() - 1))
            {
                var child_data = children.getJSONObject(i)

                map.createView(child_data)
            }
        }

    }

}