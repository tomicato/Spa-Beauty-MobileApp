package pro.comhelps.readdatajson.activities;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import pro.comhelps.readdatajson.models.Cart2;
import pro.comhelps.readdatajson.db_components.Dao;
import pro.comhelps.readdatajson.db_components.MyDb;

import java.util.List;

public class CartRepository {
    private Dao dao;
    private LiveData<List<Cart2>> allGoods;

    public CartRepository(Application application){
        MyDb myDb = MyDb.getInstance(application);
        dao = myDb.dao();
        allGoods = dao.getGoods();
    }

    public void insert(Cart2 cart2){
        new InsertCartAsyncTask(dao).execute(cart2);
    }
    public void update(Cart2 cart2){
        new UpdateCartAsyncTask(dao).execute(cart2);
    }
    public void deleteAll(){
        new DeleteAllCartAsyncTask(dao).execute();
    }
    public void delete(Cart2 cart2){
        new DeleteCartAsyncTask(dao).execute(cart2);
    }
    public  LiveData<List<Cart2>> getGoods(){
        return allGoods;
    }


    private static class InsertCartAsyncTask extends AsyncTask<Cart2, Void, Void>{

        private Dao dao;

        private InsertCartAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Cart2... cart2s) {

            dao.insert(cart2s[0]);
            return null;
        }
    }

    private static class UpdateCartAsyncTask extends AsyncTask<Cart2, Void, Void>{

        private Dao dao;

        private UpdateCartAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Cart2... cart2s) {

            dao.update(cart2s[0]);
            return null;
        }
    }

    private static class DeleteCartAsyncTask extends AsyncTask<Cart2, Void, Void>{

        private Dao dao;

        private DeleteCartAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Cart2... cart2s) {

            dao.delete(cart2s[0]);
            return null;
        }
    }

    private static class DeleteAllCartAsyncTask extends AsyncTask<Cart2, Void, Void>{

        private Dao dao;

        private DeleteAllCartAsyncTask(Dao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Cart2... cart2s) {

            dao.deleteAllGoods();
            return null;
        }
    }
}
