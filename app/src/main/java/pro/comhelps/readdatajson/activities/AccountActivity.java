package pro.comhelps.readdatajson.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.adapters.CustomAdapter;
import pro.comhelps.readdatajson.models.Custom;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;

public class AccountActivity extends AppCompatActivity {

    String req_url = "";
    ListView listView;
    CustomAdapter customAdapter;
    String json_string;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Отображене фото
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        new BackgroundTask().execute(id);


    }

    /*public void sendId(View v){
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        new BackgroundTask().execute(id);
    }*/

    class BackgroundTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            String os_id = params[0];
            BufferedReader reader = null;

            try {
                URL url = new URL(req_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);

                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String my_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(os_id, "UTF-8");

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
            req_url = "http://tomsk-life.pro/site/get-orders";
        }

        @Override
        protected void onPostExecute(String s) {
            listView = findViewById(R.id.resp_list);
            json_string = s;
            customAdapter = new CustomAdapter(getApplicationContext(), R.layout.response_layout);
            listView.setAdapter(customAdapter);

            try {

                jsonArray  = new JSONObject(json_string).getJSONArray("orders");

                int count = 0;

                String ord_img;
                String ord_name;
                String ord_price;
                String ord_qty;
                String ord_sum;
                String ord_status;


                while(count < jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);

                    ord_img = jo.getString("img");
                    ord_name = jo.getString("name");
                    ord_price = jo.getString("price");
                    ord_qty = jo.getString("qty_items");
                    ord_sum = jo.getString("sum_items");
                    ord_status = jo.getString("status");

                    Custom custom = new Custom(ord_img, ord_name, ord_price, ord_qty, ord_sum, ord_status);
                    customAdapter.add(custom);

                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(AccountActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



}
