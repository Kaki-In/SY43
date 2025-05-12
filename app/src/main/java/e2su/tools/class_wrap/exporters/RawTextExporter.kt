package e2su.tools.class_wrap.exporters;

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap

public class RawTextExporter: Exporter<String>("raw-text") {

    @Composable
    override fun createView(data: String, map: ExportersMap, modifier: Modifier, activity: Activity) {
        Text(
            data,
            modifier = modifier
        )
    }

}
