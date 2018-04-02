package com.example.interviewsqlite3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.interviewsqlite3.Models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shriji on 28/3/18.
 */

public class DBOperations {

    private Context mContext;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public DBOperations(Context context) {
        mContext = context;
        mSQLiteOpenHelper = new DBHelper(mContext);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public void openDB() {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
    }

    public void clodeDB() {
        mSQLiteDatabase.close();
    }

    public long createUser(UserModel model) {
        ContentValues values = new ContentValues(3);
        values.put(DBConstant.COLUMN_NAME, model.getUserName());
        values.put(DBConstant.COLUMN_PHONE, model.getUserPhone());
        values.put(DBConstant.COLUMN_IMAGE, model.getUserImage());
        long status = mSQLiteDatabase.insert(DBConstant.TABLE_NAME, null, values);
        return status;
    }

    public List<UserModel> getAllUser() {
        List<UserModel> userList = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query(DBConstant.TABLE_NAME, DBConstant.ALL_COLUMN,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            UserModel model = new UserModel();
            model.setUserId(cursor.getInt(cursor.getColumnIndex(DBConstant.COLUMN_ID)));
            model.setUserName(cursor.getString(cursor.getColumnIndex(DBConstant.COLUMN_NAME)));
            model.setUserPhone(cursor.getString(cursor.getColumnIndex(DBConstant.COLUMN_PHONE)));
            model.setUserImage(cursor.getString(cursor.getColumnIndex(DBConstant.COLUMN_IMAGE)));
            userList.add(model);
        }
        return userList;
    }

    public void deleteUserByID(int userId) {
        mSQLiteDatabase.delete(DBConstant.TABLE_NAME,
                DBConstant.COLUMN_ID + " =?",
                new String[]{String.valueOf(userId)});
    }

    public void updateUserByID(int userId, UserModel model) {
        ContentValues values = new ContentValues(3);
        values.put(DBConstant.COLUMN_NAME, model.getUserName());
        values.put(DBConstant.COLUMN_PHONE, model.getUserPhone());
        values.put(DBConstant.COLUMN_IMAGE, model.getUserImage());
        mSQLiteDatabase.update(DBConstant.TABLE_NAME,
                values,
                DBConstant.COLUMN_ID + " =?",
                new String[]{String.valueOf(userId)});
    }

}
