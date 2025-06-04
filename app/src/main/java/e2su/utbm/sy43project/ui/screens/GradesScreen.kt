package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class GradeDetail(
    val assignmentName: String,
    val coefficient: Float,
    val grade: Float
)

data class CourseGrades(
    val courseName: String,
    val average: Float,
    val details: List<GradeDetail>
)

@Composable
fun GradesScreen(
    courses: List<CourseGrades>,
    modifier: Modifier = Modifier
) {
    // Gère l’expansion des éléments
    var expandedCourse by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(courses) { course ->
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedCourse = if (expandedCourse == course.courseName) null else course.courseName
                        }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = course.courseName,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = String.format("%.2f", course.average),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                if (expandedCourse == course.courseName) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 8.dp)
                    ) {
                        course.details.forEach { detail ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = detail.assignmentName,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "Coef: ${detail.coefficient}",
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                                Text(
                                    text = String.format("%.2f", detail.grade)
                                )
                            }
                        }
                    }
                }
                HorizontalDivider()
            }
        }
    }
}