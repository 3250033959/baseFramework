package com.msj.core.utils.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Animatable;
import android.media.FaceDetector;
import android.net.Uri;

import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.msj.core.utils.constant.ImageConstant;
import com.msj.core.utils.constant.LogConstant;
import com.msj.core.utils.log.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author CentMeng csdn@vip.163.com on 15/11/26.
 */
public class ImageUtils {

    /**
     * SimpleDrawView
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawView(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
    }

    /**
     * SimpleDrawView黄金比，宽高比,配合人脸识别
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618(final SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        sdv.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = sdv.getDrawingCache();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        PointF pointF = setFace(bitmap, sdv.getWidth(), sdv.getHeight());
                        //按百分比算，防止偏差
                        final PointF pointF1 = new PointF(pointF.x / (float) sdv.getWidth(), pointF.y / (float) sdv.getHeight());
//                        Activity activity = (Activity)sdv.getContext();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                        sdv.getHierarchy()
                                .setActualImageFocusPoint(pointF1);
                        sdv.setDrawingCacheEnabled(false);
//
//                            }
//                        });
                    }
//                }).start();


//        }

                    @Override
                    public void onIntermediateImageSet(String id, Object imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                }

        ));
        sdv.setAspectRatio(1.618f);
//        BitmapDrawable drawable = (BitmapDrawable) sdv.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        PointF pointF = setFace(bitmap,100,100);
//        sdv.getHierarchy()
//                .setActualImageFocusPoint(pointF);
    }

    /**
     * SimpleDrawView黄金比，宽高比,配合人脸识别，异步处理人脸识别
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618Ascy(final SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, Object imageInfo, Animatable animatable) {
                        sdv.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = sdv.getDrawingCache();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PointF pointF = setFace(bitmap, sdv.getWidth(), sdv.getHeight());
                                //按百分比算，防止偏差
                                final PointF pointF1 = new PointF(pointF.x / (float) sdv.getWidth(), pointF.y / (float) sdv.getHeight());
                                Activity activity = (Activity) sdv.getContext();
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        sdv.getHierarchy()
                                                .setActualImageFocusPoint(pointF1);
                                        sdv.setDrawingCacheEnabled(false);

                                    }
                                });
                            }
                        }).start();


                    }

                    @Override
                    public void onIntermediateImageSet(String id, Object imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                }

        ));
        sdv.setAspectRatio(1.618f);
    }

    /**
     * SimpleDrawView黄金比，宽高比
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1618WithoutFace(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1.618f);
    }


    /**
     * SimpleDrawView14比，宽高比
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy14(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1.4f);
    }

    /**
     * SimpleDrawView，宽高比0727,首页弹出的广告图
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy0727(SimpleDraweeView sdv, String preurl, int width, int height, ControllerListener controllerListener) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, controllerListener));
        sdv.setAspectRatio(0.727f);
    }

    /**
     * SimpleDrawView，宽高比4437,首页专题图
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy4437(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(4.437f);
    }

    /**
     * SimpleDrawView，宽高比1:1
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewBy1(SimpleDraweeView sdv, String preurl, int width, int height) {
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, null));
        sdv.setAspectRatio(1f);
    }


    /**
     * 根据宽高展现
     *
     * @param sdv
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewByWh(SimpleDraweeView sdv, String preUrl, int width, int height) {
        String url = getUrl(preUrl, width, height);
        sdv.setController(getController(sdv, url, null));
        float f = (float) width / (float) height;
        if (f <= 0.5) {
            f = 0.727f;
        }
        sdv.setAspectRatio(f);
        /**
         * 第一次时候不显示问题
         */
//        if(width != 0 && height != 0) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
//            sdv.setLayoutParams(params);
//        }
    }


    /**
     * 未知尺寸，获取完图片之后根据比例展示
     *
     * @param sdv
     * @param preurl
     * @param width
     * @param height
     */
    public static void setSimpleDrawViewUnknowSizeByQiniu(final SimpleDraweeView sdv, final String preurl, final int width, final int height) {
//        final QiniuImageInfoRequest request = new QiniuImageInfoRequest(preurl);
//
//        request.setListener(new Response.Listener<ImageInfo>() {
//            @Override
//            public void onResponse(ImageInfo response) {
//
//                int width = sdv.getWidth();
//                float bili = (float) response.getHeight() / (float) response.getWidth();
//                float height = width * bili;
//                setSimpleDrawViewByWh(sdv, preurl, width, (int) height);
//            }
//        });
//        request.setErrorlistener(new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                String url = getUrl(preurl, width, height);
//                setSimpleDrawViewBy1618(sdv, url, 0, 0);
//            }
//        });
//        if (useCache) {
//            activity.volleyHttpClient.doNetTask(VolleyHttpClient.GET, request, DataCacheType.TEMP_CACHE);
//        } else {
//            activity.volleyHttpClient.doNetTask(VolleyHttpClient.GET, request, DataCacheType.NO_CACHE);
//        }
        String url = getUrl(preurl, width, height);
        sdv.setController(getController(sdv, url, new ControllerListener<ImageInfo>() {
            @Override
            public void onSubmit(String id, Object callerContext) {

            }

            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                LogUtils.e(LogConstant.RESULT_OPERA, "width=" + imageInfo.getWidth() + "height=" + imageInfo.getHeight() + " bili=" + (float) imageInfo.getWidth() / (float) imageInfo.getHeight());
                float f = (float) imageInfo.getWidth() / (float) imageInfo.getHeight();
                if (f <= 0.5) {
                    f = 0.727f;
                }
                sdv.setAspectRatio(f);
            }

            @Override
            public void onIntermediateImageSet(String id, ImageInfo imageInfo) {

            }

            @Override
            public void onIntermediateImageFailed(String id, Throwable throwable) {

            }

            @Override
            public void onFailure(String id, Throwable throwable) {

            }

            @Override
            public void onRelease(String id) {

            }
        }));
        sdv.setAspectRatio(1f);
    }


    private static String getUrl(String preUrl, int width, int height) {
        String url = preUrl;
        if (width != 0 && height != 0) {
            url = String.format(ImageConstant.QINIU_CUTPHOTO_BYSIZE, preUrl, width, height);
        } else if (width != 0 && height == 0) {
            url = String.format(ImageConstant.QINIU_CUTPHOTO_BYSIZE_W, preUrl, width);
        }
        return url;
    }

    private static DraweeController getController(SimpleDraweeView sdv, String url, ControllerListener controllerListener) {
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder().setImageRequest(request)
                .setControllerListener(controllerListener)
                .setOldController(sdv.getController())
                .build();
        return controller;
    }


    public static Bitmap getImageByFile(String uri) {
        try {
            FileInputStream fis = new FileInputStream(uri);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从服务器取图片，并保存流
     *
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static PointF setFace(Bitmap bitmap, int width, int height) {
        FaceDetector fd;
        FaceDetector.Face[] faces = new FaceDetector.Face[1];
        PointF midpoint = new PointF(width / 2.0f, height / 2.0f);
        int count = 0;
        try {

            Bitmap faceBitmap = bitmap.copy(Bitmap.Config.RGB_565, true);
            bitmap.recycle();
            fd = new FaceDetector(faceBitmap.getWidth(), faceBitmap.getHeight(), 1);
            count = fd.findFaces(faceBitmap, faces);
            faceBitmap.recycle();
        } catch (Exception e) {
            LogUtils.e("检测脸部", "setFace(): " + e.toString());
            return midpoint;
        }

// check if we detect any faces
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                try {
                    faces[i].getMidPoint(midpoint);
                } catch (Exception e) {
                    LogUtils.e("检测脸部", "setFace(): face " + i + ": " + e.toString());
                }
            }

        }
        return midpoint;
    }





    /**
     * Fresco配置信息----------------------------------------
     */

    private static ImagePipelineConfig sImagePipelineConfig;

    /**
     * 初始化配置，单例
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            synchronized (ImagePipelineConfig.class) {
                if (sImagePipelineConfig == null) {
                    sImagePipelineConfig = configureCaches(context);
                }
            }
        }
        return sImagePipelineConfig;
    }



    /**
     * 初始化配置
     */
    private static ImagePipelineConfig configureCaches(Context context) {

        //得到当前外部存储设备的目录
        File filePath = FileUtils.getDiskCacheDir(context,"");

        //内存配置
//        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
//                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // 内存缓存中总图片的最大大小,以字节为单位。
//                Integer.MAX_VALUE,                     // 内存缓存中图片的最大数量。
//                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
//                Integer.MAX_VALUE,                     // 内存缓存中准备清除的总图片的最大数量。
//                ConfigConstants.MAX_MEMORY_CACHE_SIZE);                    // 内存缓存中单个图片的最大大小。

        //修改内存图片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams= new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                        (int)Runtime.getRuntime().freeMemory(), // 内存缓存中总图片的最大大小,以字节为单位。
                        Integer.MAX_VALUE,                     // 内存缓存中图片的最大数量。
                        (int)Runtime.getRuntime().freeMemory(), // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                        Integer.MAX_VALUE,                     // 内存缓存中准备清除的总图片的最大数量。
                        (int)Runtime.getRuntime().freeMemory());
                LogUtils.e(LogConstant.DATA_OUTPUT,"******图片内存"+bitmapCacheParams.maxCacheSize+"---"+Runtime.getRuntime().freeMemory());
                return bitmapCacheParams;
            }
        };

        //小图片的磁盘配置
        DiskCacheConfig diskSmallCacheConfig =  DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(filePath)//缓存图片基路径
                .setBaseDirectoryName(ImageConstant.IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
//            .setCacheErrorLogger(cacheErrorLogger)//日志记录器用于日志错误的缓存。
//            .setCacheEventListener(cacheEventListener)//缓存事件侦听器。
//            .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(ImageConstant.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(ImageConstant.MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(ImageConstant.MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
//            .setVersion(version)
                .build();

        //默认图片的磁盘配置
        DiskCacheConfig diskCacheConfig =  DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(filePath)//缓存图片基路径
                .setBaseDirectoryName(ImageConstant.IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setCacheErrorLogger(new CacheErrorLogger() {
                    @Override
                    public void logError(CacheErrorCategory category, Class<?> clazz, String message, Throwable throwable) {
                        LogUtils.e(LogConstant.DATA_OUTPUT,"*******图片错误"+message);
                    }
                })//日志记录器用于日志错误的缓存。
                //缓存事件侦听器。
//            .setDiskTrimmableRegistry(diskTrimmableRegistry)//类将包含一个注册表的缓存减少磁盘空间的环境。
                .setMaxCacheSize(ImageConstant.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(ImageConstant.MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(ImageConstant.MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
//            .setVersion(version)
                .build();

        //渐变配置
        ProgressiveJpegConfig progressiveJpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };

        //缓存图片配置
        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//            .setAnimatedImageFactory(AnimatedImageFactory animatedImageFactory)//图片加载动画
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的图片）
//            .setCacheKeyFactory(cacheKeyFactory)//缓存Key工厂
//            .setEncodedMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存和未解码的内存缓存的配置（二级缓存）
//            .setExecutorSupplier(executorSupplier)//线程池配置
//            .setImageCacheStatsTracker(imageCacheStatsTracker)//统计缓存的命中率
//            .setImageDecoder(ImageDecoder imageDecoder) //图片解码器配置
//            .setIsPrefetchEnabledSupplier(Supplier<Boolean> isPrefetchEnabledSupplier)//图片预览（缩略图，预加载图等）预加载到文件缓存
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
//            .setMemoryTrimmableRegistry(memoryTrimmableRegistry) //内存用量的缩减,有时我们可能会想缩小内存用量。比如应用中有其他数据需要占用内存，不得不把图片缓存清除或者减小 或者我们想检查看看手机是否已经内存不够了。
//            .setNetworkFetchProducer(networkFetchProducer)//自定的网络层配置：如OkHttp，Volley
//            .setPoolFactory(poolFactory)//线程池工厂配置
                .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式JPEG图
//            .setRequestListeners(requestListeners)//图片请求监听
//            .setResizeAndRotateEnabledForNetwork(boolean resizeAndRotateEnabledForNetwork)//调整和旋转是否支持网络图片
                .setSmallImageDiskCacheConfig(diskSmallCacheConfig)//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
                ;
        return configBuilder.build();
    }
    //圆形，圆角切图，对动图无效
//    public static RoundingParams getRoundingParams(){
//        RoundingParams roundingParams = RoundingParams.fromCornersRadius(7f);
////    roundingParams.asCircle();//圆形
////    roundingParams.setBorder(color, width);//fresco:roundingBorderWidth="2dp"边框  fresco:roundingBorderColor="@color/border_color"
////    roundingParams.setCornersRadii(radii);//半径
////    roundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft)//fresco:roundTopLeft="true" fresco:roundTopRight="false" fresco:roundBottomLeft="false" fresco:roundBottomRight="true"
////    roundingParams. setCornersRadius(radius);//fresco:roundedCornerRadius="1dp"圆角
////    roundingParams.setOverlayColor(overlayColor);//fresco:roundWithOverlayColor="@color/corner_color"
////    roundingParams.setRoundAsCircle(roundAsCircle);//圆
////    roundingParams.setRoundingMethod(roundingMethod);
////    fresco:progressBarAutoRotateInterval="1000"自动旋转间隔
//        // 或用 fromCornersRadii 以及 asCircle 方法
//        return roundingParams;
//    }
//
//    //Drawees   DraweeHierarchy  组织
//    public static GenericDraweeHierarchy getGenericDraweeHierarchy(Context context){
//        GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(context.getResources())
////            .reset()//重置
////            .setActualImageColorFilter(colorFilter)//颜色过滤
////            .setActualImageFocusPoint(focusPoint)//focusCrop, 需要指定一个居中点
////            .setActualImageMatrix(actualImageMatrix)
////            .setActualImageScaleType(actualImageScaleType)//fresco:actualImageScaleType="focusCrop"缩放类型
////            .setBackground(background)//fresco:backgroundImage="@color/blue"背景图片
////            .setBackgrounds(backgrounds)
////            .setFadeDuration(fadeDuration)//fresco:fadeDuration="300"加载图片动画时间
//                .setFailureImage(ConfigConstants.sErrorDrawable)//fresco:failureImage="@drawable/error"失败图
////            .setFailureImage(failureDrawable, failureImageScaleType)//fresco:failureImageScaleType="centerInside"失败图缩放类型
////            .setOverlay(overlay)//fresco:overlayImage="@drawable/watermark"叠加图
////            .setOverlays(overlays)
//                .setPlaceholderImage(ConfigConstants.sPlaceholderDrawable)//fresco:placeholderImage="@color/wait_color"占位图
////            .setPlaceholderImage(placeholderDrawable, placeholderImageScaleType)//fresco:placeholderImageScaleType="fitCenter"占位图缩放类型
////            .setPressedStateOverlay(drawable)//fresco:pressedStateOverlayImage="@color/red"按压状态下的叠加图
//                .setProgressBarImage(new ProgressBarDrawable())//进度条fresco:progressBarImage="@drawable/progress_bar"进度条
////            .setProgressBarImage(progressBarImage, progressBarImageScaleType)//fresco:progressBarImageScaleType="centerInside"进度条类型
////            .setRetryImage(retryDrawable)//fresco:retryImage="@drawable/retrying"点击重新加载
////            .setRetryImage(retryDrawable, retryImageScaleType)//fresco:retryImageScaleType="centerCrop"点击重新加载缩放类型
//                .setRoundingParams(RoundingParams.asCircle())//圆形/圆角fresco:roundAsCircle="true"圆形
//                .build();
//        return gdh;
//    }
//
//
////DraweeView～～～SimpleDraweeView——UI组件
////  public static SimpleDraweeView getSimpleDraweeView(Context context,Uri uri){
////    SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
////    simpleDraweeView.setImageURI(uri);
////    simpleDraweeView.setAspectRatio(1.33f);//宽高缩放比
////    return simpleDraweeView;
////  }
//
//    //SimpleDraweeControllerBuilder
//    public static SimpleDraweeControllerBuilder getSimpleDraweeControllerBuilder(SimpleDraweeControllerBuilder sdcb,Uri uri,  Object callerContext,DraweeController draweeController){
//        SimpleDraweeControllerBuilder controllerBuilder = sdcb
//                .setUri(uri)
//                .setCallerContext(callerContext)
////              .setAspectRatio(1.33f);//宽高缩放比
//                .setOldController(draweeController);
//        return controllerBuilder;
//    }
//
//    //图片解码
//    public static ImageDecodeOptions getImageDecodeOptions(){
//        ImageDecodeOptions decodeOptions = ImageDecodeOptions.newBuilder()
////            .setBackgroundColor(Color.TRANSPARENT)//图片的背景颜色
////            .setDecodeAllFrames(decodeAllFrames)//解码所有帧
////            .setDecodePreviewFrame(decodePreviewFrame)//解码预览框
////            .setForceOldAnimationCode(forceOldAnimationCode)//使用以前动画
////            .setFrom(options)//使用已经存在的图像解码
////            .setMinDecodeIntervalMs(intervalMs)//最小解码间隔（分位单位）
//                .setUseLastFrameForPreview(true)//使用最后一帧进行预览
//                .build();
//        return decodeOptions;
//    }
//
//    //图片显示
//    public static ImageRequest getImageRequest(InstrumentedDraweeView view,String uri){
//        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
////            .setAutoRotateEnabled(true)//自动旋转图片方向
////            .setImageDecodeOptions(getImageDecodeOptions())//  图片解码库
////            .setImageType(ImageType.SMALL)//图片类型，设置后可调整图片放入小图磁盘空间还是默认图片磁盘空间
////            .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
//                .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)//请求经过缓存级别  BITMAP_MEMORY_CACHE，ENCODED_MEMORY_CACHE，DISK_CACHE，FULL_FETCH
////            .setPostprocessor(postprocessor)//修改图片
////            .setProgressiveRenderingEnabled(true)//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）
//                .setResizeOptions(new ResizeOptions(view.getLayoutParams().width, view.getLayoutParams().height))//调整大小
////            .setSource(Uri uri)//设置图片地址
//                .build();
//        return imageRequest;
//    }
//
//    //DraweeController 控制 DraweeControllerBuilder
//    public static DraweeController getDraweeController(ImageRequest imageRequest,InstrumentedDraweeView view){
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
////            .reset()//重置
//                .setAutoPlayAnimations(true)//自动播放图片动画
////            .setCallerContext(callerContext)//回调
//                .setControllerListener(view.getListener())//监听图片下载完毕等
////            .setDataSourceSupplier(dataSourceSupplier)//数据源
////            .setFirstAvailableImageRequests(firstAvailableImageRequests)//本地图片复用，可加入ImageRequest数组
//                .setImageRequest(imageRequest)//设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
////            .setLowResImageRequest(ImageRequest.fromUri(lowResUri))//先下载显示低分辨率的图
//                .setOldController(view.getController())//DraweeController复用
//                .setTapToRetryEnabled(true)//点击重新加载图
//                .build();
//        return draweeController;
//    }
//
//    //默认加载图片和失败图片
//    public static Drawable sPlaceholderDrawable;
//    public static Drawable sErrorDrawable;
//
//    @SuppressWarnings("deprecation")
//    public static void init(final Resources resources) {
//        if (sPlaceholderDrawable == null) {
//            sPlaceholderDrawable = resources.getDrawable(R.color.placeholder);
//        }
//        if (sErrorDrawable == null) {
//            sErrorDrawable = resources.getDrawable(R.color.error);
//        }
//    }
}
