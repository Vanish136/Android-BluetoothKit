package com.inuker.bluetooth.library.search;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dingjikerbo on 2016/8/28.
 */
public class SearchResult implements Parcelable
{
    public BluetoothDevice device;
    public int rssi;
    public byte[] scanRecord;
    private String finalName;

    public SearchResult(BluetoothDevice device)
    {
        this(device, 0, null);
    }

    public SearchResult(BluetoothDevice device, int rssi, byte[] scanRecord)
    {
        this.device = device;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public String getName()
    {
        if (TextUtils.isEmpty(finalName))
        {
            finalName = device.getName();
            if (TextUtils.isEmpty(finalName))
            {
                finalName = parseAdvertisedData(scanRecord).getName();
                //                KLog.e("scanRecord.getName=" + finalName);
            }
        }
        if (TextUtils.isEmpty(finalName))
            finalName = "Null";

        return finalName;
    }

    public String getAddress()
    {
        return device != null ? device.getAddress() : "";
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(", mac = ").append(device.getAddress());
        return sb.toString();
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeParcelable(this.device, 0);
        dest.writeInt(this.rssi);
        dest.writeByteArray(this.scanRecord);
    }

    public SearchResult(Parcel in)
    {
        this.device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.rssi = in.readInt();
        this.scanRecord = in.createByteArray();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>()
    {
        public SearchResult createFromParcel(Parcel source)
        {
            return new SearchResult(source);
        }

        public SearchResult[] newArray(int size)
        {
            return new SearchResult[size];
        }
    };

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SearchResult that = (SearchResult) o;

        return device.equals(that.device);

    }

    @Override
    public int hashCode()
    {
        return device.hashCode();
    }

    private BleAdvertisedData parseAdvertisedData(byte[] advertisedData)
    {
        List<UUID> uuids = new ArrayList<UUID>();
        String name = null;
        if (advertisedData == null)
            return new BleAdvertisedData(uuids, name);

        ByteBuffer buffer = ByteBuffer.wrap(advertisedData).order(ByteOrder.LITTLE_ENDIAN);
        while (buffer.remaining() > 2)
        {
            byte length = buffer.get();
            if (length == 0)
                break;

            byte type = buffer.get();
            switch (type)
            {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (length >= 2)
                    {
                        uuids.add(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getShort())));
                        length -= 2;
                    }
                    break;
                case 0x06: // Partial list of 128-bit UUIDs
                case 0x07: // Complete list of 128-bit UUIDs
                    while (length >= 16)
                    {
                        long lsb = buffer.getLong();
                        long msb = buffer.getLong();
                        uuids.add(new UUID(msb, lsb));
                        length -= 16;
                    }
                    break;
                case 0x09:
                    byte[] nameBytes = new byte[length - 1];
                    buffer.get(nameBytes);
                    try
                    {
                        name = new String(nameBytes, "utf-8");
                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                default:
                    buffer.position(buffer.position() + length - 1);
                    break;
            }
        }
        return new BleAdvertisedData(uuids, name);
    }

    private class BleAdvertisedData
    {
        private List<UUID> mUuids;
        private String mName;

        public BleAdvertisedData(List<UUID> uuids, String name)
        {
            mUuids = uuids;
            mName = name;
        }

        public List<UUID> getUuids()
        {
            return mUuids;
        }

        public String getName()
        {
            return mName;
        }
    }
}
