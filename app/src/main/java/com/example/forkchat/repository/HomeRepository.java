package com.example.forkchat.repository;

import com.example.forkchat.api.ApiService;
import com.example.forkchat.model.ApiResponse;
import com.example.forkchat.model.Topic;
import com.example.forkchat.network.RetrofitClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeRepository {

    private ApiService apiService;

    public HomeRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public List<Topic> getTopics(String sort, String tag, int page) {
        try {
            Call<ApiResponse<List<Topic>>> call = apiService.getTopics(sort, tag, page);
            Response<ApiResponse<List<Topic>>> response = call.execute();

            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                return response.body().getData();
            } else {

                return getMockTopics();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return getMockTopics();
        }
    }

    public List<String> getAllTags() {
        try {
            Call<ApiResponse<List<String>>> call = apiService.getAllTags();
            Response<ApiResponse<List<String>>> response = call.execute();

            if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                return response.body().getData();
            } else {
                return Arrays.asList("科技", "生活", "学习", "娱乐", "社交");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Arrays.asList("科技", "生活", "学习", "娱乐", "社交");
        }
    }

    private List<Topic> getMockTopics() {
        return Arrays.asList(
                new Topic(
                        "1",
                        "分叉式讨论的优势分析",
                        "分叉式讨论结构能够有效分离补充和质疑观点...",
                        "1",
                        "用户A",
                        Arrays.asList("科技", "讨论"),
                        "2024-03-20",
                        25,
                        12,
                        null
                ),
                new Topic(
                        "2",
                        "移动社交应用的发展趋势",
                        "当前移动社交应用在讨论体验上存在诸多痛点...",
                        "2",
                        "用户B",
                        Arrays.asList("社交", "科技"),
                        "2024-03-19",
                        18,
                        8,
                        null
                )
        );
    }
}