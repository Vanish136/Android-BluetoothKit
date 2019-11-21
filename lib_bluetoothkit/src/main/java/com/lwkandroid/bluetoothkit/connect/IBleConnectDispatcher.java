package com.lwkandroid.bluetoothkit.connect;

import com.lwkandroid.bluetoothkit.connect.request.BleRequest;

public interface IBleConnectDispatcher {

    void onRequestCompleted(BleRequest request);
}
