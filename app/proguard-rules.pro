-keepattributes Signature
-ignorewarnings
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}
-ignorewarnings


-keep public class tender.guru.suvidha.model.* {*;}

-repackageclasses 'tender.guru.suvidha'