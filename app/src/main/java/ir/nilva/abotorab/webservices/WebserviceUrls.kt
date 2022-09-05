package ir.nilva.abotorab.webservices

import ir.nilva.abotorab.model.*
import retrofit2.Response
import retrofit2.http.*

interface WebserviceUrls {

    @FormUrlEncoded
    @POST("signin/")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("depository_code") depositoryCode: String
    ): Response<SigninResponse>

    @FormUrlEncoded
    @POST("reception/take/")
    suspend fun take(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("phone") phoneNumber: String,
        @Field("country") country: String,
        @Field("passport_id") passportId: String,
        @Field("bag_count") bagCount: Int,
        @Field("suitcase_count") suitcaseCount: Int,
        @Field("pram_count") pramCount: Int
    ): Response<TakeResponse>

    @FormUrlEncoded
    @POST("reception/give/")
    suspend fun give(
        @Field("hash_id") hashId: String
    ): Response<GiveResponse>

    @FormUrlEncoded
    @POST("reception/give_list/")
    suspend fun give(
        @Field("hash_ids") hashId: List<String>
    ): Response<List<GiveResponse>>

    @FormUrlEncoded
    @POST("cabinet/")
    suspend fun cabinet(
        @Field("code") code: String,
        @Field("num_of_rows") rows: Int,
        @Field("num_of_cols") columns: Int,
        @Field("size") size: Int,
        @Field("first_row_size") firstRowSize: Int
    ): Response<CabinetResponse>

    @FormUrlEncoded
    @POST("cell/change_status/")
    suspend fun changeStatus(
        @Field("code") code: String,
        @Field("is_healthy") isHealthy: Boolean
    ): Response<ChangeStatusResponse>

    @GET("report/")
    suspend fun report(): Response<ReportResponse>

    @GET("config/")
    suspend fun config(): Response<ConfigResponse>

    @GET("report/start/")
    suspend fun startReport(): Response<StartReportResponse>

    @GET("cabinet/")
    suspend fun cabinetList(): Response<List<CabinetResponse>>

    @POST("cabinet/{code}/print/")
    suspend fun printCabinet(@Path("code") code: String): Response<BaseResponse>


    @FormUrlEncoded
    @POST("cabinet/{code}/extend/")
    suspend fun extendCabinet(@Path("code") code: String,
                              @Field("num_of_cols") newColumnsCount: Int,
                              @Field("num_of_rows") newRowsCount: Int): Response<CabinetResponse>


    @DELETE("cabinet/{code}/")
    suspend fun deleteCabinet(@Path("code") code: String): Response<BaseResponse>

    @GET("delivery/")
    suspend fun deliveryList(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("country") country: String,
        @Query("phone") phone: String,
        @Query("passport_id") passportId: String,
        @Query("in_house") inHouse: Boolean
    ): Response<List<DeliveryResponse>>


    @GET("delivery/")
    suspend fun deliveryListWithLimit(
        @Query("first_name") firstName: String,
        @Query("last_name") lastName: String,
        @Query("country") country: String,
        @Query("phone") phone: String,
        @Query("passport_id") passportId: String,
        @Query("in_house") inHouse: Boolean,
        @Query("limit") limit: Int
    ): Response<List<DeliveryResponse>>

    @POST("delivery/{hash_id}/revert_exit/")
    suspend fun undoDelivery(@Path("hash_id") hashId: String): Response<BaseResponse>

    @POST("delivery/{hash_id}/reprint/")
    suspend fun reprint(@Path("hash_id") hashId: String): Response<BaseResponse>

    @POST("cell/{id}/deliver_to_store/")
    suspend fun deliverToStore(@Path("id") id: Int): Response<BaseResponse>

    @POST("cell/{id}/favorite/")
    suspend fun favorite(@Path("id") id: Int): Response<BaseResponse>

    @POST("cell/{id}/print/")
    suspend fun print(@Path("id") id: Int): Response<BaseResponse>

    @POST("cell/{id}/free/")
    suspend fun free(@Path("id") id: Int): Response<BaseResponse>

    @GET("admin/")
    suspend fun test(): Response<Void>

    @GET("administration/export/")
    suspend fun export(): Response<ExportResponse>

    @GET("administration/export_store/")
    suspend fun exportStore(): Response<ExportResponse>
}
