package e2su.utbm.sy43project.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import e2su.utbm.sy43project.api.models.objects.NoobleApiRole
import e2su.utbm.sy43project.ui.components.LoadingSpinner
import e2su.utbm.sy43project.viewmodels.CurrentActionUiState
import e2su.utbm.sy43project.viewmodels.CurrentDataRequestUiState
import e2su.utbm.sy43project.viewmodels.MainViewModel
import e2su.utbm.sy43project.viewmodels.SelfUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyUserAccountScreen(
    viewModel: MainViewModel,
    accountId: String,
    modifier: Modifier = Modifier
)
{
    if (viewModel.retrieveAccountRequest.requestState.value is CurrentDataRequestUiState.Idle)
    {
        LaunchedEffect(true) {
            viewModel.retrieveAccountRequest.retrieveData {
                viewModel.noobleApi.accounts.getAccountInformation(accountId)
            }
        }
    }

    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Modify user account",
            fontSize = 25.sp
        )

        if ( accountId == (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account.id)
        {
            Text("You cannot modify your own account settings. ", color = Color.Red)
        }

        if (viewModel.retrieveAccountRequest.requestState.value is CurrentDataRequestUiState.Loading)
        {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LoadingSpinner()
            }

            return
        }

        if (viewModel.retrieveAccountRequest.requestState.value is CurrentDataRequestUiState.Error)
        {
            viewModel.retrieveAccountRequest.forget()
        }

        if (viewModel.retrieveAccountRequest.requestState.value !is CurrentDataRequestUiState.Success)
        {
            return
        }

        val account = (viewModel.retrieveAccountRequest.requestState.value as CurrentDataRequestUiState.Success).responseData

        val roles = listOf(NoobleApiRole.ROLE_STUDENT, NoobleApiRole.ROLE_TEACHER, NoobleApiRole.ROLE_ADMIN, NoobleApiRole.ROLE_TEACHER_ADMIN)
        var selectedRole by remember { mutableStateOf(account.role) }

        var mailAddress by remember {
            mutableStateOf(account.mail)
        }

        val context = LocalContext.current

        var applyingRole by remember {
            mutableStateOf(false)
        }

        if (applyingRole)
        {
            LaunchedEffect(2) {
                viewModel.applyAccountRoleRequest.execute {
                    viewModel.noobleApi.accounts.modifyRole(account.id, selectedRole)

                    Toast
                        .makeText(context,
                            "The new role has been applied successfully",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }
        }

        var applyingMail by remember {
            mutableStateOf(false)
        }

        if (applyingMail)
        {
            applyingMail = false
            LaunchedEffect(2) {
                viewModel.applyAccountMailRequest.execute {
                    viewModel.noobleApi.accounts.modifyMail(account.id, mailAddress)

                    Toast
                        .makeText(context,
                            "The new mail has been applied successfully",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            }
        }

        Text(
            "${account.profile.firstName} ${account.profile.lastName}"
        )

        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.padding(10.dp).height(2.dp).fillMaxWidth().background(MaterialTheme.colorScheme.onBackground))
        Spacer(Modifier.height(10.dp))

        Text(
            "Account role"
        )

        Column (
            horizontalAlignment = Alignment.End
        )
        {
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedRole.description,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Choose an option") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.description) },
                            onClick = {
                                selectedRole = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                enabled = viewModel.applyAccountRoleRequest.requestState.value !is CurrentActionUiState.Loading || accountId == (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account.id,
                onClick = {
                    applyingRole = true
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Apply role")
            }
        }

        Spacer(Modifier.height(10.dp))
        Spacer(Modifier.padding(10.dp).height(2.dp).fillMaxWidth().background(MaterialTheme.colorScheme.onBackground))
        Spacer(Modifier.height(10.dp))

        Text(
            "Mail address"
        )

        Column(
            horizontalAlignment = Alignment.End
        )
        {
            TextField(
                mailAddress,
                onValueChange = {
                    mailAddress = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                enabled = viewModel.applyAccountMailRequest.requestState.value !is CurrentActionUiState.Loading || accountId == (viewModel.selfViewModel.selfState.value as SelfUiState.Connected).account.id,
                onClick = {
                    applyingMail = true
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Text("Apply mail address")
            }
        }

    }

}


