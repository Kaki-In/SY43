package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import e2su.utbm.sy43project.dao.DownloadedFileEntity

@Composable
fun DownloadScreen(
    downloadedFiles: List<DownloadedFileEntity>,
    modifier: Modifier = Modifier,
    onFileClick: (DownloadedFileEntity) -> Unit = {}
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Documents téléchargés", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        LazyColumn {
            items(downloadedFiles) { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onFileClick(file) }
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(file.name)
                    Text("${file.size / 1024} Ko", style = MaterialTheme.typography.bodySmall)
                }
                HorizontalDivider()
            }
        }
    }
}