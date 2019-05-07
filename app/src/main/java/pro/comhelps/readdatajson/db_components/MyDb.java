package pro.comhelps.readdatajson.db_components;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import pro.comhelps.readdatajson.models.Cart2;


@Database(entities = {Cart2.class}, version = 3, exportSchema = false)
public abstract class MyDb extends RoomDatabase {

    private static MyDb instance;
    public abstract Dao dao();

    public static synchronized MyDb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MyDb.class, "cart_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Call Method insert
            //new PopulateDbAsyncTask(instance).execute();
        }
    };

    //  Test application to insert data in DB

    /*private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        private Dao dao;
        private PopulateDbAsyncTask(MyDb myDb){
            dao = myDb.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.insert(new Cart2("http://tomsk-life.pro/images/product/amy.jpg", "Крем для лица Лисичка", "125"));
            dao.insert(new Cart2("http://tomsk-life.pro/images/product/18_39_30_main.jpg", "Крем для рук Ромашка", "345"));
            dao.insert(new Cart2("http://tomsk-life.pro/images/product/mp8.jpg", "Лосьон после бритья Шипр", "567"));
            dao.insert(new Cart2("http://tomsk-life.pro/images/product/mp7.jpg", "Лосьон после бритья Шипр23", "5667"));
            return null;
        }
    }*/
}
