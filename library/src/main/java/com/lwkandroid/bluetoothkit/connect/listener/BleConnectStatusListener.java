package com.lwkandroid.bluetoothkit.connect.listener;

import com.lwkandroid.bluetoothkit.receiver.listener.BluetoothClientListener;

/**
 * Created by dingjikerbo on 16/11/26.
 */

public abstract class BleConnectStatusListener extends BluetoothClientListener {

    public abstract void onConnectStatusChanged(String mac, int status);

    @Override
    public void onSyncInvoke(Object... args) {
        String mac = (String) args[0];
        int status = (int) args[1];
        onConnectStatusChanged(mac, status);
    }
}
