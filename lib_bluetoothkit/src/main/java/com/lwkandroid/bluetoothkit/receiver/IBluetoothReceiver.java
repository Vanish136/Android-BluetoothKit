package com.lwkandroid.bluetoothkit.receiver;

import com.lwkandroid.bluetoothkit.receiver.listener.BluetoothReceiverListener;

/**
 * Created by dingjikerbo on 2016/11/25.
 */

public interface IBluetoothReceiver {

    void register(BluetoothReceiverListener listener);
}
