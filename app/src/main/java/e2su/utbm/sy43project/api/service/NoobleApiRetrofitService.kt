package e2su.utbm.sy43project.api.service

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import e2su.tools.class_wrap.extensions.toImageBitmapDefinedInSY43Context
import e2su.utbm.sy43project.api.models.objects.*
import e2su.utbm.sy43project.api.models.requests.*
import e2su.utbm.sy43project.api.models.responses.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.java.net.cookiejar.JavaNetCookieJar
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.io.File
import java.io.InputStream
import java.net.CookieManager
import java.net.CookiePolicy


private fun getRetrofitService(ctx: Context, baseUrl: String): Retrofit
{
    val cookieHandler = CookieManager(
        PersistentCookieStore(ctx),
        CookiePolicy.ACCEPT_ALL
    )

    val retrofitService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(Json { ignoreUnknownKeys = true } .asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient().newBuilder().cookieJar(JavaNetCookieJar(cookieHandler)).build())
        .build()

    return retrofitService
}


interface NoobleApiRetrofitService {
    @POST("/accounts/add")
    suspend fun createAccount(@Body request: AddAccountRequestModel): AddAccountResponseModel

    @POST("/accounts/delete")
    suspend fun deleteAccount(@Body request: DeleteAccountRequestModel)

    @POST("/accounts/modify-mail")
    suspend fun modifyMailAddress(@Body request: ModifyUserMailRequestModel)

    @POST("/accounts/modify-role")
    suspend fun modifyUserRole(@Body request: ModifyUserRoleRequestModel)

    @GET("/accounts/search")
    suspend fun searchAccount(@Query("pattern") pattern:String, @Query("offset") offset: Int, @Query("count") count: Int): List<NoobleApiAccountModel>

    @GET("/accounts/get-info")
    suspend fun getAccountInformation(@Query("user_id") userId:String): NoobleApiAccountModel

    @POST("/accounts/update-password")
    suspend fun updateAccountPassword(request: UpdatePasswordRequestModel)

    @POST("/activities/init")
    suspend fun initActivity(@Body activityName: String): InitializeActivityResponseModel

    @GET("/activities/list")
    suspend fun listActivities(): List<String>

    @POST("/badges/buy")
    suspend fun buyBadge(@Body request: BuyBadgeRequestModel): BuyBadgeResponseModel

    @GET("/badges/get-info")
    suspend fun getBadgeInformation(@Query("name") badgeName: String, @Query("level") level: Int): GetBadgeInfoResponseModel

    @GET("/badges/get-thumbnail")
    suspend fun getBadgeThumbnail(@Query("name") badgeName: String, @Query("level") level: Int): ResponseBody

    @GET("/badges/list")
    suspend fun listBadges(): ListBadgesResponseModel

    @POST("/classes/add-account")
    suspend fun addAccountToClass(@Body request: AddAccountToClassRequestModel)

    @POST("/classes/create")
    suspend fun createClass(@Body request: CreateClassRequestModel): CreateClassResponseModel

    @GET("/classes/data")
    suspend fun getClassData(@Query("class_id") request: String): GetClassDataResponseModel

    @GET("/classes/get-content")
    suspend fun getClassContent(@Query("class_id") request: String): JsonObject

    @POST("/classes/delete")
    suspend fun deleteClass(@Body request: DeleteClassRequestModel)

    @POST("/classes/edit")
    suspend fun editClass(@Body request: EditClassRequestModel)

    @GET("/classes/get-accounts")
    suspend fun getClassAccounts(@Query("class_id") request: String): List<String>

    @GET("/classes/search")
    suspend fun searchClass(@Query("pattern") pattern:String, @Query("offset") offset: Int, @Query("count") count: Int): List<NoobleApiClassModel>

    @POST("/classes/remove-account")
    suspend fun removeAccountFromClass(@Body request: RemoveAccountFromClassRequestModel)

    @POST("/connection/forgot-password")
    suspend fun launchForgotPasswordProcess(@Body request: ForgotPasswordRequestModel): ForgotPasswordResponseModel

    @GET("/connection/log-info")
    suspend fun getConnectionInformation(): LogInfoResponseModel

    @POST("/connection/login")
    suspend fun logToAccount(@Body request: LoginRequestModel): LoginResponseModel

    @POST("/connection/logout")
    suspend fun logOutFromAccount()

    @GET("/decorations/get-info")
    suspend fun getDecorationInformation(@Query("decoration") decoration: String): GetDecorationInfoResponseModel

    @GET("/decorations/list")
    suspend fun listDecorations(): List<NoobleApiDecorationModel>

    @POST("/decorations/buy")
    suspend fun buyDecoration(@Body request: BuyDecorationRequestModel): BuyDecorationResponseModel

    @POST("/decorations/create")
    suspend fun createDecoration(@Body request: CreateDecorationRequestModel): CreateDecorationResponseModel

    @POST("/decorations/delete")
    suspend fun deleteDecoration(@Body request: DeleteDecorationRequestModel)

    @POST("/decorations/modify")
    suspend fun modifyDecoration(@Body request: ModifyDecorationRequestModel)

    @GET("/profile/get-info")
    suspend fun getProfileInformation(@Query("user_id") userId: String): NoobleApiAccountProfileModel

    @GET("/profile/get-info")
    suspend fun getProfileInformation(): NoobleApiAccountProfileModel

    @POST("/profile/modify")
    suspend fun modifyProfile(request: ModifyAccountProfileRequestModel)

    @POST("/profile/update")
    suspend fun updateProfile(@Body request: UpdateProfileRequestModel)

    @POST("/resources/delete")
    suspend fun deleteResource(@Body request: DeleteResourceRequestModel)

    @GET("/resources/download")
    suspend fun downloadFile(@Query("id") fileId: String, @Query("type") fileType: NoobleApiResourceType): ResponseBody

    @GET("/resources/get-self-files")
    suspend fun getSelfFiles(): List<NoobleApiResourceModel>

    @GET("/resources/get-self-files")
    suspend fun getSelfFiles(@Query("type") type: NoobleApiResourceType): List<NoobleApiResourceModel>

    @Multipart
    @POST("/resources/upload")
    suspend fun uploadFile(@Part("name") name: RequestBody, @Part("type") type: RequestBody, @Part file: MultipartBody.Part): UploadResourceResponseModel

    @GET("/safe")
    suspend fun getSafe(): NoobleApiSafeModel

    @GET("/safe/badges")
    suspend fun getSafeBadges(): List<NoobleApiBadgeModel>

    @GET("/safe/decorations")
    suspend fun getSafeDecorations(): List<String>

    @GET("/safe/quota")
    suspend fun getSafeQuota(): Int

    @GET("/thread/get")
    suspend fun getThread(@Query("notreadonly") notreadonly: Boolean, @Query("count") count: Int, @Query("offset") offset: Int): List<NoobleApiActivityModel>

    @POST("/thread/mark-as-read")
    suspend fun markThreadAsRead(@Body request: MarkThreadAsReadRequestModel)

}

class AccountsApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun create(mail: String, firstName: String, lastName: String): String
    {
        return _service.createAccount(
            AddAccountRequestModel(mail, firstName, lastName)
        ).newAccountId
    }

    suspend fun delete(userId: String)
    {
        return _service.deleteAccount(
            DeleteAccountRequestModel(userId)
        )
    }

    suspend fun modifyMail(userId: String, mail: String)
    {
        return _service.modifyMailAddress(
            ModifyUserMailRequestModel(userId, mail)
        )
    }

    suspend fun modifyRole(userId: String, newRole: NoobleApiRole)
    {
        return _service.modifyUserRole(
            ModifyUserRoleRequestModel(userId, newRole)
        )
    }

    suspend fun updatePassword(lastPassword: String, newPassword: String)
    {
        return _service.updateAccountPassword(
            UpdatePasswordRequestModel(lastPassword, newPassword)
        )
    }

    suspend fun searchAccount(pattern: String, count: Int, offset: Int): List<NoobleApiAccountModel>
    {
        return _service.searchAccount(
            pattern, offset, count
        )
    }

    suspend fun getAccountInformation(accountId: String): NoobleApiAccountModel
    {
        return _service.getAccountInformation(accountId)
    }
}

class ActivitiesApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun list() : List<String>
    {
        return _service.listActivities()
    }

    suspend fun initActivity(activityName: String): String
    {
        return _service.initActivity(activityName).newFileId
    }
}

class BadgesApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun buy(name: String) : BuyBadgeResponseModel
    {
        return _service.buyBadge(
            BuyBadgeRequestModel(name)
        )
    }

    suspend fun getInformation(name: String, level: Int): GetBadgeInfoResponseModel
    {
        return _service.getBadgeInformation(name, level)
    }

    suspend fun getThumbnail(name: String, level: Int): InputStream
    {
        return _service.getBadgeThumbnail(
            name, level
        ).byteStream()
    }

    suspend fun list(): ListBadgesResponseModel
    {
        return _service.listBadges()
    }
}

class ClassesApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun addAccount(userId: String, classId: String) {
        return _service.addAccountToClass(
            AddAccountToClassRequestModel(userId, classId)
        )
    }

    suspend fun create(name: String, description: String): String {
        return _service.createClass(
            CreateClassRequestModel(name, description)
        ).newClassId
    }

    suspend fun getData(classId: String): NoobleApiClassModel {
        val result = _service.getClassData(
            classId
        )
        return NoobleApiClassModel(
            id = classId,
            description = result.description,
            lastModification = result.lastModification,
            lastModifier = result.lastModifier,
            name = result.name
        )
    }

    suspend fun getContent(classId: String): JsonObject {
        return _service.getClassContent(
            classId
        ).jsonObject["content"]!!.jsonObject
    }

    suspend fun delete(classId: String) {
        return _service.deleteClass(
            DeleteClassRequestModel(classId)
        )
    }


    suspend fun edit(
        classId: String,
        title: String,
        description: String,
        content: JsonObject
    ) {
        return _service.editClass(
            EditClassRequestModel(classId, title, description, content)
        )
    }

    suspend fun getAccounts(classId: String): List<String> {
        return _service.getClassAccounts(
            classId
        )
    }

    suspend fun removeAccount(classId: String, userId: String) {
        return _service.removeAccountFromClass(
            RemoveAccountFromClassRequestModel(classId, userId)
        )
    }

    suspend fun searchClass(pattern: String, count: Int, offset: Int): List<NoobleApiClassModel>
    {
        return _service.searchClass(
            pattern, offset, count
        )
    }

}

class ConnectionApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun launchForgotPasswordProcess(username: String): ForgotPasswordResponseModel
    {
        return _service.launchForgotPasswordProcess(
            ForgotPasswordRequestModel(username)
        )
    }

    suspend fun getInformation(): NoobleApiAccountModel?
    {
        val result = _service.getConnectionInformation().account

        if (result != null && result.profile.profileImage != null)
        {
            result.profile.loadedProfileImage = _service.downloadFile(
                result.profile.profileImage,
                NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON
            ).toImageBitmapDefinedInSY43Context()
        }

        return  result
    }

    suspend fun login(username: String, password: String): LoginResponseModel
    {
        return _service.logToAccount(
            LoginRequestModel(username, password)
        )
    }

    suspend fun logout()
    {
        return _service.logOutFromAccount()
    }

}

class DecorationsApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun buy(decorationId: String) : Int
    {
        return _service.buyDecoration(
            BuyDecorationRequestModel(decorationId)
        ).newQuota
    }

    suspend fun create(name: String, price: Int, imageId: String): String
    {
        return _service.createDecoration(
            CreateDecorationRequestModel(name, price, imageId)
        ).newDecoration
    }

    suspend fun delete(decorationId: String)
    {
        return _service.deleteDecoration(
            DeleteDecorationRequestModel(decorationId)
        )
    }

    suspend fun getInformation(decorationId: String): GetDecorationInfoResponseModel
    {
        return _service.getDecorationInformation(
            decorationId
        )
    }

    suspend fun list(): List<NoobleApiDecorationModel>
    {
        return _service.listDecorations()
    }

    suspend fun modify(decorationId: String, name:String, price:Int, image:String)
    {
        return _service.modifyDecoration(
            ModifyDecorationRequestModel(decorationId, name, price, image)
        )
    }

}

class ProfilesApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun getInformation(accountId: String? = null, loadImage: Boolean = true): NoobleApiAccountProfileModel
    {
        val profileInformation = if (accountId == null) {
            _service.getProfileInformation()
        } else {
            _service.getProfileInformation(
                accountId
            )
        }

        if (profileInformation.profileImage != null && loadImage)
        {
            profileInformation.loadedProfileImage = _service.downloadFile(
                profileInformation.profileImage,
                NoobleApiResourceType.RESOURCE_TYPE_PROFILE_ICON
            ).toImageBitmapDefinedInSY43Context()
        }

        return profileInformation
    }

    suspend fun modify(accountId: String, firstName: String, lastName: String, profileImage: String, activeDecoration: String, activeBadges: List<String>, description: String)
    {
        return _service.modifyProfile(
            ModifyAccountProfileRequestModel(accountId, firstName, lastName, profileImage, activeDecoration, activeBadges, description)
        )
    }

    suspend fun update(firstName: String, lastName: String, profileImage: String?, activeDecoration: String?, activeBadges: List<String>, description: String)
    {
        return _service.updateProfile(
            UpdateProfileRequestModel(firstName, lastName, profileImage, activeDecoration, activeBadges, description)
        )
    }

}

class ResourcesApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun delete(resourceId: String)
    {
        return _service.deleteResource(
            DeleteResourceRequestModel(resourceId)
        )
    }

    suspend fun download(resourceId: String, resourceType: NoobleApiResourceType): InputStream
    {
        return _service.downloadFile(resourceId, resourceType).byteStream()
    }

    suspend fun getSelfFiles(type: NoobleApiResourceType? = null): List<NoobleApiResourceModel>
    {
        return if (type == null)
            _service.getSelfFiles()
        else
            _service.getSelfFiles(type)
    }

    suspend fun upload(fileName: String, fileContentName: String, fileType: NoobleApiResourceType, file: File): UploadResourceResponseModel
    {
        val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull())

        val fileNameArgument = fileName.toRequestBody("text/plain".toMediaTypeOrNull())
        val fileTypeArgument = fileType.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        return _service.uploadFile(fileNameArgument, fileTypeArgument, MultipartBody.Part.createFormData(
            name = "file",
            filename = fileContentName,
            body = requestFile
        ))
    }

}

class SafeApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun getWholeSafe(): NoobleApiSafeModel
    {
        return _service.getSafe()
    }

    suspend fun getQuota(): Int
    {
        return _service.getSafeQuota()
    }

    suspend fun getBadges(): List<NoobleApiBadgeModel>
    {
        return _service.getSafeBadges()
    }

    suspend fun getDecorations(): List<String>
    {
        return _service.getSafeDecorations()
    }

}

class ThreadApi(service: NoobleApiRetrofitService)
{
    private val _service = service

    suspend fun getThread(count: Int, offset: Int, notReadOnly: Boolean): List<NoobleApiActivityModel>
    {
        return _service.getThread(
            notReadOnly, count, offset
        )
    }

    suspend fun markAsRead(activities: List<String>)
    {
        return _service.markThreadAsRead(
            MarkThreadAsReadRequestModel(activities)
        )
    }
}

class NoobleApi(ctx: Context, baseUrl: String) {
    private val _service : NoobleApiRetrofitService by lazy {
        getRetrofitService(ctx, baseUrl).create(NoobleApiRetrofitService::class.java)
    }

    val accounts = AccountsApi(_service)
    val activities = ActivitiesApi(_service)
    val badges = BadgesApi(_service)
    val classes = ClassesApi(_service)
    val connection = ConnectionApi(_service)
    val decorations = DecorationsApi(_service)
    val profiles = ProfilesApi(_service)
    val resources = ResourcesApi(_service)
    val safe = SafeApi(_service)
    val thread = ThreadApi(_service)

}

