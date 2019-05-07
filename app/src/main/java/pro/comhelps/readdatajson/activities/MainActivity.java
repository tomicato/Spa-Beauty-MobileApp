package pro.comhelps.readdatajson.activities;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.fragments.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    private Button button_get_json;
    private Button button_parse_json;
    private String json_string;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";

    private Cart2ViewModel cart2ViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Создание слушателя на обработку клика по пункту меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Плавающая кнопка
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(defaultOptions)
                        .build();
                ImageLoader.getInstance().init(config);
            }
        });

        // Кнопка в ActionBar для входа в боковое меню
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Отображене фото
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        // Выполнение фонового соединения с сервером и получение Json данных
        new BackgroundTask().execute();

    }

    @SuppressLint("StaticFieldLeak")
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String json_url;

        @Override
        protected void onPreExecute() {
            json_url = "http://tomsk-life.pro/getdata";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();

                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... voids) {
            super.onProgressUpdate(voids);
        }

        @Override
        protected void onPostExecute(String result) {

            json_string = result;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ShowToast")
    public void parseJson(View v) {
        if (json_string == null) {

            Toast.makeText(this, "Нет Данных! \n или Отсутствует интернет", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, DisplayListView.class);
            intent.putExtra("json_data", json_string);
            startActivity(intent);
        }
    }


    @SuppressLint("WrongConstant")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_message) {
            Toast.makeText(this, "Сообщение отправить", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_chat) {
            startActivity(new Intent(MainActivity.this, ProductsActivity.class));
            return true;
        } else if (id == R.id.nav_profile) {

            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            String savedText = sPref.getString(SAVED_TEXT, "");


            if (!savedText.isEmpty()) {
                Intent in = new Intent(MainActivity.this, AccountActivity.class);
                in.putExtra("id", savedText);
                startActivity(in);
            } else {
                Intent in = new Intent(MainActivity.this, LogActivity.class);
                startActivity(in);
            }

        } else if (id == R.id.nav_logout) {
            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_TEXT, "");
            ed.apply();
            Toast.makeText(this, "Вы вышли из аккаунта", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Поделиться", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Отправить", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_shop) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
            View view = item.getActionView();
            parseJson(view);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
