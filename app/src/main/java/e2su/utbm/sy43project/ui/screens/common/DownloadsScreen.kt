package e2su.utbm.sy43project.ui.screens.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import e2su.utbm.sy43project.local.DownloadedFileEntity
import androidx.compose.ui.unit.dp

@Composable
fun DownloadsScreen(
    downloadedFiles: List<DownloadedFileEntity>,
    modifier: Modifier = Modifier,
    onFileClick: (DownloadedFileEntity) -> Unit = {}
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Documents téléchargés", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Column {
            for (file in downloadedFiles) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFileClick(file) }
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(file.fileName)
                    Text("${file.fileSize / 1024} Ko", style = MaterialTheme.typography.bodySmall)
                }
                HorizontalDivider()
            }
        }
    }
}

