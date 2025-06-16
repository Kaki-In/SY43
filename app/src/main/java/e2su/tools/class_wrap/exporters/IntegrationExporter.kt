package e2su.tools.class_wrap.exporters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlin.math.log

/*

    The CustomWebViewClient allows to open links in another browser rather than directly in the
    iframe of the app.

 */
private class CustomWebViewClient(val base_url: String, val context: Context): WebViewClient()
{
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request === null)
        {
            return false
        }

        val url = request.url.toString()

        val urlIsPrimary = url == base_url || request.isRedirect || url.startsWith("data:text/HTML")

        if (request.isForMainFrame && !urlIsPrimary)
        {
            Log.i("CustomWebViewClient", "shouldOverrideUrlLoading: " + url)

            val intent = Intent(Intent.ACTION_VIEW);
            intent.setData(url.toUri());

            try {
                intent.resolveActivity(context.packageManager)
                context.startActivity(intent)
            } catch (e: Exception) {
                Log.e("CustomWebViewClient", "shouldOverrideUrlLoading: ", e)
            }

            return true
        }

        return false
    }

}

/*

    The CustomWebChromeClient allows to open fullscreen pages (such as youtube videos)

*/
private class CustomWebChromeClient(val activity: Activity, val webView: WebView): WebChromeClient()
{
    var fullscreen: View? = null

    override fun onHideCustomView()
    {
        fullscreen?.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }
    @Override
    override fun onShowCustomView(view: View, callback: CustomViewCallback)
    {
        webView.visibility = View.GONE

        if(fullscreen != null)
        {
            (activity.window.decorView as FrameLayout).removeView(fullscreen)
        }

        view.visibility = View.VISIBLE
        fullscreen = view
        (activity.window.decorView as FrameLayout).addView(fullscreen, FrameLayout.LayoutParams(-1, -1))
    }
}

class IntegrationExporter: Exporter<JsonObject>("integration") {
    @Composable
    override fun createView(
        data: JsonObject,
        map: ExportersMap,
        modifier: Modifier
    ) {
        Log.i("TAG", "createView: " + data.toString())

        val width = try {data["width"]?.jsonPrimitive?.int!!} catch (exc: Exception) {data["width"]?.jsonPrimitive?.content!!}
        val height = try {data["height"]?.jsonPrimitive?.int!!} catch (exc: Exception) {data["height"]?.jsonPrimitive?.content!!}
        val src = data["src"]?.jsonPrimitive?.content!!.replace("\"", "\\\"")
        val permissions = data["permissions"]?.jsonArray!!

        var allowFullScreen = ""

        var content = "<iframe frameborder=\"0\" width=\"$width\" height=\"$height\" src=\"$src\" loading=\"lazy\" allow=\""

        var added = false;
        for (index in 0..(permissions.size-1))
        {
            val permission = permissions[index].jsonPrimitive.content

            if (permission === "fullscreen")
            {
                allowFullScreen = " allowfullscreen"
            } else {
                if (added)
                {
                    content += "; "
                }

                content += permission
                added = true
            }

        }

        AndroidView(
            factory = { context ->
                WebView(
                    context,
                ).apply {
                    settings.javaScriptEnabled = true
                    loadData("$content\"$allowFullScreen>IFRAME ARE NOT WORKING ON YOUR MOBILE</iframe>", "text/HTML", "UTF-8")

                    webViewClient = CustomWebViewClient(src, context)
                    webChromeClient = CustomWebChromeClient(context as Activity, this)
                }
            }
        )
    }
}