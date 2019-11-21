package com.lwkandroid.bluetoothkit.search.le;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Build;

import com.lwkandroid.bluetoothkit.search.BluetoothSearcher;
import com.lwkandroid.bluetoothkit.search.SearchResult;
import com.lwkandroid.bluetoothkit.search.response.BluetoothSearchResponse;
import com.lwkandroid.bluetoothkit.utils.BluetoothUtils;

import java.util.List;

/**
 * @author dingjikerbo
 */
public class BluetoothLESearcher extends BluetoothSearcher
{
    private BluetoothLESearcher()
    {
        mBluetoothAdapter = BluetoothUtils.getBluetoothAdapter();
    }

    public static BluetoothLESearcher getInstance()
    {
        return BluetoothLESearcherHolder.instance;
    }

    private static class BluetoothLESearcherHolder
    {
        private static BluetoothLESearcher instance = new BluetoothLESearcher();
    }

    @Override
    public void startScanBluetooth(BluetoothSearchResponse response)
    {
        super.startScanBluetooth(response);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mBluetoothAdapter.getBluetoothLeScanner().startScan(mScanCallBack21);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            mBluetoothAdapter.startLeScan(mScanCallBack18);
    }

    @Override
    public void stopScanBluetooth()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallBack21);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            mBluetoothAdapter.stopLeScan(mScanCallBack18);
        super.stopScanBluetooth();
    }

    @Override
    protected void cancelScanBluetooth()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallBack21);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            mBluetoothAdapter.stopLeScan(mScanCallBack18);
        super.cancelScanBluetooth();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private final LeScanCallback mScanCallBack18 = new LeScanCallback()
    {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord)
        {            
			notifyDeviceFounded(new SearchResult(device, rssi, scanRecord));
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private final ScanCallback mScanCallBack21 = new ScanCallback()
    {
        @Override
        public void onScanResult(int callbackType, ScanResult result)
        {
            super.onScanResult(callbackType, result);
            notifyDeviceFounded(new SearchResult(result.getDevice(), result.getRssi(), result.getScanRecord().getBytes()));
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results)
        {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode)
        {
            super.onScanFailed(errorCode);
        }
    };
}
