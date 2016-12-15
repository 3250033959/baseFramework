# android框架简介

---
***本框架网络部分采用 [Retrofit2.0](https://centmeng.github.io/2016/10/17/Retrofit2%E4%BB%8B%E7%BB%8D/)+okhttp3+[Rxjava](http://gank.io/post/560e15be2dca930e00da1083)搭建，网络框架内容主要在networkcore中体现，并且在htjccore中集成了图片加载框架Fresco,Glide和Litepal数据库框架以及各Utils和自定义控件以及BaseActivity，和BaseAllication。在项目中使用采用MVP模式进行开发***

## 配置使用在根build.gradle配置信息如

```
ext {
    compileSdkVersion = 23
    buildToolsVersion = '23.0.2'
    minSdkVersion = 14
    targetSdkVersion = 23
    versionCode = 1
    versionName = "0.0.1"
    //控制生产或测试的日志是否输出
    logDebug = false
    supportLib = '23.4.0'
    retrofitVersion = '2.1.0'
    okHttpVersion = '3.4.1'
    converterGsonVersion = '2.1.0'
    retrofitrxJava = '2.1.0'
    rxJava = '1.0.14'
    rxAndroid = '1.0.1'
    litepal = '1.3.2'
    fresco = '0.12.0'
    glide = '3.7.0'
}
```

## library
---
### htjccore
#### runtimepermissions 运行时权限获取，通过BaseActivity的requestPermissions（）方法获取

#### utils
- android
 - richText 富文本操作类，图文混排支持网络连接自动识别以及gif动画播放，此处用到Glide网络框架 
 - ActivityCollector  Activiy管理工具类，包含add，remove等方法。
 - BaseTools android基本信息获取类，包含获取metadata，versionCode,versionName信息和判断是否安装某应用和安装应用辅助方法。
 - BitmapUtils 包含通过uri获取压缩处理后的Bitmap和未压缩处理的Bitmap方法，以及图片压缩方法。
 - DisplayUtils 屏幕相关工具类，包含dp转px，px转dp，获取屏幕宽和高以及Metrics等信息。
 - FileUtils 文件相关工具类，包含获取raw,asset文件夹下资源，获取文件大小，判断文件或者文件夹是否存在以及获取缓存和创建缓存目录和文件目录等方法。
 - ImageUtils Fresco加载图片帮助类
 - MemorySpaceUtils 内存空间计算类
 - NetWorkUtils 网络相关信息获取类，包含判断网络是否可用，是否wifi情况，是否漫游情况等，
 - OnClickUtil 快点点击处理类
 - Path 路径工具类，包含根据uri获取绝对路径方法
 - SharePreferenceStorageService SharePreference数据库操作辅助类。其中key写道constant中的SharePreference
 - SignUtils 签名操作类，反射实现
 - ToastUtil 仿Toast的在屏幕中间显示
 - ToastUtilsHaveRight 仿Toast在屏幕中间显示（有对勾符号）

- download
 - DownloadTask 下载文件采用多个线程下载一个文件的方式 

- Java
 - CalendarUtils Calendar工具操作
 - DateUtils 日期处理类
 - GsonUtils Gson转换工具类
 - JsonUtils Json转换工具类
 - MoneyUtil 钱币相关
 - NumberUtils 数字处理工具类
 - ObjectUtils Object判断工具类
 - StringUtils 字符串工具类
 - UrlUtils Url相关处理工具类
 - ValidateUtils 正则判断工具类

- log
 - LogUtils Log输出工具类，Tag在LogConstants中处理

- media
 - VoiceRecorder 录音工具类 
 
- security
 - AesUtil Aes加密工具类

#### view
- dialog
 - PrompfDialog 公共提示对话框
- guide 引导页，可以参照GuideDemo
- refreshLayout 下拉刷新和上拉加载更多
- scrollviewpager 图片轮播控件，实现了webview跳转
- wheelview 日期排期表格控件
- refreshLayout 下拉刷新上拉加载更多
- ClearEditText 带自动清除按钮的EditText
- RoundImageView 圆角图片
- RoundImageView2 圆形图片
- ScrollTextView 跑马灯效果的TextView 可以弃用，TextView可以自己设置此效果
- SwipeListView 实现向左滑动拉出按钮 类似老版微信滑动删除
- TextImageButton 可以输入字的ImageButton
- TipEditText 右边带提示的EditText

### networkcore 网络框架

#### Retrofit

[官方简介](http://square.github.io/retrofit)

#### oKhttp

[官方简介](http://square.github.io/okhttp/)


#### RxJava

[官方简介](https://github.com/ReactiveX/RxJava/wiki)
