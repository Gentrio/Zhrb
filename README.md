# zhrb
模仿知乎日报的个人练习项目

遵循Material Design,使用了Retrofit2和Picasso,可以通过该项目用于了解这两个框架的使用

##Disclaimer - 声明
Zhihu is a trademark of Zhihu. Inc. This app is not created nor endorsed by Zhihu Inc. All the information and content accessible through Zhihu Daily Purify are subject to Zhihu's copyright and terms of use. This is a free app and does not charge for anything. All content are available for free from Zhihu.

『知乎』是 知乎. Inc 的注册商标。本软件与其代码非由知乎创作或维护。软件中所包含的信息与内容皆违反版权与知乎用户协议。它是一个免费软件，使用它不收取您任何费用。其中的所有内容均可在知乎获取。

##Dependency - 依赖

* Java Development Kit (JDK) 7 +
* com.android.tools.build:gradle:2.0.0
* Android SDK
  * SDK 23
  * build tool 24.0.0 rc1
  
##Permission - 权限说明
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
