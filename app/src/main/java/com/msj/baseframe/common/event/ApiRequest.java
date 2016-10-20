package com.msj.baseframe.common.event;


import android.content.Context;

import com.msj.baseframe.common.event.request.RequestHeader;

import java.util.HashMap;
import java.util.Map;


/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午2:51
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class ApiRequest {

    private RequestHeader header;

    private Map<String,String> data;

    public ApiRequest(Map<String,String> dataMap, Context context){
        header = RequestHeaderUtils.getHeaderRequest(context);
        this.data = dataMap;
    }

    public ApiRequest(Context context){
        header = RequestHeaderUtils.getHeaderRequest(context);
        Map<String,String> map = new HashMap<>();
//        map.put("SESSIONID", SharePreStore.get(ElectricPayApplication.getContext(),"tempSessionId"));
        this.data = map;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
