package pro.comhelps.readdatajson.activities;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import pro.comhelps.readdatajson.models.Cart2;

import java.util.List;


public class Cart2ViewModel extends AndroidViewModel {

    private static CartRepository cartRepository;
    private LiveData<List<Cart2>> allGoods;

    public Cart2ViewModel(@NonNull Application application) {
        super(application);

        cartRepository = new CartRepository(application);
        allGoods = cartRepository.getGoods();
    }

    public void insert(Cart2 cart2) {
        cartRepository.insert(cart2);
    }

    public void update(Cart2 cart2) {
        cartRepository.update(cart2);
    }

    public void delete(Cart2 cart2) {
        cartRepository.delete(cart2);
    }

    public void deleteAllGoods() {
        cartRepository.deleteAll();
    }

    public LiveData<List<Cart2>> getGoods() {
        return allGoods;
    }
}

