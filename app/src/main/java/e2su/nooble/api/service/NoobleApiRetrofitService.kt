package e2su.nooble.api.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import e2su.nooble.api.models.objects.*
import e2su.nooble.api.models.requests.*
import e2su.nooble.api.models.responses.*
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofitService = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl("https://api.nooble-angular.flopcreation.fr")
    .build()

interface NoobleApiRetrofitService {
    @POST("/accounts/add")
    suspend fun createAccount(request: AddAccountRequestModel): AddAccountResponseModel

    @POST("/accounts/delete")
    suspend fun deleteAccount(request: DeleteAccountRequestModel)

    @POST("/accounts/modify-mail")
    suspend fun modifyMailAddress(request: ModifyUserMailRequestModel)

    @POST("/accounts/modify-role")
    suspend fun modifyMailRole(request: ModifyUserRoleRequestModel)

    @GET("/activities/list")
    suspend fun listActivities(): List<String>

    @POST("/badges/buy")
    suspend fun buyBadge(request: BuyBadgeRequestModel): BuyBadgeResponseModel

    @GET("/badges/get-infos")
    suspend fun getBadgeInformation(request: GetBadgeInfoRequestModel): GetBadgeInfosResponseModel

    @GET("/badges/list")
    suspend fun listBadges(): List<NoobleApiBadgeModel>

    @POST("/classes/add-account")
    suspend fun addAccountToClass(request: AddAccountRequestModel): AddAccountResponseModel

    @POST("/classes/create")
    suspend fun createClass(request: CreateClassRequestModel): CreateClassResponseModel

    @GET("/classes/data")
    suspend fun getClassData(request: GetClassDataRequestModel): GetClassDataResponseModel

    @POST("/classes/delete")
    suspend fun deleteClass(request: DeleteClassRequestModel)

    @POST("/classes/edit")
    suspend fun editClass(request: EditClassRequestModel)

    @GET("/classes/get-accounts")
    suspend fun getClassAccounts(request: GetClassAccountsRequestModel): List<String>

    @POST("/classes/remove-account")
    suspend fun removeAccountFromClass(request: RemoveAccountFromClassRequestModel)

    @POST("/connection/forgot-password")
    suspend fun launchForgotPasswordProcess(request: ForgotPasswordRequestModel): ForgotPasswordResponseModel

    @GET("/connection/log-info")
    suspend fun getConnectionInformation(): LogInfoResponseModel

    @POST("/connection/login")
    suspend fun logToAccount(request: LoginRequestModel): LoginResponseModel

    @POST("/connection/logout")
    suspend fun logOutFromAccount()

    @GET("/decorations/get-info")
    suspend fun getDecorationInformation(request: GetDecorationInfoRequestModel): GetDecorationInfoResponseModel

    @GET("/decorations/list")
    suspend fun listDecorations(): List<NoobleApiDecorationModel>

    @POST("/decorations/buy")
    suspend fun buyDecoration(request: BuyDecorationRequestModel): BuyDecorationResponseModel

    @POST("/decorations/create")
    suspend fun createDecoration(request: CreateDecorationRequestModel): CreateDecorationResponseModel

    @POST("/decorations/delete")
    suspend fun deleteDecoration(request: DeleteDecorationRequestModel)

    @POST("/decorations/modify")
    suspend fun modifyDecoration(request: ModifyDecorationRequestModel)

    @GET("/profile/get-info")
    suspend fun getProfileInformation(request: GetAccountProfileRequestModel): NoobleApiAccountProfileModel

    @POST("/profile/modify")
    suspend fun modifyProfile(request: ModifyAccountProfileRequestModel)

    @POST("/profile/update")
    suspend fun updateProfile(request: UpdateProfileRequestModel)

    @POST("/resources/delete")
    suspend fun deleteResource(request: DeleteResourceRequestModel)

    @GET("/resources/get-self-files")
    suspend fun getSelfFiles()

    @GET("/resources/get-self-files")
    suspend fun getSelfFiles(request: GetSelfFilesWithTypeRequestModel)

    @GET("/safe")
    suspend fun getSafe(): NoobleApiSafeModel

    @GET("/safe/badges")
    suspend fun getSafeBadges(): List<NoobleApiBadgeModel>

    @GET("/safe/decorations")
    suspend fun getSafeDecorations(): List<String>

    @GET("/safe/quota")
    suspend fun getSafeQuota(): Int

    @GET("/thread/get")
    suspend fun getThread(request: GetThreadRequestModel): List<NoobleApiActivityModel>

    @POST("/thread/mark-as-read")
    suspend fun markThreadAsRead(request: MarkThreadAsReadRequestModel)

}

object NoobleApi {
    val service : NoobleApiRetrofitService by lazy {
        retrofitService.create(NoobleApiRetrofitService::class.java)
    }
}

