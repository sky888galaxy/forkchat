package com.example.forkchat.api;

import com.example.forkchat.model.ApiResponse;
import com.example.forkchat.model.CreateTopicRequest;
import com.example.forkchat.model.LoginRequest;
import com.example.forkchat.model.RegisterRequest;
import com.example.forkchat.model.Topic;
import com.example.forkchat.model.User;
import com.example.forkchat.model.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //
    @POST("auth/login")
    Call<ApiResponse<User>> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ApiResponse<User>> register(@Body RegisterRequest request);

    //
    @GET("user/{userId}")
    Call<ApiResponse<UserProfile>> getUserProfile(@Path("userId") String userId);

    @PUT("user/{userId}")
    Call<ApiResponse<User>> updateUserProfile(@Path("userId") String userId, @Body User user);

    //
    @GET("topics")
    Call<ApiResponse<List<Topic>>> getTopics(
            @Query("sort") String sort,    // "hot" 或 "new"
            @Query("tag") String tag,      // 标签筛选，如 "科技"
            @Query("page") int page        // 分页，从1开始
    );

    @GET("tags")
    Call<ApiResponse<List<String>>> getAllTags();

    //
    @POST("topics")
    Call<ApiResponse<Topic>> createTopic(@Body CreateTopicRequest request);

    //
    @GET("topics/{topicId}")
    Call<ApiResponse<Topic>> getTopicDetail(@Path("topicId") String topicId);
}