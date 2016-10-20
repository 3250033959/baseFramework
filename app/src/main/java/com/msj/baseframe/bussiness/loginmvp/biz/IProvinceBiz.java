package com.msj.baseframe.bussiness.loginmvp.biz;


import com.msj.baseframe.common.event.response.PronvinceResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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

}
