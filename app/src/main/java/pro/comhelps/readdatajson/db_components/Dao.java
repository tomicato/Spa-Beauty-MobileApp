package pro.comhelps.readdatajson.db_components;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import pro.comhelps.readdatajson.models.Cart2;

import java.util.List;

@android.arch.persistence.room.Dao
public interface Dao {

    /*@Insert
    void addCart(Cart2 cart2);*/

    @Query("SELECT * FROM shop_cart")
    LiveData<List<Cart2>> getGoods();

    @Delete
    public void delete(Cart2 cart2);

    @Query("DELETE FROM shop_cart")
    void deleteAllGoods();

    @Update
    void update(Cart2 cart2);

    @Insert
    void insert(Cart2 cart2);

}

