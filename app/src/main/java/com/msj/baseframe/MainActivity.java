package com.msj.baseframe;

import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.msj.baseframe.bussiness.loginmvp.LoginActivity_;
import com.msj.baseframe.common.activity.BaseActivity;
import com.msj.baseframe.common.activity.WebViewActivity;
import com.msj.core.utils.android.ImageUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {


//    private TextView tv_showtext;

    @ViewById(R.id.tv_showtext)
    TextView tv_showtext ;
    @ViewById(R.id.tv_h5)
    TextView tv_h5 ;
    @ViewById(R.id.tv_login)
    TextView tv_login;

    @ViewById(R.id.sdv)
    SimpleDraweeView sdv;

    @AfterViews
    void afterView(){
        ImageUtils.setSimpleDrawViewBy1618(sdv,"http://b.hiphotos.baidu.com/album/s%3D1600%3Bq%3D90/sign=4f04be8ab8014a90853e42bb99470263/b8389b504fc2d562d426d1d5e61190ef76c66cdf.jpg?v=tbs",0,0);
    }

    @Click(R.id.tv_h5)
    public void onclick_tv_h5(){
        Toast.makeText(MainActivity.this,"sdfsdf",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("url","http://www.sina.com.cn");
        startActivity(intent);
    }
    @Click(R.id.tv_login)
    public void onclick_tv_login(){
        Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
        startActivity(intent);
    }


}
