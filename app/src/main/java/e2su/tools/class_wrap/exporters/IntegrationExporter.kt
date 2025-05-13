package e2su.tools.class_wrap.exporters

import android.R.attr.visibility
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.view.ViewGroup
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
import org.json.JSONObject
import androidx.core.graphics.createBitmap

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

        val url_is_primary = url == base_url || request.isRedirect || url.startsWith("data:text/HTML")

        if (request.isForMainFrame && !url_is_primary)
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

        var allowfullscreen = ""

        var content = "<iframe frameborder=\"0\" width=\"$width\" height=\"$height\" src=\"$src\" loading=\"lazy\" allow=\""

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

        AndroidView(
            factory = { context ->
                WebView(
                    context,
                ).apply {
                    settings.javaScriptEnabled = true
                    loadData(content+"\"$allowfullscreen>IFRAME ARE NOT WORKING ON YOUR MOBILE</iframe>", "text/HTML", "UTF-8")

                    webViewClient = CustomWebViewClient(src, context)
                    webChromeClient = CustomWebChromeClient(context as Activity, this)
                }
            }
        )
    }
}