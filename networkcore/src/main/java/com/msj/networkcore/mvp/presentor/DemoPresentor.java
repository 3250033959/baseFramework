package com.msj.networkcore.mvp.presentor;


import com.msj.networkcore.mvp.biz.IBaseBiz;
import com.msj.networkcore.mvp.view.IBaseView;
import com.msj.networkcore.utils.RetrofitClient;

import retrofit2.Retrofit;

/**
 * @author mengxiangcheng
 * @date 2016/10/13 下午1:35
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class DemoPresentor {

    private IBaseBiz demoBiz;

    private IBaseView baseView;

    public DemoPresentor(IBaseView baseView, Retrofit retrofit){
        this.baseView = baseView;
        demoBiz =  RetrofitClient.getInstance(baseView.getContext()).create(IBaseBiz.class);
    }

    public void get(){

        //公共的
//        RetrofitClient.getInstance(baseView.getContext()).createBaseApi().postForm("service/getIpInfo.php"
//                , "", new BaseSubscriber<ApiResponse<Object>>(baseView.getContext()) {
//
//                    @Override
//                    public void onNext(ApiResponse<Object> baseEntityApiResponse) {
//
//                    }
//
//                    @Override
//                    public void onError(ExceptionHandle.ResponeThrowable e) {
//                        Log.e("msj", e.getMessage());
//
//                    }
//
//                });

        //自定义的
//        RetrofitClient.getInstance(baseView.getContext(),"https://app.95598pay.com/mobilepay/",null,true).execute(demoBiz.listProvince(""), new BaseSubscriber<ApiResponse>(baseView.getContext()) {
//            @Override
//            public void onNext(ApiResponse pronvinceResponse) {
//            }
//
//            @Override
//            public void onError(ExceptionHandle.ResponeThrowable e) {
//            }
//            @Override
//            public void onStart() {
//                super.onStart();
//                baseView.showLoadingDialog();
//            }
//
//            @Override
//            public void onCompleted() {
//                super.onCompleted();
//                baseView.cancelLoadingDialog();
//            }
//        });

    }

}
