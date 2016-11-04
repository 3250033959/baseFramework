package com.msj.baseframe.bussiness.loginmvp.biz;


import com.msj.baseframe.common.event.response.PronvinceResponse;
import com.msj.networkcore.mvp.bean.ApiResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午6:35
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public interface IProvinceBiz {

    @FormUrlEncoded
    @POST("rest/transfermob/queryprovince")
    Observable<PronvinceResponse> listProvince(@Field("data") String json);

    /**
     * 使用非baseurl,传过来一个新baseurl,或者直接写全路径不用传递
     * @param url
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("{url}/api/v1/test")
    Observable<ApiResponse> getData(@Path(value = "url",encoded = true) String url, @Field("data") String json);

    /**
     * baseUrl情况
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api/v1/test")
    Observable<ApiResponse> getData(@Field("data") String json);
}
