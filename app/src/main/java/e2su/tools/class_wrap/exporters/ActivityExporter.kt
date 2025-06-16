package e2su.tools.class_wrap.exporters

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ActivityExporter: Exporter<JsonObject>("activity") {
    @Composable
    override fun createView(
        data: JsonObject,
        map: ExportersMap,
        modifier: Modifier
    ) {
        val html = data["html"]?.jsonPrimitive?.content!!

        val css = data["css"]?.jsonPrimitive?.content!!
        val javascript = data["javascript"]?.jsonPrimitive?.content!!

        val id = data["id"]?.jsonPrimitive?.int!!
        val arguments = data["arguments"]?.jsonObject!!

        val content = """
        <!DOCTYPE html>
        <html>
            <head>
                <meta charset='utf-8'/>
                <style>
                
        $css
        
                </style>
            </head>
            <body>
                <div id="MAIN_DIV">
        $html
                </div>
                <text id="alert">Bonjour</text>
                <script>
         
        $javascript
            
        function alert(message)
        {
            document.getElementById("alert").innerText = message;
        }
        
        window.addEventListener("load", () => {
            
            alert("bonjour");
            
            var activity = new Activity($id, $arguments);
            var div = document.getElementById("MAIN_DIV");
            activity.onRender(div);

        })
        
                </script>
            </body>
        </html>
        """.trimMargin()

        val webViewState = rememberWebViewStateWithHTMLData(data = content, baseUrl = "https://nooble.flopcreation.fr/")

        WebView(
            state = webViewState,
            onCreated = { obj ->
                obj.settings.javaScriptEnabled = true
            },
            modifier = modifier
        )
    }
}