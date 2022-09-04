package top.niunaijun.blackbox.fake.hook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by BlackBoxing on 2022/5/24.
 * 控制安卓版本变化函数级hook
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyVersion {
    int upper();
    int lower();
}
