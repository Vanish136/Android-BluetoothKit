package com.lwkandroid.bluetoothkit.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.lwkandroid.bluetoothkit.utils.BluetoothUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lwkandroid.bluetoothkit.Constants.SEARCH_TYPE_BLE;
import static com.lwkandroid.bluetoothkit.Constants.SEARCH_TYPE_CLASSIC;

/**
 * Created by dingjikerbo on 2016/8/28.
 */
public class SearchRequest implements Parcelable
{

    private List<SearchTask> tasks;
    //ֱ����ʾ����Թ����豸
    private boolean showConnectedDevicesDirectly = true;

    public List<SearchTask> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<SearchTask> tasks)
    {
        this.tasks = tasks;
    }

    public boolean isShowConnectedDevicesDirectly()
    {
        return showConnectedDevicesDirectly;
    }

    public void setShowConnectedDevicesDirectly(boolean showConnectedDevicesDirectly)
    {
        this.showConnectedDevicesDirectly = showConnectedDevicesDirectly;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeTypedList(this.tasks);
        dest.writeByte(this.showConnectedDevicesDirectly ? (byte) 1 : (byte) 0);
    }

    public SearchRequest()
    {
    }

    protected SearchRequest(Parcel in)
    {
        this.tasks = in.createTypedArrayList(SearchTask.CREATOR);
        this.showConnectedDevicesDirectly = in.readByte() != 0;
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>()
    {
        @Override
        public SearchRequest createFromParcel(Parcel source)
        {
            return new SearchRequest(source);
        }

        @Override
        public SearchRequest[] newArray(int size)
        {
            return new SearchRequest[size];
        }
    };

    public static class Builder
    {
        private List<SearchTask> tasks;
        private SearchRequest request;

        public Builder()
        {
            tasks = new ArrayList<>();
            request = new SearchRequest();
        }

        public Builder searchBluetoothLeDevice(int duration)
        {
            if (BluetoothUtils.isBleSupported())
            {
                SearchTask search = new SearchTask();
                search.setSearchType(SEARCH_TYPE_BLE);
                search.setSearchDuration(duration);
                tasks.add(search);
            }
            return this;
        }

        public Builder searchBluetoothLeDevice(int duration, int times)
        {
            for (int i = 0; i < times; i++)
            {
                searchBluetoothLeDevice(duration);
            }
            return this;
        }

        public Builder searchBluetoothClassicDevice(int duration)
        {
            SearchTask search = new SearchTask();
            search.setSearchType(SEARCH_TYPE_CLASSIC);
            search.setSearchDuration(duration);
            tasks.add(search);
            return this;
        }

        public Builder searchBluetoothClassicDevice(int duration, int times)
        {
            for (int i = 0; i < times; i++)
            {
                searchBluetoothClassicDevice(duration);
            }
            return this;
        }

        public Builder showConnectedDevicesDirectly(boolean show)
        {
            request.setShowConnectedDevicesDirectly(show);
            return this;
        }

        public SearchRequest build()
        {
            request.setTasks(tasks);
            return request;
        }
    }
}
