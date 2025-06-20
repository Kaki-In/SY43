package e2su.tools.class_wrap.extensions

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import okhttp3.ResponseBody
import java.io.InputStream

fun InputStream.toImageBitmapDefinedInSY43Context(): ImageBitmap =
    this.use { BitmapFactory.decodeStream(it).asImageBitmap() }

fun ResponseBody.toImageBitmapDefinedInSY43Context(): ImageBitmap =
    this.use { it.byteStream().use { it.toImageBitmapDefinedInSY43Context() } }

