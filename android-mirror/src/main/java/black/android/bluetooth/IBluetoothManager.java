package black.android.bluetooth;

import android.os.IBinder;
import android.os.IInterface;

import top.niunaijun.blackreflection.annotation.BClassName;
import top.niunaijun.blackreflection.annotation.BStaticMethod;

@BClassName("android.bluetooth.IBluetoothManager")
public interface IBluetoothManager {
    @BClassName("android.bluetooth.IBluetoothManager$Stub")
    interface Stub {
        @BStaticMethod
        IInterface asInterface(IBinder IBinder0);
    }
}
