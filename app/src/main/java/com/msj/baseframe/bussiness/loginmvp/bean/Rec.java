package com.msj.baseframe.bussiness.loginmvp.bean;

import com.google.gson.annotations.SerializedName;
import com.msj.networkcore.mvp.bean.BaseEntity;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午6:39
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class Rec extends BaseEntity {

    @SerializedName("KEY")
    private String key;

    @SerializedName("VALUE")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
