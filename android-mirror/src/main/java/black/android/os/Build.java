package black.android.os;


import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BStaticField;

@BClassName("android.os.Build")
public interface Build {
    @BStaticField
    String BOARD();

    @BStaticField
    String BRAND();

    @BStaticField
    String DEVICE();

    @BStaticField
    String DISPLAY();

    @BStaticField
    String HOST();

    @BStaticField
    String ID();

    @BStaticField
    String MANUFACTURER();

    @BStaticField
    String MODEL();

    @BStaticField
    String PRODUCT();

    @BStaticField
    String TAGS();

    @BStaticField
    String TYPE();

    @BStaticField
    String USER();
}
