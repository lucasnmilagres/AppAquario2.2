package com.example.AppAquario2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class: RegisteredDeviceItem
 * Version: 1.0
 * Parameters: void
 * Return: void
 * Created: 06/04/16
 * Creator: Lucas Gabriel N. Milagres
 */
public class RegisteredDeviceItem implements Parcelable {
    private String code;
    private String name;
    private String parentType;
    private int index;

    public RegisteredDeviceItem(String code, String name, String parentType)
    {
        this.code = code;
        this.name = name;
        this.parentType = parentType;
        this.index=0;
    }

    public String getCode() {return code;}

    public void setCode(String code) {this.code = code;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public int getIndex() {return index;}

    public void setIndex(int index) {this.index = index;}

    protected RegisteredDeviceItem(Parcel in) {
        code=in.readString();
        name = in.readString();
        parentType = in.readString();
        index=in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(parentType);
        dest.writeInt(index);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RegisteredDeviceItem> CREATOR = new Parcelable.Creator<RegisteredDeviceItem>() {
        @Override
        public RegisteredDeviceItem createFromParcel(Parcel in) {
            return new RegisteredDeviceItem(in);
        }

        @Override
        public RegisteredDeviceItem[] newArray(int size) {
            return new RegisteredDeviceItem[size];
        }
    };
}
