package e2su.tools.class_wrap.exporters

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import org.json.JSONObject

class ActivityExporter: Exporter<JSONObject>("activity") {
    @Composable
    override fun createView(
        data: JSONObject,
        map: ExportersMap,
        modifier: Modifier
    ) {
        val html = data.getString("html")

        val css = data.getString("css")
        val javascript = data.getString("javascript")

        val id = data.getInt("id")
        val arguments = data.getJSONObject("arguments").toString()

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