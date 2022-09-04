package top.niunaijun.blackbox.fake.service;

import java.lang.reflect.Method;

import black.android.bluetooth.BRIBluetoothManagerStub;
import black.android.os.BRServiceManager;
import top.niunaijun.blackbox.fake.hook.BinderInvocationStub;
import top.niunaijun.blackbox.fake.hook.MethodHook;
import top.niunaijun.blackbox.fake.hook.ProxyMethod;

/**
 * Created by Refgd on 5/21/22.
 */
public class IBluetoothManagerProxy extends BinderInvocationStub {
    public static final String TAG = "IBluetoothManagerProxy";

    public IBluetoothManagerProxy() {
        super(BRServiceManager.get().getService("bluetooth_manager"));
    }

    @Override
    protected Object getWho() {
        return BRIBluetoothManagerStub.get().asInterface(BRServiceManager.get().getService("bluetooth_manager"));
    }

    @Override
    protected void inject(Object baseInvocation, Object proxyInvocation) {
        replaceSystemService("bluetooth_manager");
    }

    @Override
    public boolean isBadEnv() {
        return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        Log.d(TAG, "call: " + method.getName() + ", args: " + Arrays.toString(args));
//        MethodParameterUtils.replaceFirstAppPkg(args);
        return super.invoke(proxy, method, args);
    }

    @ProxyMethod("getName")
    public static class getName extends MethodHook {
        @Override
        protected Object hook(Object who, Method method, Object[] args) throws Throwable {
            return null;
        }
    }
}
