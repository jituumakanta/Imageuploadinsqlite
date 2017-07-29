package com.example.wrapme09.imageuploadinsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Wrap me 09 on 09-06-2017.
 */

public class CartDatabase extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "image";
    //table name
    private static final String TABLE_USER = "imageuser";
    // column name
    private static final String KEY_COLUMN_ID = "id";
    private static final String KEY_COLUMN_PRODUCTID = "productid";
    public static final String KEY_IMAGE = "image";

    public CartDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_COLUMN_PRODUCTID + " TEXT," + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    /////////////CREATE////////////////////////////////////////////////////////////////
    public void addCartRow(byte[] imageBytes) {

        //System.out.println("before add items");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COLUMN_PRODUCTID, "hhh"); // Email
        values.put(KEY_IMAGE, imageBytes);


        // Inserting Row
        db.insert(TABLE_USER, null, values);
        //System.out.println("data inserted successfully");
        db.close(); // Closing database connection
    }

    //////////READ///////////////////////////////////////////////////////////////

    public ArrayList<String> getAllProductID() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT  * FROM " + TABLE_USER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(KEY_COLUMN_PRODUCTID)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllID() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT  * FROM " + TABLE_USER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(KEY_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public byte[] retreiveImageFromDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        /*Cursor cur = db.query(true, TABLE_USER, new String[]{KEY_IMAGE,}, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            //cur.getColumnIndex(KEY_IMAGE)
            byte[] blob = cur.getBlob(1);
            cur.close();
            return blob;
        }
        cur.close();
        return null;*/
    /*    byte[] bytes = new byte[0];
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor res = db1.rawQuery("SELECT  * FROM " + TABLE_USER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            bytes=res.getBlob(0);
            res.moveToNext();
        }
        return bytes;*/
        byte[] blob = new byte[0];
        Cursor cur = db.query(true, TABLE_USER, new String[]{
                KEY_IMAGE}, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            blob = cur.getBlob(cur.getColumnIndex(KEY_IMAGE));
            //String name = cur.getString(cur.getColumnIndex(KEY_COLUMN_PRODUCTID));
            //System.out.println("ddd" + name);
            cur.close();
            //return new Employee(Utility.getPhoto(blob), name, age);
        }
        cur.close();
        return null;


    }


    ////////////DELETE//////////////////////////////////////////////////////////
    public void deleteCartRows(String productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_COLUMN_PRODUCTID + " LIKE ?";
        String[] selectionArgs = {productID};
        db.delete(TABLE_USER, selection, selectionArgs);
    }


}
