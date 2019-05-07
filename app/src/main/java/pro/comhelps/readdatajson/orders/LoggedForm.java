package pro.comhelps.readdatajson.orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.activities.MainActivity;
import pro.comhelps.readdatajson.db_components.DbHelper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static pro.comhelps.readdatajson.db_components.DbHelper.TABLE_PRODUCTS;
public class LoggedForm extends AppCompatActivity {

    Button sendServer;
    String server_url = "";
    String json_string, int_id;
    TextView txtId;
    EditText logAddress, logPhone;


    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_form);

        dbHelper = new DbHelper(LoggedForm.this, null, null, 1);
        database = dbHelper.getWritableDatabase();
        sendServer = findViewById(R.id.btnSend);
        txtId = findViewById(R.id.txt_id_user);

        int_id = getIntent().getStringExtra("id");
        txtId.setText(int_id);

        logAddress = findViewById(R.id.log_address);
        logPhone = findViewById(R.id.log_phone);

    }


    // Send To Pay
    public void sendToServer(View v) {
        new Background().execute();
    }

    // Background Task Send Data to Server
    class Background  extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String query = "SELECT * FROM " + TABLE_PRODUCTS;
            BufferedReader reader = null;

            // Cursor point to a location in your result
            @SuppressLint("Recycle")
            Cursor cursor = database.rawQuery(query, null);
            try {
                URL url = new URL(server_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);
                String my_data = "";
                int qty_all = 0;
                int sum_all = 0;
               String address =  logAddress.getText().toString().trim();
               String phone = logPhone.getText().toString().trim();

                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                while (cursor.moveToNext()) {
                    my_data += String.valueOf(cursor.getInt(cursor.getColumnIndex("pid")))
                            + "&" + String.valueOf(cursor.getInt(cursor.getColumnIndex("qty"))) + ",";
                     qty_all += cursor.getInt(cursor.getColumnIndex("qty"));
                     sum_all += cursor.getInt(cursor.getColumnIndex("sum"));
                }

                // Send To Server
                my_data = my_data.substring(0, my_data.lastIndexOf(','))
                        + "&" +  URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                        + "&" +  URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                        + "&" +  URLEncoder.encode("qty_all", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(qty_all), "UTF-8")
                        + "&" +  URLEncoder.encode("sum_all", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(sum_all), "UTF-8")
                        + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(int_id, "UTF-8");

                bw.write(my_data);
                bw.flush();
                bw.close();
                os.close();

                System.out.println(my_data);

                InputStream is = huc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer buffer = new StringBuffer();


                while((json_string = br.readLine()) != null){
                    buffer.append(json_string);
                }

                is.close();
                huc.disconnect();

                return buffer.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            server_url = "http://tomsk-life.pro/site/insert";
        }

        @Override
        protected void onPostExecute(String s) {
            json_string = s;
            Toast.makeText(LoggedForm.this, "Ваш заказ оплачен!" + json_string, Toast.LENGTH_SHORT).show();
            database.execSQL("DELETE FROM " + TABLE_PRODUCTS);
            Toast.makeText(getApplicationContext(), "Ваша корзина пуста.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
