package e2su.tools.class_wrap.exporters

import android.app.AlertDialog
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.*
import android.widget.EditText
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ActivityExporter : Exporter<JsonObject>("activity") {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun createView(
        data: JsonObject,
        map: ExportersMap,
        mainViewModel: MainViewModel,
        modifier: Modifier
    ) {
        val context = LocalContext.current

        val css = data["css"]!!.jsonPrimitive.content
        val javascript = data["javascript"]!!.jsonPrimitive.content
        val id = data["id"]!!.jsonPrimitive.content
        val arguments = data["arguments"]!!.jsonObject

        val htmlContent = """
            <!DOCTYPE html><html><head><meta charset="utf-8">
            <style>$css</style></head>
            <body><div id="MAIN_DIV"></div>
            <script>$javascript
              window.addEventListener("load", () => {
                  const activity = new Activity("$id", $arguments);
                  activity.onRender(document.getElementById("MAIN_DIV"));
              });
            </script></body></html>
        """.trimIndent()

        var filePathCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
        val filePickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenMultipleDocuments()
        ) { uris ->
            filePathCallback?.onReceiveValue(uris.toTypedArray())
            filePathCallback = null
        }

        /* 4. WebView natif avec AndroidView */
        AndroidView(
            factory = {
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.allowFileAccess = true
                    settings.allowContentAccess = true

                    val javaCookieManager = mainViewModel.noobleApi.cookiesHandler
                    val cookies = javaCookieManager.cookieStore.cookies

                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)

                    for (cookie in cookies) {
                        val cookieString = buildString {
                            append("${cookie.name}=${cookie.value}")
                            append("; Path=${cookie.path ?: "/"}")
                            if (cookie.secure) append("; Secure")
                            if (cookie.isHttpOnly) append("; HttpOnly")
                            cookie.maxAge.takeIf { it > 0 }?.let {
                                val expireDate = java.time.Instant.now().plusSeconds(it)
                                val formatter = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
                                    .withZone(java.time.ZoneId.of("GMT"))
                                append("; Expires=${formatter.format(expireDate)}")
                            }
                        }
                        val cookieUrl = "https://${cookie.domain.trimStart('.')}"

                        Log.i("TAG", "createView: " + cookieUrl + "  --><-- " + cookieString)

                        cookieManager.setCookie(cookieUrl, cookieString)
                    }

                    cookieManager.setAcceptThirdPartyCookies(this, true)
                    cookieManager.flush()

                    webChromeClient = object : WebChromeClient() {
                        override fun onJsAlert(
                            view: WebView?,
                            url: String?,
                            message: String?,
                            result: JsResult?
                        ): Boolean {
                            AlertDialog.Builder(context)
                                .setTitle("Alerte JavaScript")
                                .setMessage(message)
                                .setPositiveButton("OK") { _, _ -> result?.confirm() }
                                .setCancelable(false)
                                .create()
                                .show()
                            return true
                        }

                        override fun onJsConfirm(
                            view: WebView?,
                            url: String?,
                            message: String?,
                            result: JsResult?
                        ): Boolean {
                            AlertDialog.Builder(context)
                                .setTitle("Confirmation")
                                .setMessage(message)
                                .setPositiveButton("Oui") { _, _ -> result?.confirm() }
                                .setNegativeButton("Non") { _, _ -> result?.cancel() }
                                .create()
                                .show()
                            return true
                        }

                        override fun onJsPrompt(
                            view: WebView?,
                            url: String?,
                            message: String?,
                            defaultValue: String?,
                            result: JsPromptResult?
                        ): Boolean {
                            val input = EditText(context)
                            input.setText(defaultValue)

                            AlertDialog.Builder(context)
                                .setTitle(message)
                                .setView(input)
                                .setPositiveButton("OK") { _, _ -> result?.confirm(input.text.toString()) }
                                .setNegativeButton("Annuler") { _, _ -> result?.cancel() }
                                .create()
                                .show()
                            return true
                        }

                        override fun onShowFileChooser(
                            webView: WebView?,
                            filePathCallback_: ValueCallback<Array<Uri>>?,
                            fileChooserParams: FileChooserParams?
                        ): Boolean {
                            filePathCallback = filePathCallback_

                            val mimeTypes = fileChooserParams?.acceptTypes
                                ?.filter { it.isNotBlank() }
                                ?.toTypedArray()
                                ?.takeIf { it.isNotEmpty() } ?: arrayOf("*/*")

                            filePickerLauncher.launch(mimeTypes)

                            return true
                        }
                    }

                    loadDataWithBaseURL(
                        "https://api.nooble-angular.flopcreation.fr",
                        htmlContent,
                        "text/html",
                        "utf-8",
                        null
                    )
                }
            },
            modifier = modifier
        )
    }
}
