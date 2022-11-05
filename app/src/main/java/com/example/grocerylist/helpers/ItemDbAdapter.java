package com.example.grocerylist.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.grocerylist.datamodels.Item;

import java.util.ArrayList;

public class ItemDbAdapter {
    private static String dbName = "ItemDb";
    private static String table = "item";
    private static int dbVersion = 1;
    private ItemDbHelper helper;
    private SQLiteDatabase itemDb;


    public ItemDbAdapter(Context context){
        helper = new ItemDbHelper(context, dbName, null, dbVersion);
    }

    public void insertItem(Item item){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("quantity", item.getQuantity());
        itemDb.insert(table, null, contentValues);
    }

    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<Item>();
        Cursor cursor = itemDb.query(table, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            items.add(
              new Item(
                      cursor.getString(cursor.getColumnIndexOrThrow("name")),
                      Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")))
              )
            );
        }
        cursor.close();
        return  items;
    }

    public void deleteByName(String name){
        itemDb.delete(table, "name='" + name + "'", new String[] {});
    }

    public void open(){
        itemDb = helper.getWritableDatabase();
    }

    public void close(){
        itemDb.close();
        itemDb = null;
    }

    private static  class ItemDbHelper extends  SQLiteOpenHelper {

        public ItemDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            sqLiteDatabase.execSQL("CREATE TABLE " + table +"(name TEXT PRIMARY KEY, quantity INTEGER NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
            sqLiteDatabase.execSQL("DROP TABLE " + table);
            sqLiteDatabase.execSQL("CREATE TABLE " + table +"(name TEXT PRIMARY KEY, quantity INTEGER NOT NULL)");
        }
    }
}
