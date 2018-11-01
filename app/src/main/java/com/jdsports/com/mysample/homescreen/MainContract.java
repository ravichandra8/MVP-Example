package com.jdsports.com.mysample.homescreen;

import com.jdsports.com.mysample.BasePresenter;
import com.jdsports.com.mysample.BaseView;
import com.jdsports.com.mysample.Util.NetworkStatus;
import com.jdsports.com.mysample.modal.pojo.Image;

import java.util.List;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        /**
         * show/hide loading indicatore
         * @param active status
         */
        void showLoadingIndicator(boolean active);

        /**
         * show error on snackbar
         * @param errorMsg msg to display
         */
        void showError(String errorMsg);

        /**
         * display image
         * @param imageList image data
         */
        void showImages(List<Image> imageList);

        /**
         * gets the error string
         * @param resourceId requested string
         * @return
         */
        String getErrorString(int resourceId);

        /**
         * start full image activity
         * @param url image url
         */
        void startFullImageActivity(String url);

        /**
         * show/hide network error layout
         * @param active status
         */
        void showNetworkError(boolean active);

        /**
         * setup and init UI element
         */
        void setUpUI();

    }

    interface Presenter extends BasePresenter {

        /**
         * gets the image list
         * @param networkStatus network interface
         */
        void getImages(NetworkStatus networkStatus);

        /**
         * gets filtered error
         * @param errorCode error Id
         * @return
         */
        String filterError(Object errorCode);

        /**
         * show full Image
         * @param url image url
         */
        void showFullImage(String url);

    }

    interface ImageClickListener {

        void onImageClick(String url);
    }
}
