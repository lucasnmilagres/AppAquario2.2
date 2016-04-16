package com.example.AppAquario2;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Class: AquariumItem
 * Version: 1.0
 * Parameters: void
 * Return: void
 * Created: 16/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class AquariumItem implements Parcelable {
    private ArrayList<RegisteredDeviceItem> registeredDeviceItemList;

    public AquariumItem(ArrayList<RegisteredDeviceItem> registeredDeviceItemList)
    {
        this.registeredDeviceItemList = registeredDeviceItemList;
    }

    public ArrayList<RegisteredDeviceItem> getRegisteredDeviceItemList() { return registeredDeviceItemList; }

    public void setRegisteredDeviceItemList(ArrayList<RegisteredDeviceItem> registeredDeviceItemList) { this.registeredDeviceItemList = registeredDeviceItemList; }

    protected AquariumItem(Parcel in) {
        if (in.readByte() == 0x01) {
            registeredDeviceItemList = new ArrayList<RegisteredDeviceItem>();
            in.readList(registeredDeviceItemList, RegisteredDeviceItem.class.getClassLoader());
        } else {
            registeredDeviceItemList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (registeredDeviceItemList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(registeredDeviceItemList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AquariumItem> CREATOR = new Parcelable.Creator<AquariumItem>() {
        @Override
        public AquariumItem createFromParcel(Parcel in) {
            return new AquariumItem(in);
        }

        @Override
        public AquariumItem[] newArray(int size) {
            return new AquariumItem[size];
        }
    };
}
