package com.msj.baseframe.common.event.request;


import com.msj.networkcore.mvp.bean.BaseEntity;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午2:53
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class RequestHeader extends BaseEntity {

    private String APPVERSION;

    private String CLIENTVERSION;

    private String MOBILETOKEN;

    private String PLAT;

    private String SDKVERSION;

    private String SESSIONID;

    private String IP;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getAPPVERSION() {
        return APPVERSION;
    }

    public void setAPPVERSION(String APPVERSION) {
        this.APPVERSION = APPVERSION;
    }

    public String getCLIENTVERSION() {
        return CLIENTVERSION;
    }

    public void setCLIENTVERSION(String CLIENTVERSION) {
        this.CLIENTVERSION = CLIENTVERSION;
    }

    public String getMOBILETOKEN() {
        return MOBILETOKEN;
    }

    public void setMOBILETOKEN(String MOBILETOKEN) {
        this.MOBILETOKEN = MOBILETOKEN;
    }

    public String getPLAT() {
        return PLAT;
    }

    public void setPLAT(String PLAT) {
        this.PLAT = PLAT;
    }

    public String getSDKVERSION() {
        return SDKVERSION;
    }

    public void setSDKVERSION(String SDKVERSION) {
        this.SDKVERSION = SDKVERSION;
    }

    public String getSESSIONID() {
        return SESSIONID;
    }

    public void setSESSIONID(String SESSIONID) {
        this.SESSIONID = SESSIONID;
    }
}
