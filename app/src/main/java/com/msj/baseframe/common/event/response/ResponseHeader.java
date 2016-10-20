package com.msj.baseframe.common.event.response;


import com.msj.networkcore.mvp.bean.BaseEntity;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午2:53
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class ResponseHeader extends BaseEntity {

    private String repcd;

    private String repmsg;

    public String getRepcd() {
        return repcd;
    }

    public void setRepcd(String repcd) {
        this.repcd = repcd;
    }

    public String getRepmsg() {
        return repmsg;
    }

    public void setRepmsg(String repmsg) {
        this.repmsg = repmsg;
    }
}
