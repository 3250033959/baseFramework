package com.msj.baseframe.common.event;


import android.content.Context;

import com.msj.baseframe.common.event.request.RequestHeader;


/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午7:57
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class RequestHeaderUtils {

    public static RequestHeader getHeaderRequest(Context context){
        RequestHeader header = new RequestHeader();
//        header.setSESSIONID(SharePreStore.get(ElectricPayApplication.getContext(),"tempSessionId"));
//        header.setPLAT("3");
//        header.setCLIENTVERSION("android" + android.os.Build.VERSION.RELEASE);
//        header.setSDKVERSION("");
//        header.setAPPVERSION(PackageUtil.getAppVersionName());
//        header.setMOBILETOKEN(SharePreStore.get(ElectricPayApplication.getContext(),"MOBILETOKEN"));
        return header;
    }

}
