package com.msj.baseframe.bussiness.loginmvp.bean;

import com.google.gson.annotations.SerializedName;
import com.msj.networkcore.mvp.bean.BaseEntity;

import java.util.List;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午2:40
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class Province extends BaseEntity {

    @SerializedName("REC")
    private List<Rec> rec;

    public List<Rec> getRec() {
        return rec;
    }

    public void setRec(List<Rec> rec) {
        this.rec = rec;
    }
}
