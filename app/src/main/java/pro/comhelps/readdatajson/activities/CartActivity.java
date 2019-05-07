package pro.comhelps.readdatajson.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.adapters.CartAdapter;
import pro.comhelps.readdatajson.db_components.MyDb;
import pro.comhelps.readdatajson.models.Cart2;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private Cart2ViewModel cart2ViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        final CartAdapter cartAdapter = new CartAdapter();
        recyclerView.setAdapter(cartAdapter);

        cart2ViewModel = ViewModelProviders.of(this).get(Cart2ViewModel.class);

        cart2ViewModel.getGoods().observe(this, new Observer<List<Cart2>>() {
            @Override
            public void onChanged(@Nullable List<Cart2> cart2s) {
                cartAdapter.setCart2s(cart2s);

                System.out.println(cart2ViewModel.getGoods());

            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                cart2ViewModel.delete(cartAdapter.getCart2At(viewHolder.getAdapterPosition()));
                Toast.makeText(CartActivity.this, "This Item Deleted!", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cart_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                cart2ViewModel.deleteAllGoods();
                Toast.makeText(this, "All items deleted!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
