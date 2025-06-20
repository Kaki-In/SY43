package e2su.tools.class_wrap.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

fun uriToCroppedImageFile(context: Context, uri: Uri): Pair<ImageBitmap, File>? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        if (originalBitmap == null) return null

        val size = min(originalBitmap.width, originalBitmap.height)
        val xOffset = (originalBitmap.width - size) / 2
        val yOffset = (originalBitmap.height - size) / 2
        val squareBitmap = Bitmap.createBitmap(originalBitmap, xOffset, yOffset, size, size)

        val file = File(context.cacheDir, "uploadingImage.jpg")
        val outputStream = FileOutputStream(file)
        squareBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return Pair(squareBitmap.asImageBitmap(), file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

