package top.niunaijun.blackbox.fake.service;


import black.android.os.BRServiceManager;
import black.com.android.internal.telephony.BRISubStub;
import top.niunaijun.blackbox.fake.hook.BinderInvocationStub;
import top.niunaijun.blackbox.fake.service.base.ValueMethodProxy;

public class ISubProxy extends BinderInvocationStub {
    public static final String TAG = "ISubProxy";

    public ISubProxy() {
        super(BRServiceManager.get().getService("isub"));
    }

    @Override
    protected Object getWho() {
        return BRISubStub.get().asInterface(BRServiceManager.get().getService("isub"));
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService("isub");
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @Override
    public Object getProxyInvocation() {
        return super.getProxyInvocation();
    }

    @Override
    public void onlyProxy(boolean only) {
        super.onlyProxy(only);
    }

    @Override
    protected void onBindMethod() {
        super.onBindMethod();

        addMethodHook(new ValueMethodProxy("getAllSubInfoList", null));
        addMethodHook(new ValueMethodProxy("getAllSubInfoCount", -1));
        addMethodHook(new ValueMethodProxy("getActiveSubscriptionInfo", null));
        addMethodHook(new ValueMethodProxy("getActiveSubscriptionInfoForIccId", null));
        addMethodHook(new ValueMethodProxy("getActiveSubscriptionInfoForSimSlotIndex", null));
        addMethodHook(new ValueMethodProxy("getActiveSubscriptionInfoList", null));
        addMethodHook(new ValueMethodProxy("getActiveSubInfoCount", -1));
        addMethodHook(new ValueMethodProxy("getActiveSubInfoCountMax", -1));
        addMethodHook(new ValueMethodProxy("getAvailableSubscriptionInfoList", null));
        addMethodHook(new ValueMethodProxy("getAccessibleSubscriptionInfoList", null));
        addMethodHook(new ValueMethodProxy("addSubInfoRecord", -1));
        addMethodHook(new ValueMethodProxy("addSubInfo", -1));
        addMethodHook(new ValueMethodProxy("removeSubInfo", -1));
//        addMethodHook(new ValueMethodProxy("getAllSubInfoCount", -1));
//        addMethodHook(new ValueMethodProxy("getAllSubInfoCount", -1));
    }
}
