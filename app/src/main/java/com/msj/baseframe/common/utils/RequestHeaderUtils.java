package com.msj.baseframe.common.utils;


import com.msj.baseframe.common.event.request.RequestHeader;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午7:57
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class RequestHeaderUtils {

    public static RequestHeader getHeaderRequest(){
        RequestHeader header = new RequestHeader();
//        header.setSESSIONID(BaseActivity.preferenceStorageService(""));
        header.setPLAT("3");
        header.setCLIENTVERSION("android6.0.1");
        header.setSDKVERSION("1.0");
        header.setAPPVERSION("2.7.8");
        header.setMOBILETOKEN("");
        header.setIP("");
        return header;
    }

}
