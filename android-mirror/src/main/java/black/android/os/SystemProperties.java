package black.android.os;


import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

@BClassName("android.os.SystemProperties")
public interface SystemProperties {
    @BStaticMethod
    String get(String key, String def);
}
