package com.msj.core;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.msj.core.utils.android.ImageUtils;

import org.litepal.LitePalApplication;


/**
 * @author mengxiangcheng
 * @date 2016/10/12 上午8:32
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class BaseApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
    }

    /**
     * onCreate时候Fresco初始化
     */
    private void initFresco() {
        ImagePipelineConfig config = ImageUtils.getImagePipelineConfig(this);
        Fresco.initialize(this, config);
    }

}
