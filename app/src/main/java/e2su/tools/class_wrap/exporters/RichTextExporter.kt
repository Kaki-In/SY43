package e2su.tools.class_wrap.exporters

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap

class RichTextExporter: Exporter<String>("rich-text") {
    @Composable
    override fun createView(
        data: String,
        map: ExportersMap,
        modifier: Modifier
    ) {

        // We need to create an old xml text for that, since the html parser returns an
        // android.text.Spanned
        AndroidView(factory = { context ->
            TextView(context).apply {
                setText(HtmlCompat.fromHtml(data, HtmlCompat.FROM_HTML_MODE_LEGACY))
            }
        }, modifier = modifier)
    }
}