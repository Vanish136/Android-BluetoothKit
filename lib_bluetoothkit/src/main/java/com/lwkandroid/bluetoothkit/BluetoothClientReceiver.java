package com.lwkandroid.bluetoothkit;

import com.lwkandroid.bluetoothkit.connect.listener.BleConnectStatusListener;
import com.lwkandroid.bluetoothkit.connect.response.BleNotifyResponse;
import com.lwkandroid.bluetoothkit.receiver.listener.BluetoothBondListener;
import com.lwkandroid.bluetoothkit.connect.listener.BluetoothStateListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liwentian on 2017/1/13.
 */

public class BluetoothClientReceiver {

    private HashMap<String, HashMap<String, List<BleNotifyResponse>>> mNotifyResponses;
    private HashMap<String, List<BleConnectStatusListener>> mConnectStatusListeners;
    private List<BluetoothStateListener> mBluetoothStateListeners;
    private List<BluetoothBondListener> mBluetoothBondListeners;
}
