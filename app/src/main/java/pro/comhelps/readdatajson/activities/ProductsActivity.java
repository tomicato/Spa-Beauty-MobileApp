package pro.comhelps.readdatajson.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.*;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.adapters.ProductsAdapter;
import pro.comhelps.readdatajson.db_components.DbHelper;
import pro.comhelps.readdatajson.orders.LoggedForm;


import static pro.comhelps.readdatajson.db_components.DbHelper.TABLE_PRODUCTS;

public class ProductsActivity extends AppCompatActivity {


    DbHelper dbHelper;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    private SQLiteDatabase database;
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        dbHelper = new DbHelper(ProductsActivity.this, null, null, 1);
        database = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.prod_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsAdapter = new ProductsAdapter(this, getAllItems());
        recyclerView.setAdapter(productsAdapter);

        // Swipe to Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

    }

    // Delete Item from DataBase
    public void deleteProduct(View v) {
        database.execSQL("DELETE FROM " + TABLE_PRODUCTS);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        Toast.makeText(this, "Ваша корзина пуста.", Toast.LENGTH_SHORT).show();

    }

    private void removeItem(int id){
        database.delete(TABLE_PRODUCTS, DbHelper.COLUMN_ID + "=" + id, null);
        productsAdapter.swapCursor(getAllItems());
    }

    // Select & put to view All Items from DataBase
    private Cursor getAllItems() {
        return database.query(
                TABLE_PRODUCTS,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    // Send To Server
    public void sendToServer(View v){

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");


        if (!savedText.isEmpty()) {
            Intent in = new Intent(ProductsActivity.this, LoggedForm.class);
            in.putExtra("id", savedText);
            startActivity(in);
        } else {
            Intent in = new Intent(ProductsActivity.this, LogActivity.class);
            Toast.makeText(this, "Войдите, чтобы продолжить оплату.", Toast.LENGTH_SHORT).show();
            startActivity(in);
            
        }

        //new Background().execute();


    }

}
