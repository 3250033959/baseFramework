package com.msj.baseframe.bussiness.loginmvp.presenter;

import com.msj.baseframe.bussiness.loginmvp.biz.IProvinceBiz;
import com.msj.baseframe.bussiness.loginmvp.view.IProvinceView;
import com.msj.baseframe.common.event.ApiRequest;
import com.msj.baseframe.common.event.response.PronvinceResponse;
import com.msj.core.utils.java.GsonUtils;
import com.msj.networkcore.utils.BaseSubscriber;
import com.msj.networkcore.utils.ExceptionHandle;
import com.msj.networkcore.utils.RetrofitClient;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午6:41
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc MVP P层
 */
public class ProvinceGetPresenter {


    private IProvinceView provinceView;

    private IProvinceBiz provinceBiz;

    public ProvinceGetPresenter(IProvinceView provinceView){
        this.provinceView = provinceView;
        provinceBiz =  RetrofitClient.getInstance(provinceView.getContext()).create(IProvinceBiz.class);
    }

    public void get(){
        ApiRequest apiRequest = new ApiRequest(provinceView.getContext());
        RetrofitClient.execute(provinceBiz.listProvince(GsonUtils.toJson(apiRequest)), new BaseSubscriber<PronvinceResponse>(provinceView.getContext()) {
            @Override
            public void onNext(PronvinceResponse pronvinceResponse) {
                provinceView.success(pronvinceResponse);
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                provinceView.showFailedError(e);
            }
            @Override
            public void onStart() {
                super.onStart();
                provinceView.showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                provinceView.cancelLoadingDialog();
            }
        });
    }

}
