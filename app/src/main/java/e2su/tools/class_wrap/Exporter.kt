package e2su.tools.class_wrap;

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

public abstract class Exporter<T> (name: String) {

    val name = name

    @Composable
    abstract fun createView(data: T, map: ExportersMap, modifier: Modifier)

}
