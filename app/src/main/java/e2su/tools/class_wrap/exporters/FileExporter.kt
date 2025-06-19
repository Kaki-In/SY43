package e2su.tools.class_wrap.exporters

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import e2su.tools.class_wrap.Exporter
import e2su.tools.class_wrap.ExportersMap
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.local.DownloadedFileEntity

import kotlinx.coroutines.launch
import e2su.utbm.sy43project.NoobleApp
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers

class FileExporter : Exporter<JsonObject>("file") {

    @Composable
    override fun createView(data: JsonObject, map: ExportersMap, modifier: Modifier) {
        val src = data["src"]?.jsonPrimitive?.content!!
        val filename: String = data["filename"]?.jsonPrimitive?.content!!
        val description: String = data["description"]?.jsonPrimitive?.content!!

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val downloadedFileDao = (context.applicationContext as? NoobleApp)?.database?.downloadedFileDao()

        Button(onClick = {
            val downloadId = downloadFile(context, src, filename)
            val destinationPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}/$filename"
            coroutineScope.launch(Dispatchers.IO) {
                downloadedFileDao?.let { dao: e2su.utbm.sy43project.local.DownloadedFileEntityDAO ->
                    val downloadedFile = DownloadedFileEntity(
                        id = downloadId,
                        fileName = filename,
                        filePath = destinationPath,
                        fileSize = 0L
                    )
                    dao.insert(downloadedFile)
                }
            }
        },  modifier = modifier.height(80.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(
                    description,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(65.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.file_download),
                        contentDescription = "download"
                    )
                    Text(
                        filename,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }

    private fun downloadFile(context: Context, url: String, filename: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setTitle(filename)
            .setDescription("Téléchargement en cours...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(request)
    }
}