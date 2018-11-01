package com.jdsports.com.mysample.homescreen;

import com.jdsports.com.mysample.R;
import com.jdsports.com.mysample.Util.ErrorCode;
import com.jdsports.com.mysample.Util.NetworkStatus;
import com.jdsports.com.mysample.modal.pojo.Image;
import com.jdsports.com.mysample.modal.ImageDataSource;
import com.jdsports.com.mysample.modal.ImageRepository;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private ImageRepository repository;

    public MainPresenter(MainContract.View view, ImageRepository repository) {
        this.view = view;
        this.repository = repository;
        this.view.setPresenter(this);
    }

    @Override
    public void getImages(NetworkStatus networkStatus) {
        view.showLoadingIndicator(true);
        repository.getImages(networkStatus, new ImageDataSource.LoadCallBackListener() {
            @Override
            public void onLoaded(Object response) {
                view.showLoadingIndicator(false);
                view.showImages((List<Image>) response);
            }

            @Override
            public void onError(Object error) {
                view.showLoadingIndicator(false);
                if ((int) error == ErrorCode.NETWORK_ERROR) {
                    view.showNetworkError(true);
                } else {
                    view.showError(filterError(error));
                }
            }
        });
    }

    @Override
    public String filterError(Object errorCode) {
        String errorMsg;
        switch ((int) errorCode) {
            case ErrorCode.CONNECTION_PROBLEM:
                errorMsg = view.getErrorString(R.string.connection_problem);
                break;
            case ErrorCode.NETWORK_ERROR:
                errorMsg = view.getErrorString(R.string.network_problem);
                break;

            case ErrorCode.NO_RESULT:
                errorMsg = view.getErrorString(R.string.no_result);
                break;
            default:
                errorMsg = view.getErrorString(R.string.connection_problem);
                break;
        }
        return errorMsg;
    }

    @Override
    public void showFullImage(String url) {
        view.startFullImageActivity(url);
    }

    @Override
    public void start() {
        view.setUpUI();
    }
}
