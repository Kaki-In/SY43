package e2su.tools.class_wrap.exporters

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
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class ContainerExporter: Exporter<JsonObject>("container") {

    @Composable
    override fun createView(data: JsonObject, map: ExportersMap, modifier: Modifier) {
        val children = data["children"]?.jsonArray!!

        Column (modifier = modifier.padding(0.dp)) {
            Spacer(
                modifier = Modifier
                    .background(Color(0xffdddddd))
                    .fillMaxWidth()
                    .height(1.dp)
            )

            for (i in 0..(children.size - 1))
            {
                var childData = children[i].jsonObject

                map.createView(childData, modifier = Modifier.padding(5.dp))
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