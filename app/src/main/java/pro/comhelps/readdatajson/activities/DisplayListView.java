package pro.comhelps.readdatajson.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pro.comhelps.readdatajson.R;
import pro.comhelps.readdatajson.adapters.AnonsAdapter;
import pro.comhelps.readdatajson.models.Anons;

public class DisplayListView extends AppCompatActivity {

    String json_string;
    JSONArray jsonArray;
    AnonsAdapter anonsAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_view);

        listView = (ListView) findViewById(R.id.listview);
        anonsAdapter = new AnonsAdapter(this, R.layout.row_layout);
        listView.setAdapter(anonsAdapter);
        json_string = getIntent().getStringExtra("json_data");

        try {

            jsonArray  = new JSONObject(json_string).getJSONArray("server_response");

            int count = 0;
            String prod_id;
            String prod_img;
            String prod_name;
            String prod_price;
            String prod_stock;
            String prod_content;


            while(count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);

                prod_id =  jo.getString("id");
                prod_img = jo.getString("img");
                prod_name = jo.getString("prod_name");
                prod_price = jo.getString("price");
                prod_stock = jo.getString("in_stock");
                prod_content = jo.getString("full_description");

                Anons anons = new Anons(prod_id, prod_img, prod_name, prod_price, prod_stock, prod_content);
                anonsAdapter.add(anons);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
