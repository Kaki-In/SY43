package e2su.tools.class_wrap.exporters

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.google.accompanist.web.rememberWebViewStateWithHTMLData
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import org.json.JSONObject

class CustomChromeClient(val activity: Activity): AccompanistWebChromeClient() {

    var customView: View? = null

    override fun onHideCustomView() {
        (activity.window.decorView as FrameLayout).removeView(this.customView)
        this.customView = null

    }

    override fun onShowCustomView(paramView: View, paramCustomViewCallback: CustomViewCallback) {
        if (this.customView != null) {
            onHideCustomView()
            return
        }
        this.customView = paramView
        (activity.window.decorView as FrameLayout).addView(this.customView, FrameLayout.LayoutParams(-1, -1))
    }
}

class IntegrationExporter: Exporter<JSONObject>("integration") {
    @Composable
    override fun createView(
        data: JSONObject,
        map: ExportersMap,
        modifier: Modifier,
        activity: Activity
    ) {
        val width = data.get("width")
        val height = data.get("height")
        val src = data.getString("src").replace("\"", "\\\"")
        val permissions = data.getJSONArray("permissions")

        var allowfullscreen = ""

        var content = "<iframe width=\"$width\" height=\"$height\" src=\"$src\" loading=\"lazy\" allow=\""

        var added = false;
        for (index in 0..(permissions.length()-1))
        {
            val permission = permissions.get(index)

            if (permission === "fullscreen")
            {
                allowfullscreen = " allowfullscreen"
            } else {
                if (added)
                {
                    content += "; "
                }

                content += permission
                added = true
            }

        }

        Log.i("IntegrationExporter", "content: " + content+"\"$allowfullscreen>IFRAME ARE NOT WORKING ON YOUR MOBILE</iframe>")

        val webViewState = rememberWebViewStateWithHTMLData(data=content+"\">IFRAME ARE NOT WORKING ON YOUR MOBILE</iframe>")

        WebView(
            webViewState,
            chromeClient = CustomChromeClient(activity),
            onCreated = { obj ->
                obj.settings.javaScriptEnabled = true
            },
            modifier = modifier.fillMaxWidth()
        )
    }
}