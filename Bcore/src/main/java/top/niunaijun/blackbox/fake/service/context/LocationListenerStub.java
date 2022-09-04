package top.niunaijun.blackbox.fake.service.context;

import android.location.Location;

import java.lang.reflect.Method;
import java.util.List;

import top.niunaijun.blackbox.app.BActivityThread;
import top.niunaijun.blackbox.fake.frameworks.BLocationManager;
import top.niunaijun.blackbox.fake.hook.ClassInvocationStub;
import top.niunaijun.blackbox.fake.hook.MethodHook;
import top.niunaijun.blackbox.fake.hook.ProxyMethod;

/**
 * Created by Refgd on 4/8/21.
 * * ∧＿∧
 * (`･ω･∥
 * 丶　つ０
 * しーＪ
 */
public class LocationListenerStub extends ClassInvocationStub {
    public static final String TAG = "LocationListenerStub";
    private Object mBase;

    public Object wrapper(final Object locationListenerProxy) {
        mBase = locationListenerProxy;
        injectHook();
        return getProxyInvocation();
    }

    @Override
    protected Object getWho() {
        return mBase;
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {

    }

    @Override
    protected void onBindMethod() {

    }

    @ProxyMethod("onLocationChanged")
    public static class OnLocationChanged extends MethodHook {

        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            if(args[0] instanceof List){
                List<Location> locations = (List<Location>) args[0];
                locations.clear();
                locations.add(BLocationManager.get().getLocation(BActivityThread.getUserId(), BActivityThread.getAppPackageName()).convert2SystemLocation());
                args[0] = locations;
            }else if(args[0] instanceof  Location){
                args[0] = BLocationManager.get().getLocation(BActivityThread.getUserId(), BActivityThread.getAppPackageName()).convert2SystemLocation();
            }
            return method.invoke(who, args);
        }
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }
}
