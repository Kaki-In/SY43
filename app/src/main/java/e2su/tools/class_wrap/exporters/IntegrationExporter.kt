package e2su.tools.class_wrap.exporters

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import org.json.JSONObject

class IntegrationExporter: Exporter<JSONObject>("integration") {
    @Composable
    override fun createView(
        data: JSONObject,
        map: ExportersMap,
        modifier: Modifier
    ) {
        val width = data.get("width")
        val height = data.get("height")
        val src = data.getString("src").replace("\"", "\\\"")
        val permissions = data.getJSONArray("permissions")

        var content = "<iframe width=\"{$width}\" height=\"{$height}\" src=\"{$src}\" loading=\"lazy\" permissions=\""

        for (index in 0..(permissions.length()-1))
        {
            val permission = permissions.get(index)

            if (index > 0)
            {
                content += "; "
            }

            content += permission
        }

        val webViewState = rememberWebViewStateWithHTMLData(data=content+"\">IFRAME ARE NOT WORKING ON YOUR MOBILE</iframe>")

        WebView(webViewState, modifier = modifier)
    }
}