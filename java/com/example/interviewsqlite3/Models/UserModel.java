package com.example.interviewsqlite3.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shriji on 28/3/18.
 */

public class UserModel implements Parcelable {

    private int userId;
    private String userName;
    private String userPhone;
    private String userImage;

    public UserModel(String userName, String userPhone, String userImage) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userImage = userImage;
    }

    public UserModel() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeString(this.userImage);
    }

    protected UserModel(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.userImage = in.readString();
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
