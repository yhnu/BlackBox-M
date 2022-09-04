package top.niunaijun.blackbox.utils;

import android.util.Log;

import java.lang.reflect.Method;

import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;

public class HackAppUtils {

    public static final String TAG = "VirtualM";
    /**
     * Enable the Log output of QQ.
     *
     * @param classLoader class loader
     */
    private static void enableQQLogOutput(ClassLoader classLoader) {
        try {
            Reflector.on("com.tencent.qphone.base.util.QLog", true, classLoader)
                    .field("UIN_REPORTLOG_LEVEL")
                    .set(100);
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }

    private static void injectLine(ClassLoader classLoader) {
        try {
            //just for test why crash
            Method method = Reflector.on("jp.naver.line.android.util.e1$e", false, classLoader)
                    .findMethod("invoke");

            Pine.hook(method, new MethodHook() {
                @Override public void beforeCall(Pine.CallFrame callFrame) {
                    Log.i(TAG, "Before getValue");
                }

                @Override public void afterCall(Pine.CallFrame callFrame) {
                    Log.i(TAG, "After getValue:" + String.valueOf(callFrame.getResult()));
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void hackApp(String packageName, ClassLoader classLoader){
//        if ("jp.naver.line.android".equals(packageName)) {
//            injectLine(classLoader);
//        }
    }

    public static void hackActivity(String packageName, ClassLoader classLoader){
//        if ("com.tencent.mobileqq".equals(packageName)) {
//            enableQQLogOutput(classLoader);
//        }
    }
}
