package com.msj.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.msj.core.runtimepermissions.PermissionsManager;
import com.msj.core.runtimepermissions.PermissionsResultAction;
import com.msj.core.utils.android.OnClickUtil;
import com.msj.core.utils.android.SharePreferenceStorageService;
import com.msj.core.utils.android.ToastUtil;
import com.msj.core.utils.android.ToastUtilHaveRight;
import com.msj.core.view.dialog.PrompfDialog;
import com.msj.networkcore.mvp.presentor.DemoPresentor;
import com.msj.networkcore.mvp.view.IBaseView;

/**
 * @author mengxiangcheng
 * @date 2016/10/11 上午11:03
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc BaseActivity
 */
public abstract class AbstractBaseActivity extends Activity implements IBaseView {

    /**
     * 快点时间
     */
    public final static int FAST_CLICK_TIME = 1000;

    public Dialog progressDialog;

    public static ToastUtil toastUtil;

    public static ToastUtilHaveRight toastUtilRight;

    public static SharePreferenceStorageService preferenceStorageService;

    public Context context;

    //DemoPresentor MVP模式，这里只是为了例子方便查看
    private DemoPresentor presenter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBasic();
        initFontScale();
        //Demo需要
//        presenter = new DemoPresentor(this,mRetrofit);
    }


    private void initBasic() {
        this.context = AbstractBaseActivity.this;
        preferenceStorageService = SharePreferenceStorageService
                .newInstance(getApplicationContext());
        if (toastUtil == null) {
            toastUtil = new ToastUtil(this);
        }
    }


    private void initFontScale() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1; //0.85 small size, 1 normal size, 1,15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }


    /**
     * 自定义对话框
     */

    PrompfDialog customDialog;

    /**
     *
     * @param context
     * @param content  提示内容
     * @param submitName 确认按钮
     * @param cancelName 取消按钮
     * @param listener 监听器
     */
    public void showCustomDialog(final AbstractBaseActivity context, String content, String submitName, String cancelName, PrompfDialog.UpdateOnclickListener listener) {

        customDialog = null;
        customDialog = new PrompfDialog(context,
                R.style.transparentFrameWindowStyle, submitName, cancelName,
                content, this.getString(R.string.app_name));
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(false);
        customDialog
                .setUpdateOnClickListener(listener);
        Window window = customDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        if (!isFinishing()) {
            customDialog.show();
        }


    }


    /**
     * 没有提示语
     */
    @Override
    public void showLoadingDialog() {
        showLoadingDialog(false, "");

    }

    @Override
    public void showLoadingDialog(boolean canCancel, String msg) {
        showLoadingDialog(canCancel, msg, false);
    }

    /**
     * 提示语为空则提示指点君，在路途中
     *
     * @param msg
     */
    @Override
    public void showLoadingDialog(String msg) {
        if (msg.isEmpty()) {
            msg = getString(R.string.loading);
        }
        showLoadingDialog(false, msg);

    }

    @Override
    public void showLoadingDialog(boolean canCancel, String msg, boolean alive) {
        if (!alive && progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (!(alive && progressDialog != null)) {
            progressDialog = new Dialog(this, R.style.progress_dialog);
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.setCancelable(canCancel);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent);
            TextView message = (TextView) progressDialog
                    .findViewById(R.id.id_tv_loadingmsg);
            if (msg.isEmpty()) {
                message.setVisibility(View.INVISIBLE);
            } else {
                message.setVisibility(View.VISIBLE);
                message.setText(msg);
            }
        }
        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showSystemToast(String msg) {
        if (!ToastUtil.isFastShow(3000)) {
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showSystemShortToast(String msg) {
        if (!ToastUtil.isFastShow(2000)) {
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showToast(String msg) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!ToastUtil.isFastShow(3000)) {
                toastUtil.show(msg);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void showToastRight(String msg) {
        try {
            if (toastUtilRight == null) {
                toastUtilRight = new ToastUtilHaveRight(this.getApplicationContext());
            }
            toastUtilRight.show(msg);
        } catch (Exception e) {
        }
    }

    @Override
    public void showToast(int res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            toastUtil.show(res);
        } catch (Exception e) {
        }
    }

    @Override
    public void showToastShort(int res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!OnClickUtil.isFastDoubleClick(3000)) {
                toastUtil.showShort(res);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void showToastShort(String res) {
        try {
            if (toastUtil == null) {
                toastUtil = new ToastUtil(this.getApplicationContext());
            }
            if (!OnClickUtil.isFastDoubleClick(2000)) {
                toastUtil.showShort(res);
            }
        } catch (Exception e) {
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
