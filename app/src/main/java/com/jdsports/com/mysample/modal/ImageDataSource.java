package com.jdsports.com.mysample.modal;


import com.jdsports.com.mysample.Util.NetworkStatus;

public interface ImageDataSource {

    void getImages(NetworkStatus networkStats, LoadCallBackListener callBackListener);

    void getImageSearch(NetworkStatus networkStats, String query, LoadCallBackListener callBackListener);

    interface LoadCallBackListener {
        void onLoaded(Object response);

        void onError(Object error);
    }
}
