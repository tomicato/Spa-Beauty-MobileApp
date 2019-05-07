package pro.comhelps.readdatajson.db_components;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.ImageView;
import pro.comhelps.readdatajson.activities.ProductsActivity;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "products.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_SUM = "sum";
    public static final String COLUMN_PRODUCT_ID = "pid";

    private ImageView imgView;
    View row;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMG + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_QTY + " INTEGER, " +
                COLUMN_SUM + " INTEGER, " +
                COLUMN_PRODUCT_ID + " INTEGER)";


        db.execSQL(query);
        System.out.println("DB successfully Created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // Add new row to DB
    public void addProduct(Products products) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IMG, products.getImg());
        cv.put(COLUMN_TITLE, products.getTitle());
        cv.put(COLUMN_PRICE, products.getPrice());
        cv.put(COLUMN_QTY, products.getQty());
        cv.put(COLUMN_SUM, products.getSum());
        cv.put(COLUMN_PRODUCT_ID, products.getPid());


        db.insert(TABLE_PRODUCTS, null, cv);
        System.out.println("Product inserted!" + cv);
        db.close();
    }

    // Delete Item from DataBase
    public void deleteProduct(String productName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS);
    }

    // Select and Draw data from DB
    public String dbToString() {
        String db_string = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;


        // Cursor point to a location in your result
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                db_string +=
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMG)) +
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)) +
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)) +
                    cursor.getString(cursor.getColumnIndex(COLUMN_QTY)) +
                    cursor.getString(cursor.getColumnIndex(COLUMN_SUM)) +
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID)) + "\n";


            } while (cursor.moveToNext());
        }


        db.close();
       // System.out.println(db_string + " " + cursor.getCount());
        return db_string;

    }
}
