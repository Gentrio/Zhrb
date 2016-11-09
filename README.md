#模仿的知乎日报(修改成符合我的使用习惯)

##简介

本人使用知乎日报的频率比较高,可是知乎日报的一些功能并不满足我的日常使用习惯(其实是为了省流量...)

偷偷地改了一下下...
* 日报的无图模式只支持2G/3G 将其无图模式改为不加载网络图片 并置于菜单栏显眼位置
* 日报的我的收藏位置不好点 也将其置于菜单栏显眼位置 改成了离线收藏
* 字体大小改变放在了原来我的收藏的位置

#### 相关
* 遵循Material Design 
* 使用了Retrofit2 Picasso RxAndroid RxJava

##依赖

* Java Development Kit (JDK) 7 +
* com.android.tools.build:gradle:2.0.0
* Android SDK
  * SDK 23
  * build tool 24.0.0 rc1
  
##权限说明
```xml
//获取网络状态
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//用于访问网络
<uses-permission android:name="android.permission.INTERNET"/>
//读取扩展卡信息
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
//写入扩展卡信息
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
##声明
本软件与其代码非由知乎创作或维护。软件中所包含的信息与内容皆违反版权与知乎用户协议,其中的所有内容均可在知乎获取,若侵犯知乎的权益，则立即删除此页面和所有项目。API来自 https://github.com/izzyleung/ZhihuDailyPurify/wiki/知乎日报-API-分析

##显示
![alt text](https://github.com/Gentrio/zhrb/blob/master/one.png)
![alt text](https://github.com/Gentrio/zhrb/blob/master/two.png)
![alt text](https://github.com/Gentrio/zhrb/blob/master/three.png)
