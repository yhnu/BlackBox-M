package black.android.content.pm;


import android.content.pm.PackageManager;
import android.content.pm.PackageParser;

import java.io.File;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BConstructor;
import top.niunaijun.blackreflection.annotation.BMethod;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

@BClassName("android.content.pm.PackageParser")
public interface PackageParserPie {
    @BConstructor
    PackageParser _new();

    @BStaticMethod
    void collectCertificates(PackageParser.Package p, boolean skipVerify);

    @BMethod
    PackageParser.Package parsePackage(File File0, int int1);

    @BStaticMethod
    void setCallback(PackageParser.Callback cb);
}
