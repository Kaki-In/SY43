package e2su.utbm.sy43project.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.R
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import java.lang.Double


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddUserToClassScreen(
    mainViewModel: MainViewModel,
    classId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    var searchText by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<NoobleApiAccountModel>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var searchError by remember { mutableStateOf<String?>(null) }
    var addingUserId by remember { mutableStateOf<String?>(null) }
    var addSuccess by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val performSearch: () -> Unit = {
        if (searchText.length >= 3) {
            isSearching = true
            searchError = null
            addSuccess = null
            coroutineScope.launch {
                try {
                    val results = mainViewModel.noobleApi.accounts.searchAccount(searchText, count = 5, offset = 0)
                    searchResults = results
                } catch (e: Exception) {
                    searchError = "Erreur lors de la recherche: ${e.message}"
                    searchResults = emptyList()
                } finally {
                    isSearching = false
                }
            }
        } else {
            searchError = "Entrez au moins 3 caractères pour rechercher"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ajouter des utilisateurs à la classe",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Rechercher un utilisateur") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Rechercher")
                },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = performSearch,
                enabled = !isSearching
            ) {
                Text("Rechercher")
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                isSearching -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                searchError != null -> {
                    Text(
                        text = searchError!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                searchResults.isEmpty() -> {
                    Text(
                        text = "Aucun résultat ou recherche non effectuée",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(searchResults) { user ->
                            UserItem(
                                user = user,
                                isAdding = addingUserId == user.id,
                                onAddClick = {
                                    addingUserId = user.id
                                    coroutineScope.launch {
                                        try {
                                            mainViewModel.noobleApi.classes.addAccount(user.id, classId)
                                            addSuccess = "${user.profile.firstName} ${user.profile.lastName} a été ajouté à la classe avec succès"
                                        } catch (e: Exception) {
                                            searchError = "Erreur lors de l'ajout: ${e.message}"
                                        } finally {
                                            addingUserId = null
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        addSuccess?.let {
            Alert(message = it, onDismiss = { addSuccess = null })
        }

        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Text("Retour")
        }
    }
}

@Composable
fun UserItem(
    user: NoobleApiAccountModel,
    isAdding: Boolean,
    onAddClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .clickable { onAddClick() }
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        val profileImage = user.profile.loadedProfileImage
        if (profileImage == null) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Account image",
                modifier = Modifier.size(60.dp).padding(10.dp)
            )
        } else {
            Image(
                bitmap = profileImage,
                contentDescription = "Account image",
                modifier = Modifier.size(60.dp).padding(10.dp)
            )
        }
        Text(
            text = "${user.profile.firstName} ${user.profile.lastName}",
            fontStyle = FontStyle.Italic,
            fontSize = 17.sp,
            modifier = Modifier.weight(1f)
        )
        if (isAdding) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
        } else {
            Icon(
                Icons.Default.Add,
                contentDescription = "Ajouter",
                modifier = Modifier.clickable { onAddClick() }
            )
        }
    }
}

@Composable
fun Alert(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = { Text("Succès") },
        text = { Text(message) }
    )
}