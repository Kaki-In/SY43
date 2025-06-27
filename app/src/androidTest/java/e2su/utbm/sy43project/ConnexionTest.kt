package e2su.utbm.sy43project

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiAccountProfileModel
import e2su.utbm.sy43project.api.models.objects.NoobleApiRole
import e2su.utbm.sy43project.api.models.responses.LoginResponseModel
import e2su.utbm.sy43project.api.service.NoobleApi
import e2su.utbm.sy43project.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import e2su.utbm.sy43project.ui.theme.SY43ProjectTheme
import e2su.utbm.sy43project.ui.appsides.DisconnectedAppSide
import kotlinx.coroutines.runBlocking
import io.mockk.mockk

@OptIn(ExperimentalCoroutinesApi::class)
class AdminUserCreationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var noobleApi: NoobleApi
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() = runTest {
        Dispatchers.setMain(testDispatcher)

        noobleApi = mockk(relaxed = true)

        coEvery { noobleApi.connection.login("eden@mifamofi.net", "RsL46T1N") } returns LoginResponseModel("success", "success")

        val profile = NoobleApiAccountProfileModel(
            firstName = "Admin",
            lastName = "User",
            description = "Administrator profile",
            activeDecoration = null,
            activeBadges = emptyList(),
            profileImage = null
        )
        val accountModel = NoobleApiAccountModel(
            id = "admin123",
            mail = "eden@mifamofi.net",
            profile = profile,
            role = NoobleApiRole.ROLE_ADMIN
        )
        coEvery { noobleApi.connection.getInformation() } returns accountModel

        coEvery { noobleApi.accounts.create(any(), any(), any()) } returns "new_user_123"

        mainViewModel = MainViewModel(noobleApi)

        runBlocking {
            mainViewModel.selfViewModel.login("eden@mifamofi.net", "RsL46T1N")
        }
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLoginAndCreateUser() = runTest {

        composeTestRule.setContent {
            SY43ProjectTheme {
                DisconnectedAppSide(mainViewModel)
            }
        }

        composeTestRule.onNodeWithText("Email").performTextInput("eden@mifamofi.net")
        composeTestRule.onNodeWithText("Mot de passe").performTextInput("RsL46T1N")
        composeTestRule.onNodeWithText("Se connecter").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Ouvrir menu").performClick()
        composeTestRule.onNodeWithText("Manage users").performClick()

        composeTestRule.onNodeWithText("Créer un utilisateur").performClick()

        composeTestRule.onNodeWithText("Prénom").performTextInput("Jean")
        composeTestRule.onNodeWithText("Nom").performTextInput("Dupont")
        composeTestRule.onNodeWithText("Email").performTextInput("jean.dupont@example.com")
        composeTestRule.onNodeWithText("Créer").performClick()

        coVerify {
            noobleApi.accounts.create("jean.dupont@example.com", "Jean", "Dupont")
        }
    }
}