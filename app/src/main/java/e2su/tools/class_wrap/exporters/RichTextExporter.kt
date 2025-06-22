package e2su.tools.class_wrap.exporters

import android.app.Activity
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.serialization.json.JsonPrimitive

class RichTextExporter: Exporter<JsonPrimitive>("rich-text") {
    @Composable
    override fun createView(
        data: JsonPrimitive,
        map: ExportersMap,
        mainViewModel: MainViewModel,
        modifier: Modifier
    ) {

        // We need to create an old xml text for that, since the html parser returns an
        // android.text.Spanned
        AndroidView(factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(data.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }, modifier = modifier.background(Color.White, shape = RoundedCornerShape(4.dp)).padding(2.dp))
    }
}