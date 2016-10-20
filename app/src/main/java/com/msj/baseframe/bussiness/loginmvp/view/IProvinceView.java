package com.msj.baseframe.bussiness.loginmvp.view;

import com.msj.baseframe.common.event.response.PronvinceResponse;
import com.msj.networkcore.mvp.view.IBaseView;
import com.msj.networkcore.utils.ExceptionHandle;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午6:54
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc 业务逻辑接口
 */
public interface IProvinceView extends IBaseView {
    void showFailedError(ExceptionHandle.ResponeThrowable e);

    void success(PronvinceResponse response);
}
