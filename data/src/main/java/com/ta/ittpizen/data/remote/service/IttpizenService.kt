package com.ta.ittpizen.data.remote.service

import com.haroldadmin.cnradapter.NetworkResponse
import com.ta.ittpizen.data.remote.request.auth.LoginRequest
import com.ta.ittpizen.data.remote.request.auth.RegisterRequest
import com.ta.ittpizen.data.remote.request.job.CreateJobRequest
import com.ta.ittpizen.data.remote.request.post.CreatePostCommentRequest
import com.ta.ittpizen.data.remote.response.CommonErrorResponse
import com.ta.ittpizen.data.remote.response.CommonResponse
import com.ta.ittpizen.data.remote.response.PagedCommonResponse
import com.ta.ittpizen.data.remote.response.auth.LoginResponse
import com.ta.ittpizen.data.remote.response.auth.RegisterResponse
import com.ta.ittpizen.data.remote.response.connection.ConnectionResponse
import com.ta.ittpizen.data.remote.response.connection.CreateConnectionResponse
import com.ta.ittpizen.data.remote.response.connection.DetailConnectionResponse
import com.ta.ittpizen.data.remote.response.job.CreateJobResponse
import com.ta.ittpizen.data.remote.response.job.DetailJobResponse
import com.ta.ittpizen.data.remote.response.job.JobResponse
import com.ta.ittpizen.data.remote.response.job.SaveJobResponse
import com.ta.ittpizen.data.remote.response.post.CreatePostCommentResponse
import com.ta.ittpizen.data.remote.response.post.CreatePostResponse
import com.ta.ittpizen.data.remote.response.post.PostCommentResponse
import com.ta.ittpizen.data.remote.response.post.PostResponse
import com.ta.ittpizen.data.remote.response.profile.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface IttpizenService {

    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): NetworkResponse<CommonResponse<LoginResponse>, CommonErrorResponse>

    @POST("user/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): NetworkResponse<CommonResponse<RegisterResponse>, CommonErrorResponse>

    @Multipart
    @POST("post")
    suspend fun createPost(
        @Header("Authorization")
        authorization: String,
        @Part("type")
        type: RequestBody,
        @Part("text")
        text: RequestBody,
        @Part
        media: MultipartBody.Part? = null
    ): NetworkResponse<CommonResponse<CreatePostResponse>, CommonErrorResponse>

    @GET("post")
    suspend fun getAllPost(
        @Header("Authorization")
        authorization: String,
        @Query("type")
        type: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<PostResponse>>, CommonErrorResponse>

    @GET("post/user/{userId}")
    suspend fun getPostByUser(
        @Header("Authorization")
        authorization: String,
        @Path("userId")
        userId: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<PostResponse>>, CommonErrorResponse>

    @GET("post/{postId}")
    suspend fun getPostById(
        @Header("Authorization")
        authorization: String,
        @Path("postId")
        postId: String
    ): NetworkResponse<CommonResponse<PostResponse>, CommonErrorResponse>

    @GET("post/comment/{postId}")
    suspend fun getPostComment(
        @Header("Authorization")
        authorization: String,
        @Path("postId")
        postId: String
    ): NetworkResponse<CommonResponse<List<PostCommentResponse>>, CommonErrorResponse>

    @POST("post/comment/{postId}")
    suspend fun createPostComment(
        @Header("Authorization")
        authorization: String,
        @Path("postId")
        postId: String,
        @Body
        request: CreatePostCommentRequest
    ): NetworkResponse<CommonResponse<CreatePostCommentResponse>, CommonErrorResponse>

    @POST("post/like/{postId}")
    suspend fun createPostLike(
        @Header("Authorization")
        authorization: String,
        @Path("postId")
        postId: String,
    ): NetworkResponse<CommonResponse<String>, CommonErrorResponse>

    @DELETE("post/like/{postId}")
    suspend fun deletePostLike(
        @Header("Authorization")
        authorization: String,
        @Path("postId")
        postId: String,
    ): NetworkResponse<CommonResponse<String>, CommonErrorResponse>

    @GET("connection")
    suspend fun getAllConnection(
        @Header("Authorization")
        authorization: String,
        @Query("type")
        type: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<ConnectionResponse>>, CommonErrorResponse>

    @GET("connection/search")
    suspend fun searchConnection(
        @Header("Authorization")
        authorization: String,
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<ConnectionResponse>>, CommonErrorResponse>


    @GET("connection/{userId}")
    suspend fun getConnectionById(
        @Header("Authorization")
        authorization: String,
        @Path("userId")
        userId: String
    ): NetworkResponse<CommonResponse<DetailConnectionResponse>, CommonErrorResponse>

    @POST("connection/connect/{userId}")
    suspend fun createConnection(
        @Header("Authorization")
        authorization: String,
        @Path("userId")
        userId: String
    ): NetworkResponse<CommonResponse<CreateConnectionResponse>, CommonErrorResponse>

    @DELETE("connection/connect/{userId}")
    suspend fun deleteConnection(
        @Header("Authorization")
        authorization: String,
        @Path("userId")
        userId: String
    ): NetworkResponse<CommonResponse<String?>, CommonErrorResponse>

    @GET("job")
    suspend fun getAllJob(
        @Header("Authorization")
        authorization: String,
        @Query("workplaceType")
        workplaceType: String,
        @Query("jobType")
        jobType: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<JobResponse>>, CommonErrorResponse>

    @GET("job/search")
    suspend fun searchJob(
        @Header("Authorization")
        authorization: String,
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<JobResponse>>, CommonErrorResponse>

    @GET("job/saved")
    suspend fun getSavedJob(
        @Header("Authorization")
        authorization: String,
        @Query("page")
        page: Int,
        @Query("size")
        size: Int
    ): NetworkResponse<PagedCommonResponse<List<JobResponse>>, CommonErrorResponse>

    @POST("job")
    suspend fun createJob(
        @Header("Authorization")
        authorization: String,
        @Body
        request: CreateJobRequest,
    ): NetworkResponse<CommonResponse<CreateJobResponse>, CommonErrorResponse>

    @POST("job/save/{jobId}")
    suspend fun saveJob(
        @Header("Authorization")
        authorization: String,
        @Path("jobId")
        jobId: String,
    ): NetworkResponse<CommonResponse<SaveJobResponse>, CommonErrorResponse>

    @DELETE("job/save/{jobId}")
    suspend fun unSaveJob(
        @Header("Authorization")
        authorization: String,
        @Path("jobId")
        jobId: String,
    ): NetworkResponse<CommonResponse<Unit>, CommonErrorResponse>

    @GET("job/{jobId}")
    suspend fun getJobById(
        @Header("Authorization")
        authorization: String,
        @Path("jobId")
        jobId: String
    ): NetworkResponse<CommonResponse<DetailJobResponse>, CommonErrorResponse>

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization")
        authorization: String,
    ): NetworkResponse<CommonResponse<ProfileResponse>, CommonErrorResponse>

    @Multipart
    @PATCH("profile")
    suspend fun updateProfile(
        @Header("Authorization")
        authorization: String,
        @Part("name")
        name: RequestBody,
        @Part("bio")
        bio: RequestBody,
        @Part
        photo: MultipartBody.Part? = null
    ): NetworkResponse<CommonResponse<ProfileResponse>, CommonErrorResponse>

}