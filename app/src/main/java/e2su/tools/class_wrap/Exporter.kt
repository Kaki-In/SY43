package e2su.tools.class_wrap;

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.serialization.json.JsonElement

public abstract class Exporter<T: JsonElement> (val name: String) {
    @Composable
    abstract fun createView(data: T, map: ExportersMap, mainViewModel: MainViewModel, modifier: Modifier)

}
