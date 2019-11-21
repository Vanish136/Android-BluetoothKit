package com.lwkandroid.bluetoothkit.connect.request;

import com.lwkandroid.bluetoothkit.connect.IBleConnectDispatcher;

/**
 * Created by dingjikerbo on 16/8/25.
 */
public interface IBleRequest {

    void process(IBleConnectDispatcher dispatcher);

    void cancel();
}
