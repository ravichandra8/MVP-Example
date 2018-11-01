package com.jdsports.com.mysample.modal;

import com.jdsports.com.mysample.Util.ErrorCode;
import com.jdsports.com.mysample.Util.NetworkStatus;
import com.jdsports.com.mysample.modal.pojo.IMResponse;
import com.jdsports.com.mysample.modal.pojo.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageRepository implements ImageDataSource {

    private static ImageRepository ourInstance;
    private List<Image> imageList;
    private HashMap<String, List<Image>> searchImages;
    private ImageRemoteDataSource remoteDataSource;
    private HashMap<String, Boolean> isServiceLoading;

    private ImageRepository(ImageRemoteDataSource remoteDataSource) {
        imageList = new ArrayList<>();
        this.remoteDataSource = remoteDataSource;
        this.isServiceLoading = new HashMap<>();
        this.isServiceLoading.put("getImages", false);
        this.isServiceLoading.put("getImageSearch", false);


    }

    public static ImageRepository getInstance(ImageRemoteDataSource remoteDataSource) {
        if (ourInstance == null) {
            ourInstance = new ImageRepository(remoteDataSource);
        }
        return ourInstance;
    }

    @Override
    public void getImages(NetworkStatus networkStats, final LoadCallBackListener callBackListener) {
        if (imageList.isEmpty()) {
            if (networkStats.isOnline()) {
                if (!isServiceLoading.get("getImages")) {
                    remoteDataSource.getImages(networkStats, new LoadCallBackListener() {
                        @Override
                        public void onLoaded(Object response) {
                            IMResponse imResponse = (IMResponse) response;
                            imageList.clear();
                            if (imResponse.getImages() != null && imResponse.getImages().size() > 0) {
                                imageList.addAll(imResponse.getImages());
                            }
                            callBackListener.onLoaded(imageList);
                        }

                        @Override
                        public void onError(Object error) {
                            callBackListener.onError(error);

                        }
                    });
                }
            } else {
                callBackListener.onError(ErrorCode.NETWORK_ERROR);
            }
        } else {
            callBackListener.onLoaded(imageList);
        }
    }

    @Override
    public void getImageSearch(NetworkStatus networkStats, String query, final LoadCallBackListener callBackListener) {
        if (!isSearchResultAvailable(query)) {
            if (networkStats.isOnline()) {
                if (!isServiceLoading.get("getImageSearch")) {
                    remoteDataSource.getImages(networkStats, new LoadCallBackListener() {
                        @Override
                        public void onLoaded(Object response) {
                            IMResponse imResponse = (IMResponse) response;
                            imageList.clear();
                            if (imResponse.getImages() != null && imResponse.getImages().size() > 0) {
                                imageList.addAll(imResponse.getImages());
                            }
                        }

                        @Override
                        public void onError(Object error) {
                            callBackListener.onError(error);

                        }
                    });
                }
            } else {
                callBackListener.onError(ErrorCode.NETWORK_ERROR);
            }
        } else {
            callBackListener.onLoaded(getSearchResultFromCache(query));
        }
    }

    public boolean isSearchResultAvailable(String query) {
        List<Image> searchResult = searchImages.get(query);
        if (searchResult == null && searchResult.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    List<Image> getSearchResultFromCache(String query) {
        List<Image> searchResult = searchImages.get(query);
        if (searchResult == null && searchResult.isEmpty()) {
            return null;
        } else {
            return searchResult;
        }
    }
}
