package top.niunaijun.blackbox.fake.service

import black.android.os.BRBuild
import top.niunaijun.blackbox.fake.hook.ClassInvocationStub

class BuildProxy: ClassInvocationStub() {
    override fun getWho(): Any {
       return BRBuild.get()
    }

    override fun isBadEnv(): Boolean {
        return false
    }

    override fun inject(baseInvocation: Any?, proxyInvocation: Any?) {
        BRBuild.get()._set_BOARD("umi")
        BRBuild.get()._set_BRAND("Xiaomi")
        BRBuild.get()._set_DEVICE("umi")
        BRBuild.get()._set_DISPLAY("QKQ1.191117.002 test-keys")
        BRBuild.get()._set_HOST("c5-miui-ota-bd074.bj")
        BRBuild.get()._set_ID("QKQ1.191117.002")
        BRBuild.get()._set_MANUFACTURER("Xiaomi")
        BRBuild.get()._set_MODEL("Mi 10")
        BRBuild.get()._set_PRODUCT("umi")
        BRBuild.get()._set_TAGS("release-keys")
        BRBuild.get()._set_TYPE("user")
        BRBuild.get()._set_USER("builder")
    }

}