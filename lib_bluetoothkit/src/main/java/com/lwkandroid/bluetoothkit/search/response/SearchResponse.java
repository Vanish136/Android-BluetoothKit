package com.lwkandroid.bluetoothkit.search.response;

import com.lwkandroid.bluetoothkit.search.SearchResult;

/**
 * Created by dingjikerbo on 2016/9/1.
 */
public interface SearchResponse {

    void onSearchStarted();

    void onDeviceFounded(SearchResult device);

    void onSearchStopped();

    void onSearchCanceled();
}
