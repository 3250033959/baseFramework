package com.msj.core.utils.constant;

import com.facebook.common.util.ByteConstants;

/**
 * @author mengxiangcheng
 * @date 2016/10/11 下午4:49
 * @copyright ©2016 孟祥程 All Rights Reserved
 * @desc
 */
public class ImageConstant {

    /**
     * 七牛图片截取
     */
    public final static String QINIU_CUTPHOTO = "?vframe/png/offset/1/w/500/h/333";

    /**
     * 七牛获取视频元信息
     */
    public final static String QINNIU_VIDEOINFO = "?avinfo";

    /**
     * 七牛添加视频水印  aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS93YXRlcm1hcmsucG5n  为水印图片Base64加密地址
     * aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zbWFsbF93YXRlcm1hcmsucG5n  小图片
     * NorthWest     |     North      |     NorthEast
     * |                |
     * |                |
     * --------------+----------------+--------------
     * |                |
     * West          |     Center     |          East
     * |                |
     * --------------+----------------+--------------
     * |                |
     * |                |
     * SouthWest     |     South      |     SouthEast
     */
    public final static String QINIU_WATERMARK = "?avthumb/1/wmImage/aHR0cDovLzd4bmllaC5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zbWFsbF93YXRlcm1hcmsucG5n/wmGravity/SouthEast";

    /**
     * 七牛图片水印  dissolve 透明度
     */
    public final static String QINIU_PICTUREWATERMARK = "?imageView/2/w/500/333|watermark/2/text/5oyH54K5/font/5a6L5L2T/fontsize/640/fill/d2hpdGU=/gravity/SouthWest/dissolve/100/dx/1";

    public final static String DISCLOSE_IMAGEURL = "http://7xqxpm.com1.z0.glb.clouddn.com/headline_128.png";

    /**
     * 七牛图片截取
     */
    public final static String QINIU_SMALL_CUTPHOTO = "?imageView/2/w/100/h/100";

    /**
     * 七牛图片截取
     */
    public final static String QINIU_CUTPHOTO_BYSIZE = "%1$s?imageView/2/w/%2$d/h/%3$d";

    public final static String QINIU_CUTPHOTO_BYSIZE_W = "%1$s?imageView/2/w/%2$d";

    /**
     * 七牛按质量缩小
     */
    public final static String QINIU_CUTPHOTO_BYQUALITY = "%1$s?imageView2/2/q/%2$d";

    public static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().freeMemory();//分配的可用内存
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//使用的缓存数量

    public static final int MAX_SMALL_DISK_VERYLOW_CACHE_SIZE = 30 * ByteConstants.MB;//小图极低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_LOW_CACHE_SIZE = 30 * ByteConstants.MB;//小图低磁盘空间缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）
    public static final int MAX_SMALL_DISK_CACHE_SIZE = 20 * ByteConstants.MB;//小图磁盘缓存的最大值（特性：可将大量的小图放到额外放在另一个磁盘空间防止大图占用磁盘空间而删除了大量的小图）

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 30 * ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 50 * ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 150 * ByteConstants.MB;//默认图磁盘缓存的最大值


    public static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "smallpic";//小图所放路径的文件夹名
    public static final String IMAGE_PIPELINE_CACHE_DIR = "pic";//默认图所放路径的文件夹名

}
