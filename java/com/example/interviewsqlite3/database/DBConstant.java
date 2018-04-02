package com.example.interviewsqlite3.database;

/**
 * Created by shriji on 28/3/18.
 */

public interface DBConstant {

    String TABLE_NAME = "userTable";
    String COLUMN_ID = "userId";
    String COLUMN_NAME = "userName";
    String COLUMN_PHONE = "userPhone";
    String COLUMN_IMAGE = "userImage";

    String[] ALL_COLUMN = {COLUMN_ID, COLUMN_NAME, COLUMN_PHONE, COLUMN_IMAGE};

    String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT UNIQUE," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_IMAGE + " TEXT" + ");";

    String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
