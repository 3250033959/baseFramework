package com.msj.baseframe.bussiness.loginmvp;

import android.view.View;
import android.widget.TextView;

import com.msj.baseframe.R;
import com.msj.baseframe.bussiness.loginmvp.presenter.ProvinceGetPresenter;
import com.msj.baseframe.bussiness.loginmvp.view.IProvinceView;
import com.msj.baseframe.common.activity.BaseActivity;
import com.msj.baseframe.common.event.response.PronvinceResponse;
import com.msj.networkcore.utils.ExceptionHandle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author mengxiangcheng
 * @date 2016/10/12 下午2:38
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements IProvinceView {

    @ViewById
    TextView tv_test001;

    private ProvinceGetPresenter presenter ;

    @AfterViews
    void afterView(){
        presenter = new ProvinceGetPresenter(this);
    }


    public void test(View view){
        presenter.get();
    }

    @Override
    public void showFailedError(ExceptionHandle.ResponeThrowable e) {
        showSystemShortToast(e.getMessage());
    }

    @Override
    public void success(PronvinceResponse response) {
        showSystemToast(response.getHeader().getRepcd()+"------"+response.getHeader().getRepmsg());
    }

}
