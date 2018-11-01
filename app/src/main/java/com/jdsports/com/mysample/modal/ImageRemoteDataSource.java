package com.jdsports.com.mysample.modal;

import com.jdsports.com.mysample.Util.ErrorCode;
import com.jdsports.com.mysample.Util.NetworkStatus;
import com.jdsports.com.mysample.modal.pojo.IMResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRemoteDataSource implements ImageDataSource {

    private static ImageRemoteDataSource ourInstance;
    private final String API_KEY = "8439361-5e1e53a0e1b58baa26ab398f7";
    private ImageServices apiService;

    private ImageRemoteDataSource() {
    }

    public static ImageRemoteDataSource getInstance() {
        if (ourInstance == null) {
            ourInstance = new ImageRemoteDataSource();
        }
        return ourInstance;
    }

    @Override
    public void getImages(NetworkStatus networkStats, final LoadCallBackListener callBackListener) {
        apiService = RetrofitClient.getClient().create(ImageServices.class);

        Call<IMResponse> call = apiService.getAllImages(API_KEY);
        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                if (response.isSuccessful()) {
                    callBackListener.onLoaded(response.body());
                } else {
                    callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);

                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {
                callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);
            }
        });
    }

    @Override
    public void getImageSearch(NetworkStatus networkStats, String query, final LoadCallBackListener callBackListener) {
        apiService = RetrofitClient.getClient().create(ImageServices.class);

        Call<IMResponse> call = apiService.searchImages(API_KEY, query);
        call.enqueue(new Callback<IMResponse>() {
            @Override
            public void onResponse(Call<IMResponse> call, Response<IMResponse> response) {
                if (response.isSuccessful()) {
                    callBackListener.onLoaded(response);
                } else {
                    callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);

                }
            }

            @Override
            public void onFailure(Call<IMResponse> call, Throwable t) {
                callBackListener.onError(ErrorCode.CONNECTION_PROBLEM);
            }
        });
    }
}
