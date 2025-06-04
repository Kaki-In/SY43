package e2su.utbm.sy43project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import e2su.nooble.models.ProfileModel
import e2su.utbm.sy43project.ui.components.ProfileButton

@Composable
fun ProfileEditScreen(
    profile: ProfileModel,
    modifier: Modifier = Modifier,
    onSaveClick: (ProfileModel) -> Unit,
    onNavigateToProfile: () -> Unit,
    onChangePhoto: () -> Unit,
    onChangeContour: () -> Unit
) {
    var nameEditing by remember { mutableStateOf(false) }
    var mailEditing by remember { mutableStateOf(false) }
    var surnameEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(profile.name) }
    var mail by remember { mutableStateOf(profile.mail) }
    var surname by remember { mutableStateOf(profile.surname) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Column {
                ProfileButton(
                    profile = profile,
                    size = 120,
                )
                Row {
                    IconButton(onClick = onChangePhoto) {
                        Icon(Icons.Default.Edit, contentDescription = "Changer la photo")
                    }
                    IconButton(onClick = onChangeContour) {
                        Icon(Icons.Default.Edit, contentDescription = "Changer le contour")
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { if (nameEditing) name = it },
                label = { Text("Nom") },
                readOnly = !nameEditing,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                nameEditing = !nameEditing
                if (!nameEditing) onSaveClick(profile.copy(name = name))
            }) {
                Icon(
                    imageVector = if (nameEditing) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (nameEditing) "Valider le nom" else "Éditer le nom"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = mail,
                onValueChange = { if (mailEditing) mail = it },
                label = { Text("Email") },
                readOnly = !mailEditing,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                mailEditing = !mailEditing
                if (!mailEditing) onSaveClick(profile.copy(mail = mail))
            }) {
                Icon(
                    imageVector = if (mailEditing) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (mailEditing) "Valider l'email" else "Éditer l'email"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = surname,
                onValueChange = { if (surnameEditing) surname = it },
                label = { Text("Téléphone") },
                readOnly = !surnameEditing,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                surnameEditing = !surnameEditing
                if (!surnameEditing) onSaveClick(profile.copy(surname = surname))
            }) {
                Icon(
                    imageVector = if (surnameEditing) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (surnameEditing) "Valider le téléphone" else "Éditer le téléphone"
                )
            }
        }
        Button(
            onClick = {
                val updatedProfile = profile.copy(
                    name = name,
                    mail = mail,
                    surname = surname
                )
                onSaveClick(updatedProfile)
                onNavigateToProfile()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Sauvegarder les modifications")
        }
    }
}