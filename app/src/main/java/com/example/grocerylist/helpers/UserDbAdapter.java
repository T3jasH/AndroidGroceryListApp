package com.example.grocerylist.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.grocerylist.datamodels.User;

import java.util.ArrayList;

public class UserDbAdapter {
    private static String dbName = "UserDb";
    private static String table = "user";
    private static int dbVersion = 1;
    private UserDbHelper helper;
    private SQLiteDatabase userDb;


    public UserDbAdapter(Context context){
        helper = new UserDbHelper(context, dbName, null, dbVersion);
    }

    public void open(){
        userDb = helper.getWritableDatabase();
    }

    public void close(){
        userDb.close();
        userDb = null;
    }

    public void createUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("password", user.getPassword());
        userDb.insert(table, null, contentValues);
        Log.d("create user", "done");
    }

    public User getUserByEmail(String email){
        Cursor cursor = userDb.rawQuery("SELECT * FROM " + table + " WHERE email='" + email + "'", new String[] {});
        if(cursor.moveToNext()){
            return  new User(
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password"))
            );
        }
        return null;
    }

    public boolean validateUser(User user){
        User fromDb = getUserByEmail(user.getEmail());
        if(fromDb == null){
            Log.d("", "user null");
        }
        if(fromDb == null || !fromDb.getPassword().equals(user.getPassword())) return false;
        return true;
    }

    private static class UserDbHelper extends SQLiteOpenHelper {

        public UserDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL("CREATE TABLE " + table +"(email TEXT PRIMARY KEY, password TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
            sqLiteDatabase.execSQL("DROP TABLE " + table);
            sqLiteDatabase.execSQL("CREATE TABLE " + table +"(email TEXT PRIMARY KEY, password TEXT NOT NULL)");
        }
    }
}
