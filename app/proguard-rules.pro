# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\programfiles\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 友盟的混淆
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class [com.mundane.examassistant].R$*{
    public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-dontwarn org.greenrobot.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
# 如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

# 如果引用了v4或者v7包
-dontwarn android.support.**
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {
 *;
}
# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保护注解
-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation {*;}

# 泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod
# 不混淆内部类
-keepattributes InnerClasses
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
    public static java.lang.String TABLENAME;
}
-keep public class * extends android.os.Binder
-keep class **$Properties

# If you do not use SQLCipher:
-dontwarn org.greenrobot.greendao.database.**
# If you do not use Rx:
-dontwarn rx.**
-keepclassmembers class rx.** { *; }
# retrolambda
-dontwarn java.lang.invoke.*
# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity { #保持类成员
   public void *(android.view.View);
}

-keepclassmembers enum * {                  # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {    # 保持Parcelable不被混淆
  public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

 # butterknife混淆代码
 # Retain generated class which implement Unbinder.
 -keep public class * implements butterknife.Unbinder {
      public <init>(**, android.view.View);
  }

 # Prevent obfuscation of types which use ButterKnife annotations since the simple name
 # is used to reflectively look up the generated ViewBinding.
 -keep class butterknife.*
 -keepclasseswithmembernames class * { @butterknife.* <methods>; }
 -keepclasseswithmembernames class * { @butterknife.* <fields>; }

 # fastjson
 -dontwarn com.alibaba.fastjson.**
