package e2su.tools.class_wrap.exporters

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.tools.class_wrap.exceptions.InvalidArgumentException
import org.json.JSONException
import org.json.JSONObject
import e2su.utbm.sy43project.R

class FileExporter: Exporter<JSONObject>("file") {

    @Composable
    override fun createView(data: JSONObject, map: ExportersMap) {
        val src: String
        val filename: String
        val description: String

        try {
            src = data.getString("src")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("could not find src for file")
        }

        try {
            filename = data.getString("filename")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("could not find name for file")
        }

        try {
            description = data.getString("description")
        } catch (exc: JSONException) {
            throw InvalidArgumentException("could not find name for file")
        }

        Button(onClick = {
            // TODO : download file
        }) {
            Row {
                Text(
                    description,
                    modifier = Modifier.fillMaxSize()
                )
                Column {
                    Image(
                        painter = painterResource(R.drawable.file_download),
                        contentDescription = "download"
                    )

                    Text(
                        filename
                    )
                }
            }
        }
    }

}