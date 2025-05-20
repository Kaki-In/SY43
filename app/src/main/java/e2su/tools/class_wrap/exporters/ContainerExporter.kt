package e2su.tools.class_wrap.exporters

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.tools.class_wrap.exceptions.InvalidArgumentException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ContainerExporter: Exporter<JSONObject>("container") {

    @Composable
    override fun createView(data: JSONObject, map: ExportersMap, modifier: Modifier) {
        val children: JSONArray

        try {
            children = data.getJSONArray("children")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("Could not find children for the container. Please go check in the white van. ")
        }

        Column (modifier = modifier.padding(0.dp)) {
            Spacer(
                modifier = Modifier
                    .background(Color(0xffdddddd))
                    .fillMaxWidth()
                    .height(1.dp)
            )

            for (i in 0..(children.length() - 1))
            {
                var child_data = children.getJSONObject(i)

                map.createView(child_data, modifier = Modifier.padding(5.dp))
            }

            Spacer(
                modifier = Modifier
                    .background(Color(0xffdddddd))
                    .fillMaxWidth()
                    .height(1.dp)
            )

        }

    }

}