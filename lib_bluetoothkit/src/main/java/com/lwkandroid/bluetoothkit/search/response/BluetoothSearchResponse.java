package com.lwkandroid.bluetoothkit.search.response;

import com.lwkandroid.bluetoothkit.search.SearchResult;

public interface BluetoothSearchResponse {
    void onSearchStarted();

    void onDeviceFounded(SearchResult device);

    void onSearchStopped();

    void onSearchCanceled();
}
