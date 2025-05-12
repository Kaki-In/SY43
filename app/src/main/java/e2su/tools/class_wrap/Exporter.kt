package e2su.tools.class_wrap;

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public abstract class Exporter<T> (name: String) {

    val name = name

    @Composable
    abstract fun createView(data: T, map: ExportersMap, modifier: Modifier, activity: Activity)

}
